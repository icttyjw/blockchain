package edu.ictt.blockchain.socket.packet;

import static org.mockito.Matchers.anyString;

import edu.ictt.blockchain.ApplicationContextProvider;
import edu.ictt.blockchain.Block.block.UpperBlock;
import edu.ictt.blockchain.core.event.ClientRequestEvent;
import edu.ictt.blockchain.core.manager.UDbBlockManager;
import edu.ictt.blockchain.socket.body.upperbody.URpcSimpleBlockBody;

public class UNextBlockPacketBuilder {
	
	public static BlockPacket build() {
		return build(null);
	}

	public static BlockPacket build(String responseId) {
		UpperBlock uBlock = ApplicationContextProvider.getBean(UDbBlockManager.class).getLastBlock();
		String uhash = uBlock.getUpperBlockHash();
		String bhash = uBlock.getuBlockHeader().getBhash();
		
		URpcSimpleBlockBody uRpcSimpleBlockBody = new URpcSimpleBlockBody(uhash, bhash);
		//rpcBlockBody.setResponseMsgId(responseId);
		BlockPacket blockPacket = new PacketBuilder<>().setType(UPacketType.NEXT_BLOCK_INFO_REQUEST).setBody(uRpcSimpleBlockBody).build();
		//生成blockpacket后发布请求事件
		ApplicationContextProvider.publishEvent(new ClientRequestEvent(blockPacket));
		return blockPacket;
	}
}
