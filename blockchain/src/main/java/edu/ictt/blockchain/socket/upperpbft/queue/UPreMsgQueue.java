package edu.ictt.blockchain.socket.upperpbft.queue;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import cn.hutool.core.bean.BeanUtil;
import edu.ictt.blockchain.Block.block.Block;
import edu.ictt.blockchain.Block.block.UpperBlock;
import edu.ictt.blockchain.common.timer.TimerManager;
import edu.ictt.blockchain.core.event.UAddBlockEvent;
import edu.ictt.blockchain.socket.upperpbft.event.UMsgPrepareEvent;
import edu.ictt.blockchain.socket.upperpbft.UVoteType;
import edu.ictt.blockchain.socket.upperpbft.msg.UVoteMsg;
import edu.ictt.blockchain.socket.upperpbft.msg.UVotePreMsg;

/**
 * 共识投票前的预处理
 *
 */
@Component
public class UPreMsgQueue extends UBaseMsgQueue{
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	//预处理后需要push到preparequeue
	@Resource
	private UPrepareMsgQueue uPrepareMsgQueue;
	
	//预处理后需要publish prepare event
	@Resource
	private ApplicationEventPublisher eventPublisher;
	
	//把收到的投票信息都转化为premsg存起来
	private ConcurrentHashMap<String, UVotePreMsg> blockConcurrentHashMap = new ConcurrentHashMap<>();
	
	//此处的push和abstractqueue中的push作用一致，对投票信息进行预处理
	@Override
	protected void push(UVoteMsg uVoteMsg) {
		//先把投票信息转化为premsg
		UVotePreMsg uVotePreMsg = JSONObject.parseObject(JSONObject.toJSONString(uVoteMsg),UVotePreMsg.class);
		logger.info("[校间共识投票预处理]：" + uVotePreMsg);
		//查重，避免收到重复信息;同时要判断uhash和bhash是否对应
		String uhash = uVotePreMsg.getUhash();
		String bhash = uVotePreMsg.getBhash();
		if(blockConcurrentHashMap.get(uhash) != null) {
			if(blockConcurrentHashMap.get(uhash).getBhash().equals(bhash)) {
				return;
			}else {
				logger.info("[校间共识预处理]：该upperBlock与其封装的block不匹配");
				return;
			}
			
		}
		uPrepareMsgQueue.log();
		//查重无误后，判断下一阶段是否存在了相同number的vote，如果出现，则拒绝进入
		
		if(uPrepareMsgQueue.otherConfirm(uhash, uVoteMsg.getNumber())) {
			logger.info("[校间共识投票预处理]：该区块已进入共识阶段，拒绝再次进入Prepare阶段，hash为" + uhash);
			return;
		}
		
		//如果下一阶段不存在，则把该次预处理信息放入pre队列
		blockConcurrentHashMap.put(uhash, uVotePreMsg);
		//并进一步加入prepare队列，推送给所有人
		UVoteMsg prepareMsg = new UVoteMsg();
		BeanUtil.copyProperties(uVoteMsg, prepareMsg);
		prepareMsg.setVoteType(UVoteType.prepare);
		//TODO 这块可以不需要
		prepareMsg.setAppId("ddd");
		eventPublisher.publishEvent(new UMsgPrepareEvent(prepareMsg));
		
	}
	
	/**
     * 根据uhash，得到内存中的upperBlock信息
     *
     * @param hash
     * @return Block
     */
    public UpperBlock findByHash(String uhash) {
        UVotePreMsg uVotePreMsg = blockConcurrentHashMap.get(uhash);
        if (uVotePreMsg != null) {
            return uVotePreMsg.getuBlock();
        }
        return null;
    }
    
    /**
     * 清理比当前要生成的ublock小的区块
     */
    @Order(4)
    @EventListener(UAddBlockEvent.class)
    public void ublockGenerated(UAddBlockEvent uaddBlockEvent) {
    	UpperBlock uBlock = (UpperBlock) uaddBlockEvent.getSource();
    	long uBlockNumber = uBlock.getuBlockHeader().getUblockNumber();
    	TimerManager.schedule(()->{
    		for(String key: blockConcurrentHashMap.keySet()) {
    			if(blockConcurrentHashMap.get(key).getNumber() <= uBlockNumber) {
    				blockConcurrentHashMap.remove(key);
    			}
    		}
    		return null;
    	}, 2000);
    }
    

}
