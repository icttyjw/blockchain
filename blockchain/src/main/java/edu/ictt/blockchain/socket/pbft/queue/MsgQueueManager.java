package edu.ictt.blockchain.socket.pbft.queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import edu.ictt.blockchain.ApplicationContextProvider;
import edu.ictt.blockchain.socket.pbft.VoteType;
import edu.ictt.blockchain.socket.pbft.msg.VoteMsg;

@Component
public class MsgQueueManager {

	Logger logger = LoggerFactory.getLogger(getClass());
	public void push(VoteMsg voteMsg){
		BaseMsgQueue baseMsgQueue=null;
		logger.info("vote type"+voteMsg.getVoteType());
		switch(voteMsg.getVoteType()){
		case VoteType.pre:
			baseMsgQueue=ApplicationContextProvider.getBean(PreMsgQueue.class);
			//System.out.println(8);
			break;
		case VoteType.prepare:
			baseMsgQueue=ApplicationContextProvider.getBean(PrepareMsgQueue.class);
			break;
		case VoteType.commit:
			baseMsgQueue=ApplicationContextProvider.getBean(CommitMsgQueue.class);
			break;
		default:
			break;
		}
		if(baseMsgQueue!=null){
			baseMsgQueue.push(voteMsg);
		}
	}
}
