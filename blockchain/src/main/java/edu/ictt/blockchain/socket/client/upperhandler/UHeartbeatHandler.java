package edu.ictt.blockchain.socket.client.upperhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;

import edu.ictt.blockchain.socket.body.common.StateBody;
import edu.ictt.blockchain.socket.client.handler.HeartbeatHandler;
import edu.ictt.blockchain.socket.common.intf.AbstractBlockHandler;
import edu.ictt.blockchain.socket.packet.BlockPacket;

/**
 * 心跳消息
 * @author zoe
 *
 */
public class UHeartbeatHandler extends AbstractBlockHandler<StateBody>{
	
	private static Logger logger = LoggerFactory.getLogger(HeartbeatHandler.class);
	
	public UHeartbeatHandler() {}

	@Override
	public Class<StateBody> bodyClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object handler(BlockPacket packet, StateBody bsBody, ChannelContext channelContext) throws Exception {
		logger.info("-----------Client心跳消息-------------");
		return null;
	}
}
