package edu.ictt.blockchain.socket.pbft.queue;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import cn.hutool.core.bean.BeanUtil;
import edu.ictt.blockchain.Block.block.Block;
import edu.ictt.blockchain.core.event.AddBlockEvent;
import edu.ictt.blockchain.socket.pbft.VoteType;
import edu.ictt.blockchain.socket.pbft.event.MsgCommitEvent;
import edu.ictt.blockchain.socket.pbft.msg.VoteMsg;
/*
 * prepare队列处理
 */
@Component
public class PrepareMsgQueue extends AbstractVoteMsgQueue{

	@Resource
	private CommitMsgQueue commitMsgQueue;
	@Resource
	private ApplicationEventPublisher eventPublisher;
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	
	/**
     * 收到节点（包括自己）针对某Block的Prepare消息
     *
     * @param voteMsg
     *         voteMsg
     */
	@Override
	protected void deal(VoteMsg voteMsg, List<VoteMsg> voteMsgs) {
		String hash = voteMsg.getHash();
        VoteMsg commitMsg = new VoteMsg();
        BeanUtil.copyProperties(voteMsg, commitMsg);
        commitMsg.setVoteType(VoteType.commit);
        commitMsg.setAppId("ddd");
        //开始校验并决定是否进入commit阶段
        //校验该vote是否合法
        if (commitMsgQueue.hasOtherConfirm(hash, voteMsg.getNumber())) {
             agree(commitMsg, false);
        } else {
            //开始校验拜占庭数量，如果agree的超过2f + 1，就commit
            long agreeCount = voteMsgs.stream().filter(VoteMsg::isAgree).count();
            logger.info("[共识投票]：agreecount:"+agreeCount);
            long unAgreeCount = voteMsgs.size() - agreeCount;
            logger.info("[共识投票]：disagreecount:"+unAgreeCount);
            //开始发出commit的同意or拒绝的消息
            if (agreeCount >= pbftAgreesize()) {
                agree(commitMsg, true);
            } else if (unAgreeCount >= pbftsize() + 1) {
                agree(commitMsg, false);
            }
            
        }
	}

	
	public void log(){
		logger.info("[共识投票]：启用prepare");
	}
	
	/**
     * 判断大家是否已对其他的Block达成共识，如果true，则拒绝即将进入队列的Block
     * 这个准备放message里
     * @param hash
     *         hash
     * @return 是否存在
     */
    public boolean otherConfirm(String hash, long l) {
       if (commitMsgQueue.hasOtherConfirm(hash, l)) {
            return false;
        }
        return hasOtherConfirm(hash, l);
            
    }
    
    public void preparesize()
    {
    	System.out.println(voteStateConcurrentHashMap.size());
    }
    
    private void agree(VoteMsg commitMsg, boolean flag) {
        logger.info("[共识投票]：Prepare阶段完毕，是否进入commit的标志是：" + flag);
        //发出拒绝commit的消息
        commitMsg.setAgree(flag);
        logger.info("[共识投票]：com: "+commitMsg);
        voteStateConcurrentHashMap.put(commitMsg.getHash(), flag);
        eventPublisher.publishEvent(new MsgCommitEvent(commitMsg));
    }

    /**
     * 新区块生成后，clear掉map中number比区块小的所有数据
     *
     * @param addBlockEvent  addBlockEvent
     */
    //@Order(3)
    //@EventListener(AddBlockEvent.class)
    public void blockGenerated(AddBlockEvent addBlockEvent){
    	Block block=(Block) addBlockEvent.getSource();
    	clearOldBlockHash(block.getBlockHeader().getBlockNumber());//block.getBlockHeader().getNumber());
    }
}
