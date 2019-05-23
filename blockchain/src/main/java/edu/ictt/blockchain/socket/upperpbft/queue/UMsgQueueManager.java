package edu.ictt.blockchain.socket.upperpbft.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import edu.ictt.blockchain.App;
import edu.ictt.blockchain.ApplicationContextProvider;
import edu.ictt.blockchain.socket.upperpbft.UVoteType;
import edu.ictt.blockchain.socket.upperpbft.msg.UVoteMsg;

/**
 * 根据voteMsg中投票类型将投票信息放入相应队列处理
 *
 */
@Component
public class UMsgQueueManager {
	
		Logger logger = LoggerFactory.getLogger(getClass());
		
		public void push(UVoteMsg uMsg) {
			UBaseMsgQueue uMsgQueuequeue = null;
			logger.info("[共识-投票信息处理，当前投票类型为：]" + uMsg.getVoteType());
			switch (uMsg.getVoteType()) {
			case UVoteType.pre:
				uMsgQueuequeue=ApplicationContextProvider.getBean(UPreMsgQueue.class);
				break;
			case UVoteType.prepare:
				uMsgQueuequeue=ApplicationContextProvider.getBean(UPrepareMsgQueue.class);
				break;
			case UVoteType.commit:
				uMsgQueuequeue=ApplicationContextProvider.getBean(UCommitMsgQueue.class);
				break;
			default:
				break;
			}
			if(uMsgQueuequeue!=null) {
				uMsgQueuequeue.push(uMsg);
			}
		}
}
