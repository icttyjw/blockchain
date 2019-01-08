package edu.ictt.blockchain.socket.pbft;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import edu.ictt.blockchain.socket.pbft.msg.VoteMsg;
import edu.ictt.blockchain.socket.pbft.queue.MsgQueueManager;

/*
 * 各个阶段的队列
 */
public class Message {

	/*
	 * pre队列信息
	 */
	//public static ConcurrentHashMap<String, VoteMsg> blockConcurrentHashMap=new ConcurrentHashMap<String, VoteMsg>();
	
	public static MsgQueueManager msgQueueManager=new MsgQueueManager();
}
