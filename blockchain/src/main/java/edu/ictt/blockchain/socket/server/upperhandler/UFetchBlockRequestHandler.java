package edu.ictt.blockchain.socket.server.upperhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;

import edu.ictt.blockchain.ApplicationContextProvider;
import edu.ictt.blockchain.Block.block.UpperBlock;
import edu.ictt.blockchain.core.manager.UDbBlockManager;
import edu.ictt.blockchain.socket.body.upperbody.URpcBlockBody;
import edu.ictt.blockchain.socket.body.upperbody.URpcSimpleBlockBody;
import edu.ictt.blockchain.socket.common.intf.AbstractBlockHandler;
import edu.ictt.blockchain.socket.packet.BlockPacket;
import edu.ictt.blockchain.socket.packet.PacketBuilder;
import edu.ictt.blockchain.socket.packet.PacketType;
import edu.ictt.blockchain.socket.packet.UPacketType;
import edu.ictt.blockchain.socket.server.handler.FetchBlockRequestHandler;

/**
 * 上层节点收到某个节点请求某个block后的的处理
 * @author zoe
 *
 */
public class UFetchBlockRequestHandler extends AbstractBlockHandler<URpcSimpleBlockBody>{

	private Logger logger = LoggerFactory.getLogger(UFetchBlockRequestHandler.class);
	
	@Override
	public Class<URpcSimpleBlockBody> bodyClass() {
	
		return URpcSimpleBlockBody.class;
	}

	@Override
	public Object handler(BlockPacket packet, URpcSimpleBlockBody bsBody, ChannelContext channelContext)
			throws Exception {
		logger.info("[Server校内校间通信：收到来自于<" + bsBody.getAppId() + "><请求UBlock的消息，uhash为：" + bsBody.getUhash() + "，bhash为：" + bsBody.getBhash() + "]");
		
		//从本地数据库找到该区块
		UpperBlock uBlock = ApplicationContextProvider.getBean(UDbBlockManager.class).getBlockByHash(bsBody.getUhash());
		//打包成blockpacket并发送给请求的节点
		BlockPacket blockPacket = new PacketBuilder<>().setType(UPacketType.FETCH_BLOCK_INFO_RESPONSE).setBody(new URpcBlockBody(uBlock)).build();
		Tio.send(channelContext, blockPacket);
		return null;
	}

}
