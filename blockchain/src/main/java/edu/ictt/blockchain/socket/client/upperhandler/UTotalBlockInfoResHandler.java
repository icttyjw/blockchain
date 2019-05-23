package edu.ictt.blockchain.socket.client.upperhandler;

import org.tio.core.ChannelContext;

import edu.ictt.blockchain.socket.body.upperbody.URpcBlockBody;
import edu.ictt.blockchain.socket.common.intf.AbstractBlockHandler;
import edu.ictt.blockchain.socket.packet.BlockPacket;

/**
 * 请求全部Upper层区块
 * @author zoe
 *
 */
public class UTotalBlockInfoResHandler extends AbstractBlockHandler<URpcBlockBody>{

	@Override
	public Class<URpcBlockBody> bodyClass() {
		return URpcBlockBody.class;
	}

	@Override
	public Object handler(BlockPacket packet, URpcBlockBody bsBody, ChannelContext channelContext) throws Exception {
		
		//TODO check合法性
        //TODO response
		return null;
	}
	
}
