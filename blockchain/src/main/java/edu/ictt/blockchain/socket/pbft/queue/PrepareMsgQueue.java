package edu.ictt.blockchain.socket.pbft.queue;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.hutool.core.bean.BeanUtil;
import edu.ictt.blockchain.socket.pbft.VoteType;
import edu.ictt.blockchain.socket.pbft.event.MsgCommitEvent;
import edu.ictt.blockchain.socket.pbft.listener.CommitEventListener;
import edu.ictt.blockchain.socket.pbft.msg.VoteMsg;
/*
 * prepare队列处理
 */
public class PrepareMsgQueue extends AbstractVoteMsgQueue{

	private CommitMsgQueue commitMsgQueue=new CommitMsgQueue();
	
	private ListenerManager listenerManager=new ListenerManager();
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	
	/**
     * 收到节点（包括自己）针对某Block的Prepare消息
     *
     * @param voteMsg
     *         voteMsg
     */
	@Override
	void deal(VoteMsg voteMsg, List<VoteMsg> voteMsgs) {
		System.out.println("prepare");
		String hash = voteMsg.getHash();
        VoteMsg commitMsg = new VoteMsg();
        BeanUtil.copyProperties(voteMsg, commitMsg);
        commitMsg.setVoteType(VoteType.commit);
        commitMsg.setAppId("f");
        //开始校验并决定是否进入commit阶段
        //校验该vote是否合法
        System.out.println(4);
        if (commitMsgQueue.hasOtherConfirm(hash, voteMsg.getNumber())) {
        	System.out.println(6);
             agree(commitMsg, false);
        } else {
            //开始校验拜占庭数量，如果agree的超过2f + 1，就commit
            long agreeCount = voteMsgs.stream().filter(VoteMsg::isAgree).count();
            System.out.println("同意的数量：" + agreeCount);
            long unAgreeCount = voteMsgs.size() - agreeCount;
            System.out.println("不同意的数量：" + unAgreeCount);
            //开始发出commit的同意or拒绝的消息
            if (agreeCount >= pbftAgreesize()) {
            	System.out.println("agree");
                agree(commitMsg, true);
            } else if (unAgreeCount >= pbftsize() + 1) {
            	System.out.println("disagree");
                agree(commitMsg, false);
            }
            
        }
	}

	/**
     * 判断大家是否已对其他的Block达成共识，如果true，则拒绝即将进入队列的Block
     * 这个准备放message里
     * @param hash
     *         hash
     * @return 是否存在
     */
    public boolean otherConfirm(String hash, int number) {
    	System.out.println(2);
       if (commitMsgQueue.hasOtherConfirm(hash, number)) {
            return false;
        }
        return hasOtherConfirm(hash, number);
            
    }
    
    public void preparesize()
    {
    	System.out.println(voteStateConcurrentHashMap.size());
    }
    
    private void agree(VoteMsg commitMsg, boolean flag) {
        logger.info("Prepare阶段完毕，是否进入commit的标志是：" + flag);
        //发出拒绝commit的消息
        System.out.println("Prepare阶段完毕，是否进入commit的标志是：" + flag);
    	System.out.println(8);
        commitMsg.setAgree(flag);
        voteStateConcurrentHashMap.put(commitMsg.getHash(), flag);
        listenerManager.addEListener(new CommitEventListener());
        listenerManager.publishEvent(new MsgCommitEvent(commitMsg));
    }
}
