package edu.ictt.blockchain.socket.client.upperhandler;

import static org.hamcrest.CoreMatchers.nullValue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.utils.json.Json;

import edu.ictt.blockchain.ApplicationContextProvider;
import edu.ictt.blockchain.Block.block.Block;
import edu.ictt.blockchain.Block.block.UpperBlock;
import edu.ictt.blockchain.Block.check.UCheckerManager;
import edu.ictt.blockchain.core.event.UAddBlockEvent;
import edu.ictt.blockchain.socket.body.upperbody.URpcBlockBody;
import edu.ictt.blockchain.socket.body.upperbody.URpcCheckBlockBody;
import edu.ictt.blockchain.socket.client.PacketSender;
import edu.ictt.blockchain.socket.client.handler.TotalBlockInfoResponseHandler;
import edu.ictt.blockchain.socket.common.intf.AbstractBlockHandler;
import edu.ictt.blockchain.socket.packet.BlockPacket;
import edu.ictt.blockchain.socket.packet.NextBlockPacketBuilder;
import edu.ictt.blockchain.socket.packet.UNextBlockPacketBuilder;
import edu.ictt.blockchain.socket.upperpbft.queue.UNextBlockQueue;

/**
 * 请求某一个区块
 * @author zoe
 *
 */
public class UFetchBlockResHandler extends AbstractBlockHandler<URpcBlockBody>{

	private Logger logger = LoggerFactory.getLogger(TotalBlockInfoResponseHandler.class);
	
	@Override
	public Class<URpcBlockBody> bodyClass() {
		
		return URpcBlockBody.class;
	}

	@Override
	public Object handler(BlockPacket packet, URpcBlockBody bsBody, ChannelContext channelContext) throws Exception {
		
		logger.info("[Client校间通信：收到来自<" + bsBody.getAppId() + ">的回复，收到的uBlock为：" + Json.toJson(bsBody));	
		
		//从收到的信息解析出ublock
		UpperBlock uBlock = bsBody.getUbBlock();
		
		//如果ublock为空，则说明对方也没有该Block
		if(uBlock == null) {
			logger.info("[client校间通信：收到来自<" + bsBody.getAppId() + ">的区块为空,对方没有该block或该block还未生成");
		}else {
			//如果不为空，对区块进行校验
			//？？？此处区块的hash为空意味着什么
			if(ApplicationContextProvider.getBean(UNextBlockQueue.class).pop(uBlock.getUpperBlockHash()) == null) return null;
			UCheckerManager uCheckerManager = ApplicationContextProvider.getBean(UCheckerManager.class);
			URpcCheckBlockBody uRpcCheckBlockBody = uCheckerManager.check(uBlock); 
			//校验成功，则存入本地，更新本地最新的一个区块
			 if(uRpcCheckBlockBody.getCode() == 0) {
				 ApplicationContextProvider.publishEvent(new UAddBlockEvent(uBlock));
				 //继续请求下一块
				BlockPacket blockPacket = UNextBlockPacketBuilder.build();
				ApplicationContextProvider.getBean(PacketSender.class).sendUGroup(blockPacket);
			 }
		}
		return null;
	}
	
}
