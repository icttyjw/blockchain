package edu.ictt.blockchain.socket.client;


import edu.ictt.blockchain.ApplicationContextProvider;
import edu.ictt.blockchain.core.event.ClientRequestEvent;
import edu.ictt.blockchain.socket.packet.BlockPacket;

import org.springframework.stereotype.Component;
import org.tio.client.ClientGroupContext;
import org.tio.core.Tio;

import static edu.ictt.blockchain.common.Const.GROUP_NAME;
import static edu.ictt.blockchain.socket.client.ClientContextConfig.clientGroupContext;

import javax.annotation.Resource;

/**
 * 发送消息的工具类
 */
@Component
public class PacketSender {
	
    @Resource
    private ClientContextConfig clientContextConfig;

    public void sendGroup(BlockPacket blockPacket) {
        //对外发出client请求事件
        ApplicationContextProvider.publishEvent(new ClientRequestEvent(blockPacket));
        //发送到一个group
        Tio.sendToGroup(clientGroupContext, GROUP_NAME, blockPacket);
    }

}