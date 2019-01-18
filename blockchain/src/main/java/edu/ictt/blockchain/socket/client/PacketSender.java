package edu.ictt.blockchain.socket.client;


import edu.ictt.blockchain.socket.packet.BlockPacket;
import org.tio.client.ClientGroupContext;
import org.tio.core.Tio;

import static edu.ictt.blockchain.socket.client.ClientContextConfig.clientGroupContext;
import static edu.ictt.blockchain.socket.common.Const.GROUP_NAME;

/**
 * 发送消息的工具类
 */

public class PacketSender {
	
    

    public static void sendGroup(String groupname,BlockPacket blockPacket) {
        //对外发出client请求事件
        //ApplicationContextProvider.publishEvent(new ClientRequestEvent(blockPacket));
        //发送到一个group
        Tio.sendToGroup(clientGroupContext, groupname, blockPacket);
    }

}
