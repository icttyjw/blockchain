package edu.ictt.blockchain.socket.upperpbft.queue;

import static org.mockito.Matchers.longThat;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import edu.ictt.blockchain.ApplicationContextProvider;
import edu.ictt.blockchain.Block.block.UpperBlock;
import edu.ictt.blockchain.core.event.UAddBlockEvent;
import edu.ictt.blockchain.socket.upperpbft.msg.UVoteMsg;

/**
 * Upper层commit消息队列
 * @author zoe
 *
 */
@Component
public class UCommitMsgQueue extends UAbastracVoteMsgQueue{

	/**
	 * 预处理当前投票的队列
	 */
	@Resource
	private UPreMsgQueue uPreMsgQueue;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	void deal(UVoteMsg uVoteMsg, List<UVoteMsg> uVoteMsgs) {
		String uhash = uVoteMsg.getUhash();
		//校验agree的数量，决定是否再本地生成Block
		long count = uVoteMsgs.stream().filter(UVoteMsg::isAgree).count();
		logger.info("[校间共识投票]：已经commit为true的数量为：" + count);
		//检查本地是否已经有该block
		if(count >= pbftAgreesize()) {
			UpperBlock uBlock = uPreMsgQueue.findByHash(uhash);
			if(uBlock==null) {
				return;
			}
			//区块通过共识并落地，发布生成区块的事件
			localVoteStateConHash.put(uhash, true);
			logger.info("[校间共识投票]：区块" + uVoteMsg.getNumber() + "成功落地");
			ApplicationContextProvider.publishEvent(new UAddBlockEvent(uBlock));
		}
	}
	
	/**
     * 新区块生成后，clear掉map中number比区块小的所有数据
     *
     * @param addBlockEvent  addBlockEvent
     */
    @Order(4)
    @EventListener(UAddBlockEvent.class)
    public void blockGenerated(UAddBlockEvent uaddBlockEvent){
    	UpperBlock ublock=(UpperBlock) uaddBlockEvent.getSource();
    	clearOldBlockHash(ublock.getuBlockHeader().getUblockNumber());
    }

}
