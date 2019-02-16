package edu.ictt.blockchain.socket.pbft.queue;

import org.springframework.stereotype.Component;

import edu.ictt.blockchain.socket.pbft.VoteType;
import edu.ictt.blockchain.socket.pbft.msg.VoteMsg;

@Component
public class MsgQueueManager {

	public void push(VoteMsg voteMsg){
		BaseMsgQueue baseMsgQueue=null;
		System.out.println("manager");
		switch(voteMsg.getVoteType()){
		case VoteType.pre:
			baseMsgQueue=new PreMsgQueue();
			//System.out.println(8);
			break;
		case VoteType.prepare:
			baseMsgQueue=new PrepareMsgQueue();
			break;
		case VoteType.commit:
			baseMsgQueue=new CommitMsgQueue();
			break;
		default:
			break;
		}
		if(baseMsgQueue!=null){
			baseMsgQueue.push(voteMsg);
		}
	}
}
