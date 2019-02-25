package edu.ictt.blockchain.socket.pbft;

import static edu.ictt.blockchain.socket.pbft.Message.blockConcurrentHashMap;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import edu.ictt.blockchain.Block.block.Block;
import edu.ictt.blockchain.socket.pbft.msg.VoteMsg;
import edu.ictt.blockchain.socket.pbft.msg.VotePreMsg;
import edu.ictt.blockchain.socket.pbft.queue.MsgQueueManager;

/*
 * 各个阶段的队列
 */
public class Message {

	/*
	 * pre队列信息
	 */
	public static ConcurrentHashMap<String, VotePreMsg> blockConcurrentHashMap=new ConcurrentHashMap<String, VotePreMsg>();

	 
	
	/*
	 * 队列管理
	 */
	public static MsgQueueManager msgQueueManager=new MsgQueueManager();
	
	public static Block findByHash(String hash) {
		System.out.println(blockConcurrentHashMap.size());
        VotePreMsg votePreMsg = blockConcurrentHashMap.get(hash);
        if (votePreMsg != null) {
            return votePreMsg.getBlock();
        }
        return null;
    }
	
}
