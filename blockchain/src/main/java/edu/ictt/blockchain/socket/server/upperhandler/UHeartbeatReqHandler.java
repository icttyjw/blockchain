package edu.ictt.blockchain.socket.server.upperhandler;

import org.tio.core.ChannelContext;

import edu.ictt.blockchain.socket.body.common.StateBody;
import edu.ictt.blockchain.socket.common.intf.AbstractBlockHandler;
import edu.ictt.blockchain.socket.packet.BlockPacket;

public class UHeartbeatReqHandler extends AbstractBlockHandler<StateBody>{

	@Override
	public Class<StateBody> bodyClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object handler(BlockPacket packet, StateBody bsBody, ChannelContext channelContext) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
