package edu.ictt.blockchain.socket.pbft.queue;

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
import edu.ictt.blockchain.common.timer.TimerManager;
import edu.ictt.blockchain.core.event.AddBlockEvent;
import edu.ictt.blockchain.socket.pbft.VoteType;
import edu.ictt.blockchain.socket.pbft.event.MsgPrepareEvent;
import edu.ictt.blockchain.socket.pbft.msg.VoteMsg;
import edu.ictt.blockchain.socket.pbft.msg.VotePreMsg;
/*
 * pre队列处理
 */

@Component
public class PreMsgQueue extends BaseMsgQueue{

	@Resource
	private PrepareMsgQueue prepareMsgQueue;
	@Resource
	private ApplicationEventPublisher eventPublisher;
	
	private ConcurrentHashMap<String, VotePreMsg> blockConcurrentHashMap = new ConcurrentHashMap<>();
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	protected void push(VoteMsg voteMsg) {
		 //该队列里的是votePreMsg
        VotePreMsg votePreMsg = JSONObject.parseObject(JSONObject.toJSONString(voteMsg),VotePreMsg.class);
        logger.info("[共识投票]：votepremsg: "+votePreMsg);
        String hash = votePreMsg.getHash();
        //避免收到重复消息
        if (blockConcurrentHashMap.get(hash) != null) {
            return;
        }
        prepareMsgQueue.log();
        //但凡是能进到该push方法的，都是通过基本校验的，但在并发情况下可能会相同number的block都进到投票队列中
        //需要对新进来的Vote信息的number进行校验，如果在比prepre阶段靠后的阶段中，已经出现了认证OK的同number的vote，则拒绝进入该队列
        if (prepareMsgQueue.otherConfirm(hash, voteMsg.getNumber())) {
            logger.info("[共识投票]：拒绝进入Prepare阶段，hash为" + hash);
            return;
        }
        // 检测脚本是否正常
        //存入Pre集合中
        blockConcurrentHashMap.put(hash, votePreMsg);
        //加入Prepare行列，推送给所有人
        VoteMsg prepareMsg = new VoteMsg();
        BeanUtil.copyProperties(voteMsg, prepareMsg);
        prepareMsg.setVoteType(VoteType.prepare);
        prepareMsg.setAppId("ddd");
        eventPublisher.publishEvent(new MsgPrepareEvent(prepareMsg));
	}

	 /**
     * 根据hash，得到内存中的Block信息
     *
     * @param hash
     *         hash
     * @return Block
     */
    public Block findByHash(String hash) {
        VotePreMsg votePreMsg = blockConcurrentHashMap.get(hash);
        if (votePreMsg != null) {
            return votePreMsg.getBlock();
        }
        return null;
    }
    @Order(3)
    @EventListener(AddBlockEvent.class)
    public void blockGenerated(AddBlockEvent addBlockEvent) {
        Block block = (Block) addBlockEvent.getSource();
        long number = block.getBlockHeader().getBlockNumber();
        TimerManager.schedule(() -> {
            for (String key : blockConcurrentHashMap.keySet()) {
                if (blockConcurrentHashMap.get(key).getNumber() <= number) {
                    blockConcurrentHashMap.remove(key);
                }
            }
            return null;
        },2000);
    }
}
