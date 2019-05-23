package edu.ictt.blockchain.socket.server.upperhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.utils.json.Json;

import edu.ictt.blockchain.ApplicationContextProvider;
import edu.ictt.blockchain.Block.block.UpperBlock;
import edu.ictt.blockchain.core.manager.UDbBlockManager;
import edu.ictt.blockchain.socket.body.upperbody.URpcNextBlockBody;
import edu.ictt.blockchain.socket.body.upperbody.URpcSimpleBlockBody;
import edu.ictt.blockchain.socket.common.intf.AbstractBlockHandler;
import edu.ictt.blockchain.socket.packet.BlockPacket;
import edu.ictt.blockchain.socket.packet.PacketBuilder;
import edu.ictt.blockchain.socket.packet.PacketType;
import edu.ictt.blockchain.socket.packet.UPacketType;
import edu.ictt.blockchain.socket.server.handler.TotalBlockInfoRequestHandler;

/**
 * Upper层获取某个区块下一块的请求，发起方带着自己的lastBlock hash，接收方则将自己的区块中，在传来的hash后面的那块返回回去。<p>
 * 如A传来了3，而我本地有5个区块，则返回区块4。
 *
 */
public class UNextBlockRequestHandler extends AbstractBlockHandler<URpcSimpleBlockBody>{

	private Logger logger = LoggerFactory.getLogger(UTotalBlockInfoRequestHandler.class);
	
	@Override
	public Class<URpcSimpleBlockBody> bodyClass() {
		
		return URpcSimpleBlockBody.class;
	}

	@Override
	public Object handler(BlockPacket packet, URpcSimpleBlockBody bsBody, ChannelContext channelContext)
			throws Exception {
		logger.info("[Server校内校间通信]：收到来自<" + bsBody.getAppId() + ">的<请求下一UBlock>消息，请求者的uhash为：" + Json.toJson(bsBody.getUhash()) + "，请求者的bhash为：" + Json.toJson(bsBody.getBhash()));
		
		//如果传来的Block，如果为null，说明发起方连一个Block都没有
		String uhash = bsBody.getUhash();
		String bhash = bsBody.getBhash();
		
		//查询自己的next block hash，返回给对方，让对方搜集2f+1后确定哪个是对的
		UpperBlock nextUBlock = ApplicationContextProvider.getBean(UDbBlockManager.class).getNextBlockByhash(uhash);
		String nextUHash = null;
		String nextBHash = null;
		if(nextUBlock != null) {
			nextBHash = nextUBlock.getuBlockHeader().getBhash();
			nextUHash = nextUBlock.getUpperBlockHash();
		}
		URpcNextBlockBody uRespBody = new URpcNextBlockBody(nextUHash, nextBHash, uhash, bhash);
		BlockPacket blockPacket = new PacketBuilder<URpcNextBlockBody>().setType(UPacketType.NEXT_BLOCK_INFO_REQUEST).setBody(uRespBody).build();
		Tio.send(channelContext, blockPacket);
		logger.info("[Server校内校间通信]：回复给<" + bsBody.getAppId() + ">，我的nextBlock是:" + uRespBody.toString());
		return null;
	}

}
