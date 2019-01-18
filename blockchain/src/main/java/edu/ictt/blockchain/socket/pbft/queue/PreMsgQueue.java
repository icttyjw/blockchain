package edu.ictt.blockchain.socket.pbft.queue;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSONObject;

import cn.hutool.core.bean.BeanUtil;
import edu.ictt.blockchain.bean.Block;
import edu.ictt.blockchain.common.timer.TimerManager;
import edu.ictt.blockchain.socket.pbft.VoteType;
import edu.ictt.blockchain.socket.pbft.event.MsgPrepareEvent;
import edu.ictt.blockchain.socket.pbft.listener.PrepareEventListener;
import edu.ictt.blockchain.socket.pbft.msg.VoteMsg;
import edu.ictt.blockchain.socket.pbft.msg.VotePreMsg;

import static edu.ictt.blockchain.socket.pbft.Message.blockConcurrentHashMap;
/*
 * pre队列处理
 */

public class PreMsgQueue extends BaseMsgQueue{

	private PrepareMsgQueue prepareMsgQueue=new PrepareMsgQueue();
	
	private ListenerManager listenerManager=new ListenerManager();
	
	
	
	@Override
	public void push(VoteMsg voteMsg) {
		System.out.println("pre");
		 //该队列里的是votePreMsg
        VotePreMsg votePreMsg = JSONObject.parseObject(JSONObject.toJSONString(voteMsg),VotePreMsg.class);
        System.out.println("pre");
        
        String hash = votePreMsg.getHash();
        System.out.println(votePreMsg.getNumber());
        //避免收到重复消息
        if (blockConcurrentHashMap.get(hash) != null) {
        	System.out.println("pre null");
            return;
        }
        System.out.println(1);
        //但凡是能进到该push方法的，都是通过基本校验的，但在并发情况下可能会相同number的block都进到投票队列中
        //需要对新进来的Vote信息的number进行校验，如果在比prepre阶段靠后的阶段中，已经出现了认证OK的同number的vote，则拒绝进入该队列
        if (prepareMsgQueue.otherConfirm(hash, voteMsg.getNumber())) {
            //logger.info("拒绝进入Prepare阶段，hash为" + hash);
        	System.out.println("pre exist");
            return;
        }
        // 检测脚本是否正常
        System.out.println("pre");
        //存入Pre集合中
        blockConcurrentHashMap.put(hash, votePreMsg);
        System.out.println("try publish");
        //加入Prepare行列，推送给所有人
        VoteMsg prepareMsg = new VoteMsg();
        BeanUtil.copyProperties(voteMsg, prepareMsg);
        prepareMsg.setVoteType(VoteType.prepare);
        //prepareMsg.setAppId(AppId.value);
        listenerManager.addEListener(new PrepareEventListener());
        listenerManager.publishEvent(new MsgPrepareEvent(prepareMsg));
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
    /*
    public void blockGenerated(AddBlockEvent addBlockEvent) {
        Block block = (Block) addBlockEvent.getSource();
        int number = block.getBlockHeader().getNumber();
        TimerManager.schedule(() -> {
            for (String key : blockConcurrentHashMap.keySet()) {
                if (blockConcurrentHashMap.get(key).getNumber() <= number) {
                    blockConcurrentHashMap.remove(key);
                }
            }
            return null;
        },2000);
    }*/
}
