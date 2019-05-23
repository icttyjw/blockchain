package edu.ictt.blockchain.socket.upperpbft.queue;

import static org.mockito.Matchers.intThat;
import static org.mockito.Matchers.longThat;

import java.util.List;

import javax.annotation.Resource;

import org.checkerframework.checker.i18nformatter.qual.I18nFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import cn.hutool.core.bean.BeanUtil;
import edu.ictt.blockchain.Block.block.Block;
import edu.ictt.blockchain.Block.block.UpperBlock;
import edu.ictt.blockchain.core.event.UAddBlockEvent;
import edu.ictt.blockchain.socket.upperpbft.UVoteType;
import edu.ictt.blockchain.socket.upperpbft.event.UMsgCommitEvent;
import edu.ictt.blockchain.socket.upperpbft.msg.UVoteMsg;

/**
 * Upper层prepare消息队列
 * @author zoe
 *
 */
@Component
public class UPrepareMsgQueue extends UAbastracVoteMsgQueue{

	/**
	 * prepare投票成功后进入Commit队列
	 */
	@Resource 
	private UCommitMsgQueue uCommitMsgQueue;
	
	/**
	 * prepare事件成功后发布
	 */
	@Resource
	private ApplicationEventPublisher eventPublisher;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	void deal(UVoteMsg uVoteMsg, List<UVoteMsg> uVoteMsgs) {
		String uhash = uVoteMsg.getUhash();
		UVoteMsg commitMsg = new UVoteMsg();
		BeanUtil.copyProperties(uVoteMsg, commitMsg);
		commitMsg.setVoteType(UVoteType.commit);
		commitMsg.setAppId("A001");
		//如果后续阶段没有当前投票信息，则允许进入下一阶段
		if(uCommitMsgQueue.hasOhterConfirm(uhash, uVoteMsg.getNumber())) {
			agree(commitMsg,false);
		}else {
			//开始校验拜占庭数量，如果agree的超过2f + 1，就commit
			long agreeCount = uVoteMsgs.stream().filter(UVoteMsg::isAgree).count();
			logger.info("[校间共识投票]：agreecount:"+agreeCount);
			long disagreeCount = uVoteMsgs.size() - agreeCount;
			logger.info("[校间共识投票]：disagreecount:" + disagreeCount);
			//开始发出commit的同意或拒绝的而消息
			 if (agreeCount >= pbftAgreesize()) {
	                agree(commitMsg, true);
	            } else if (disagreeCount >= pbftsize() + 1) {
	                agree(commitMsg, false);
	            }
		}
		
	}
	
	private void agree(UVoteMsg commitMsg, boolean flag) {
		//根据flag的标识设置commitMsg的isagree变量，将投票结果放入本地存储投票结果的集合并发出消息
		logger.info("[校间共识投票]：Prepare阶段完毕，是否是否进入commit的标志是：" + flag);
		commitMsg.setAgree(flag);
		localVoteStateConHash.put(commitMsg.getUhash(), flag);
		eventPublisher.publishEvent(new UMsgCommitEvent(commitMsg));
	}

	/**
	 * 输出prepare启动信息
	 */
	public void log() {
		logger.info("[校间共识投票开始]：启用prepare");
		
	}
	
	/**
	 * 判断下一阶段是否已经存在相同的投票，如果出现则不理会当前投票信息
	 * @param uhash
	 * @param number
	 * @return
	 */
	public boolean otherConfirm(String uhash, long number) {
		return uCommitMsgQueue.hasOhterConfirm(uhash, number);
	}
	
	/**
     * 新区块生成后，clear掉map中number比区块小的所有数据
     *
     * @param addBlockEvent  addBlockEvent
     */
    @Order(4)
    @EventListener(UAddBlockEvent.class)
    public void blockGenerated(UAddBlockEvent uaddBlockEvent){
    	UpperBlock ublock=(UpperBlock) uaddBlockEvent.getSource();
    	clearOldBlockHash(ublock.getuBlockHeader().getUblockNumber());
    }

}
