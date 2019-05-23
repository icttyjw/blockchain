package edu.ictt.blockchain.socket.server.upperhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;

import edu.ictt.blockchain.ApplicationContextProvider;
import edu.ictt.blockchain.Block.block.Block;
import edu.ictt.blockchain.socket.block.queue.BlockQueue;
import edu.ictt.blockchain.socket.body.upperbody.UBlockBody;
import edu.ictt.blockchain.socket.common.intf.AbstractBlockHandler;
import edu.ictt.blockchain.socket.packet.BlockPacket;

/**
 * 上层节点收到下层的block后的处理：
 * @author zoe
 *
 */
public class ReceiveBlockHandler extends AbstractBlockHandler<UBlockBody>{

	private Logger logger=LoggerFactory.getLogger(getClass());
	
	@Override
	public Class<UBlockBody> bodyClass() {
		// TODO Auto-generated method stub
		return UBlockBody.class;
	}

	@Override
	public Object handler(BlockPacket packet, UBlockBody bsBody, ChannelContext channelContext) throws Exception {
		// TODO Auto-generated method stub
		Block block = bsBody.getBlock();
		logger.info("[Server校内校内-校级通信]收到来自于<：" +bsBody.getAppId() + ">的区块，区块信息为：[" + block + "]");

		ApplicationContextProvider.getBean(BlockQueue.class).receive(bsBody);
		return null;
	}
	
}
