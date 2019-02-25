package edu.ictt.blockchain.socket.pbft.queue;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import edu.ictt.blockchain.Block.block.Block;
import edu.ictt.blockchain.core.event.AddBlockEvent;
import edu.ictt.blockchain.socket.pbft.msg.VoteMsg;

import static edu.ictt.blockchain.socket.pbft.Message.findByHash;
/*
 * commit队列处理
 */
@Component
public class CommitMsgQueue extends AbstractVoteMsgQueue{

	/*
	 * 
	 * 要看完整跑一遍流程把Block部分注释掉就行了
	 */
	@Resource
	private PreMsgQueue preMsgQueue;//=new PreMsgQueue();
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	void deal(VoteMsg voteMsg, List<VoteMsg> voteMsgs) {
		System.out.println("commit");
		 String hash = voteMsg.getHash();
		 System.out.println("commit end");
	        //通过校验agree数量，来决定是否在本地生成Block
	        long count = voteMsgs.stream().filter(VoteMsg::isAgree).count();
	        logger.info("已经commit为true的数量为:"+count);
	        System.out.println(count);
	        if (count >= pbftAgreesize()) {
	            Block block = preMsgQueue.findByHash(hash);
	            if (block == null) {
	                return;
	            }
	            //本地落地
	            voteStateConcurrentHashMap.put(hash, true);
	            //发布生成区块的事件
	        }
	        System.out.println("commit end");
	}
	
	//@Order(3)
	//@EventListener(AddBlockEvent.class)
	 public void blockGenerated(AddBlockEvent addBlockEvent) {
	        Block block = (Block) addBlockEvent.getSource();
	        clearOldBlockHash(block.getBlockHeader().getBlockMumber());
	    }
	
}
