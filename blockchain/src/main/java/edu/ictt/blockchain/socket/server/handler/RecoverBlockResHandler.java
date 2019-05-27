package edu.ictt.blockchain.socket.server.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;

import edu.ictt.blockchain.ApplicationContextProvider;
import edu.ictt.blockchain.Block.block.Block;
import edu.ictt.blockchain.Block.check.CheckerManager;
import edu.ictt.blockchain.core.manager.DbBlockManager;
import edu.ictt.blockchain.socket.body.lowerbody.RpcBlockBody;
import edu.ictt.blockchain.socket.body.lowerbody.RpcCheckBlockBody;
import edu.ictt.blockchain.socket.body.lowerbody.RpcSimpleBlockBody;
import edu.ictt.blockchain.socket.client.PacketSender;
import edu.ictt.blockchain.socket.common.intf.AbstractBlockHandler;
import edu.ictt.blockchain.socket.packet.BlockPacket;
import edu.ictt.blockchain.socket.packet.PacketBuilder;
import edu.ictt.blockchain.socket.packet.PacketType;

public class RecoverBlockResHandler extends AbstractBlockHandler<RpcSimpleBlockBody>{

	private Logger logger = LoggerFactory.getLogger(RecoverBlockResHandler.class);
	
	@Override
	public Class<RpcSimpleBlockBody> bodyClass() {
		// TODO Auto-generated method stub
		return RpcSimpleBlockBody.class;
	}

	@Override
	public Object handler(BlockPacket packet, RpcSimpleBlockBody rpcBlockBody, ChannelContext channelContext)
			throws Exception {
		 logger.info("[通信]:收到来自于<" + rpcBlockBody.getAppId() + "><请求该Block>消息，block hash为[" + rpcBlockBody.getHash() + "]");
	        Block block = ApplicationContextProvider.getBean(DbBlockManager.class).getBlockByHash(rpcBlockBody.getHash());
	        CheckerManager checkerManager = ApplicationContextProvider.getBean(CheckerManager.class);
            int res = checkerManager.periodcheck(block);
            //校验通过，则存入本地DB，保存新区块
            if (res == 0){
            	BlockPacket blockPacket = new PacketBuilder<>().setType(PacketType.BLOCK_RECOVER_RESPONSE).setBody(new RpcBlockBody(block)).build();
            	 Tio.send(channelContext, blockPacket);
            }else{
            	BlockPacket blockPacket = new PacketBuilder<>().setType(PacketType.BLOCK_RECOVER_REQUEST).setBody(new RpcSimpleBlockBody(rpcBlockBody.getHash())).build();
            	ApplicationContextProvider.getBean(PacketSender.class).sendGroup(blockPacket);
            }
		
		return null;
	}



}
