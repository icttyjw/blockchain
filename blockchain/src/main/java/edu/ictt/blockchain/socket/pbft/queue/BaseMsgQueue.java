package edu.ictt.blockchain.socket.pbft.queue;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import edu.ictt.blockchain.socket.client.ClientStarter;
import edu.ictt.blockchain.socket.pbft.msg.VoteMsg;

@Component
public abstract class BaseMsgQueue {

	@Resource
	private ClientStarter clientStarter;
	
	/*
	 * 应该有统计来源，这里暂时手写
	 * pbft中拜占庭节点数f，总结点数3f+1
	 * size为2f
	 */
	public int pbftsize(){
		return 0;//clientStarter.pbftSize();
	}
	/*
	 * pbft拜占庭同意数2f+1
	 */
	public int pbftAgreesize(){
		return 1;//clientStarter.pbftAgreeCount();
	}
	
	protected abstract void push(VoteMsg voteMsg);
}
