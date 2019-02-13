package edu.ictt.blockchain.socket.pbft.queue;

import java.util.List;

import edu.ictt.blockchain.bean.Block;
import edu.ictt.blockchain.socket.pbft.msg.VoteMsg;

import static edu.ictt.blockchain.socket.pbft.Message.findByHash;
/*
 * commit队列处理
 */
public class CommitMsgQueue extends AbstractVoteMsgQueue{

	/*
	 * 
	 * 要看完整跑一遍流程把Block部分注释掉就行了
	 */
	//private PreMsgQueue preMsgQueue;//=new PreMsgQueue();
	
	@Override
	void deal(VoteMsg voteMsg, List<VoteMsg> voteMsgs) {
		System.out.println("commit");
		 String hash = voteMsg.getHash();
		 System.out.println("commit end");
	        //通过校验agree数量，来决定是否在本地生成Block
	        long count = voteMsgs.stream().filter(VoteMsg::isAgree).count();
	        System.out.println(count);
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
