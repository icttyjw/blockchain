package edu.ictt.blockchain.socket.pbft.queue;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ictt.blockchain.bean.Block;
import edu.ictt.blockchain.socket.pbft.msg.VoteMsg;

import static edu.ictt.blockchain.socket.pbft.Message.findByHash;
/*
 * commit队列处理
 * 每个节点收到超过2f+1个不同节点（包括自己）的commit消息后，就认为该区块已经达成一致，进入committed状态，并将其持久化到区块链数据库中
 */
public class CommitMsgQueue extends AbstractVoteMsgQueue{

	/*
	 * 
	 * 要看完整跑一遍流程把Block部分注释掉就行了
	 */
	//private PreMsgQueue preMsgQueue;//=new PreMsgQueue();
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	void deal(VoteMsg voteMsg, List<VoteMsg> voteMsgs) {
		System.out.println("commit");
		 String hash = voteMsg.getHash();
		 System.out.println("get commit block‘s hash");
	        //通过校验agree数量，来决定是否在本地生成Block
	        long count = voteMsgs.stream().filter(VoteMsg::isAgree).count();
	        logger.info("已经commit为true的数量为:"+ count);
	        System.out.println("已经commit为true的数量为:"+ count);
	        //System.out.println(count);
	        if (count >= pbftAgreesize()) {
	            Block block = findByHash(hash);
	           // if (block == null) {
	           //     return;
	           // }
	            //本地落地
	            voteStateConcurrentHashMap.put(hash, true);
	            //发布生成区块的事件
	        }
	        System.out.println("commit end");
	}
	/*
	 public void blockGenerated(AddBlockEvent addBlockEvent) {
	        Block block = (Block) addBlockEvent.getSource();
	        clearOldBlockHash(block.getBlockHeader().getNumber());
	    }
	*/
}
