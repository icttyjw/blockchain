package edu.ictt.blockchain.socket.client;


import edu.ictt.blockchain.ApplicationContextProvider;
import edu.ictt.blockchain.core.event.ClientRequestEvent;
import edu.ictt.blockchain.socket.packet.BlockPacket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.tio.client.ClientGroupContext;
import org.tio.core.Tio;

import static edu.ictt.blockchain.common.Const.GROUP_NAME;

import javax.annotation.Resource;

/**
 * 发送消息的工具类
 */
@Component
public class PacketSender {
	
    @Resource
    private ClientGroupContext clientGroupContext;

    private Logger logger=LoggerFactory.getLogger(getClass());
    
    public void sendGroup(BlockPacket blockPacket) {
        //对外发出client请求事件
    	logger.info("[通信]：send group");
    	
        //ApplicationContextProvider.publishEvent(new ClientRequestEvent(blockPacket));
        //发送到一个group
        Tio.sendToGroup(clientGroupContext, GROUP_NAME, blockPacket);
    }

}
