package edu.ictt.blockchain.socket.client.upperhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;

import edu.ictt.blockchain.socket.body.common.StateBody;
import edu.ictt.blockchain.socket.common.intf.AbstractBlockHandler;
import edu.ictt.blockchain.socket.packet.BlockPacket;

/**
 * 登陆进组请求
 * @author zoe
 *
 */
public class UloginResHandler extends AbstractBlockHandler<StateBody>{
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public Class<StateBody> bodyClass() {
		// TODO Auto-generated method stub
		logger.info("[Client校间共识]：登陆进组请求");
		return null;
	}

	@Override
	public Object handler(BlockPacket packet, StateBody bsBody, ChannelContext channelContext) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
