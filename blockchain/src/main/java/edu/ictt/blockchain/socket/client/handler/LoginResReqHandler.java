package edu.ictt.blockchain.socket.client.handler;

import org.tio.core.ChannelContext;
import org.tio.core.Tio;

import edu.ictt.blockchain.socket.body.StateBody;
import edu.ictt.blockchain.socket.common.intf.AbstractBlockHandler;
import edu.ictt.blockchain.socket.packet.BlockPacket;

public class LoginResReqHandler extends AbstractBlockHandler<StateBody>{

	@Override
	public Class<StateBody> bodyClass() {
		// TODO Auto-generated method stub
		return StateBody.class;
	}

	@Override
	public Object handler(BlockPacket packet, StateBody bsBody, ChannelContext channelContext) throws Exception {
		// TODO Auto-generated method stub
		//Tio.bindGroup(channelContext, group);
		return null;
	}

}
