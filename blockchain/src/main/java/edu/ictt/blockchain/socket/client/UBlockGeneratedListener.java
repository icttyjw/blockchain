package edu.ictt.blockchain.socket.client;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.tio.core.*;

import edu.ictt.blockchain.Block.block.Block;
import edu.ictt.blockchain.core.event.AddBlockEvent;

import edu.ictt.blockchain.socket.body.upperbody.UBlockBody;
import edu.ictt.blockchain.socket.packet.BlockPacket;
import edu.ictt.blockchain.socket.packet.PacketBuilder;
import edu.ictt.blockchain.socket.packet.PacketType;
import edu.ictt.blockchain.socket.packet.UPacketType;

/**
 * 本地新生成区块后，根据规则将本地区块定期发送到校级节点
 * 目的：（1）保证校内块能及时上链：
 * （2）尽可能减小产块时的冲突（目前的产块是集体并发的产块）：
 * @author zoe
 *
 */
@Component
public class UBlockGeneratedListener {
	
	
	//普通区块包
    @Resource
    private PacketSender packetSender;
    
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * TODO 想法：定义一个区块的send值，由区块的产生时间和本地区块数量决定，值越高，就越有可能被发送出去
     * @param addBlockEvent
     */
    
    @Order(3)
    @EventListener(AddBlockEvent.class)
    public void blockGenerated(AddBlockEvent addBlockEvent) {
        Block block = (Block) addBlockEvent.getSource();
        BlockPacket blockPacket = new PacketBuilder<>().setType(UPacketType.RECEIVE_BLOCK).
        		setBody(new UBlockBody(block, block.getBlockHash())).build();
        logger.info("[Client校内-校间通信]：发送区块给校级节点，当前时间为" + System.currentTimeMillis());
        

        //目前先做简单处理：即每落地一个区块，就发送给校间
        //单节点sendGroup
        packetSender.sendGroup(blockPacket);
        //packetSender.sendMainNode(blockPacket);
    }
}
