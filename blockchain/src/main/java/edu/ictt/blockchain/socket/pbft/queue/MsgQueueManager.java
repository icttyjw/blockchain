package edu.ictt.blockchain.socket.pbft.queue;

import edu.ictt.blockchain.socket.pbft.VoteType;
import edu.ictt.blockchain.socket.pbft.msg.VoteMsg;

public class MsgQueueManager {

	public void push(VoteMsg voteMsg){
		BaseMsgQueue baseMsgQueue=null;
		System.out.println("manager");
		switch(voteMsg.getVoteType()){
		case VoteType.pre:
			System.out.println("pre");
			baseMsgQueue=new PreMsgQueue();
			//System.out.println(8);
			break;
		case VoteType.prepare:
			System.out.println("prepare");
			baseMsgQueue=new PrepareMsgQueue();
			break;
		case VoteType.commit:
			System.out.println("commit");
			baseMsgQueue=new CommitMsgQueue();
			break;
		default:
			break;
		}
		//System.out.println(5);
		if(baseMsgQueue==null)
		System.out.println(6);
		if(baseMsgQueue!=null){
			baseMsgQueue.push(voteMsg);
		}
	}
}
