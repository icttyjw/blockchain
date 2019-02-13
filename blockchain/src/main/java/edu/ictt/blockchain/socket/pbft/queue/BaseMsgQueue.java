package edu.ictt.blockchain.socket.pbft.queue;

import edu.ictt.blockchain.socket.pbft.msg.VoteMsg;

/**
 * 各节点互传的投票消息存储队列基类
 *
 */
public abstract class BaseMsgQueue {

	/*
	 * 应该有统计来源，这里暂时手写
	 * pbft中拜占庭节点数f，总结点数3f+1
	 * size为2f
	 */
	public int pbftsize(){
		return 0;
	}
	/*
	 * pbft拜占庭同意数2f+1
	 */
	public int pbftAgreesize(){
		return 1;
	}
	
	/**
     * 来了新消息
     *
     * @param voteMsg
     *         voteMsg
     */
	public abstract void push(VoteMsg voteMsg);
}
