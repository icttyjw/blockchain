package edu.ictt.blockchain.socket.upperpbft.queue;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import edu.ictt.blockchain.socket.client.ClientStarter;
import edu.ictt.blockchain.socket.pbft.msg.VoteMsg;
import edu.ictt.blockchain.socket.upperpbft.msg.UVoteMsg;

/**
 * pbftMsg队列相关基类
 * @author zoe
 *
 */
@Component
public abstract class UBaseMsgQueue {
	
	@Resource
	private ClientStarter clientStarter;
	
	/**
	 * pbft节点数
	 */
	public int pbftsize() {
		return 0;//clientStarter.pbftSize();
	}
	
	/*
	 * pbft同意数2f+1
	 */
	public int pbftAgreesize(){
		return 1;//clientStarter.pbftAgreeCount();
	}
	
	//将投票信息放入相应的队列
	protected abstract void push(UVoteMsg uVoteMsg);
}
