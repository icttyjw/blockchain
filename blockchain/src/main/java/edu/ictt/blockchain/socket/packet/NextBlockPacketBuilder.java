package edu.ictt.blockchain.socket.packet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ictt.blockchain.ApplicationContextProvider;
import edu.ictt.blockchain.core.event.ClientRequestEvent;
import edu.ictt.blockchain.core.manager.DbBlockManager;
import edu.ictt.blockchain.socket.body.RpcSimpleBlockBody;

public class NextBlockPacketBuilder {

	 public static BlockPacket build() {
	        return build(null);
	    }

	    public static BlockPacket build(String responseId) {
	        String hash = ApplicationContextProvider.getBean(DbBlockManager.class).getLastBlockHash();

	        RpcSimpleBlockBody rpcBlockBody = new RpcSimpleBlockBody(hash);
	        //rpcBlockBody.setResponseMsgId(responseId);
	        BlockPacket blockPacket = new PacketBuilder<>().setType(PacketType.NEXT_BLOCK_INFO_REQUEST).setBody
	                (rpcBlockBody).build();
	        //发布client请求事件
	        ApplicationContextProvider.publishEvent(new ClientRequestEvent(blockPacket));
	        return blockPacket;
	    }
}
