package edu.ictt.blockchain.socket.upperpbft.queue;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.Mockito.RETURNS_MOCKS;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ictt.blockchain.Block.record.NewDegreeInfo;
import edu.ictt.blockchain.common.timer.TimerManager;
import edu.ictt.blockchain.socket.upperpbft.msg.UVoteMsg;

/**
 * pbft投票队列的抽象基类
 * @author zoe
 *	TODO return给出更具体的数字代码提示问题症状
 */
public abstract class UAbastracVoteMsgQueue extends UBaseMsgQueue{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 存储节点们对区块（hash）的投票集合。包括自己的。
	 * @param uMsg
	 */
	protected ConcurrentHashMap<String, List<UVoteMsg>> voteMsgConcurrentHashMap = new ConcurrentHashMap<>();
	
	/**
     * 存储本节点已确认状态的hash的集合，即本节点已对外广播过允许或拒绝投票的消息了
     */
	protected ConcurrentHashMap<String,Boolean> localVoteStateConHash = new ConcurrentHashMap<>();

	/**
	 * 本地对投票信息进行处理,不同队列进行对应的处理
	 * @param uVoteMsg
	 * @param uVoteMsgs
	 */
	abstract void deal(UVoteMsg uVoteMsg,List<UVoteMsg> uVoteMsgs);
	
	/**
	 *  对投票信息的预处理：包括检查当前投票的list是否存在，list中是否已存在当前投票，本节点是否已对该次投票的信息进行过投票
	 */
	protected void push(UVoteMsg uVoteMsg) {

		
		//先检查是否存在对当前uhash的投票list
		String uhash = uVoteMsg.getUhash();
		String bhash = uVoteMsg.getBhash();
		List<UVoteMsg> uVoteMsgs = voteMsgConcurrentHashMap.get(uhash);
		
		//如果当前uhash的投票list不存在，则新建投票list并放入投票队列
		if(uVoteMsgs == null ) {
			uVoteMsgs = new ArrayList<>();
			uVoteMsgs.add(uVoteMsg);
		}else {
			//投票集合存在，要检查当前uhash中封装的bhash是否和投票list中封装的bhash相同
			if(uVoteMsgs.get(0).getBhash().equals(uVoteMsg.getBhash())) {
				//hash对应关系正确，则检查该投票是否已经存在于投票list中:此处用节点id进行检查
				for(UVoteMsg uMsg:uVoteMsgs) {
					if(uMsg.getAppId().equals(uVoteMsg.getAppId())) {
						return;
					}
				}
			}else {
				return;
			}
		}
		
		//如果投票list中不存在当前投票，则将当前投票加入队列中
		uVoteMsgs.add(uVoteMsg);
		
		//判断本地是否进行了当前次投票
		if(localVoteStateConHash.get(uhash) != null) {return;}
		
		//如果本地未进行投票，则本地进行该次投票
		//TODO 如果本地没有收到该次的区块，又从这里强行开始投票，那么再之后应当先判断本地是否有当前次区块
		deal(uVoteMsg, uVoteMsgs);
		
	}

	/**
	 * 确保当前投票阶段的block与后续阶段的block是正确逻辑的block，而不是已经投过票的block。并且下一阶段不存在对该block的投票，否则该投票信息作废
	 */
	public boolean hasOhterConfirm(String hash, long l) {
		//遍历该阶段的所有投票信息
		for(String key: voteMsgConcurrentHashMap.keySet()) {
			//如果下一阶段存在同一个hash的投票，则不理会。把判重交给下一队列。
			if(hash.equals(key)) {
				continue;
				//判断下一阶段的number是否比当前投票的小，如果小，则区块顺序逻辑正确，则不理会
			}if(voteMsgConcurrentHashMap.get(key).get(0).getNumber() < l) {
				continue;
			}
			//只有别的>=number的Block已经达成共识了，则返回true，那么将会拒绝该hash进入下一阶段
			if(localVoteStateConHash.get(key)!= null && localVoteStateConHash.get(key)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 清理队列中对旧的blockhash的投票信息
	 */
	protected void clearOldBlockHash(long number) {
		TimerManager.schedule(()->{
			for(String key: voteMsgConcurrentHashMap.keySet()) {
				if (voteMsgConcurrentHashMap.get(key).get(0).getNumber() <= number) {
                    voteMsgConcurrentHashMap.remove(key);
                    localVoteStateConHash.remove(key);
                }
			}
			return null;
		}, 2000);
	}
}
