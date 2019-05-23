package edu.ictt.blockchain.socket.server.upperhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;

import edu.ictt.blockchain.socket.body.upperbody.URpcBlockBody;
import edu.ictt.blockchain.socket.common.intf.AbstractBlockHandler;
import edu.ictt.blockchain.socket.packet.BlockPacket;
import edu.ictt.blockchain.socket.server.handler.TotalBlockInfoRequestHandler;

/**
 * 上层节点收到 获取全部区块信息的请求的处理
 *
 */
public class UTotalBlockInfoRequestHandler extends AbstractBlockHandler<URpcBlockBody>{

	 private Logger logger = LoggerFactory.getLogger(UTotalBlockInfoRequestHandler.class);
	 
	@Override
	public Class<URpcBlockBody> bodyClass() {
		// TODO Auto-generated method stub
		return URpcBlockBody.class;
	}

	@Override
	public Object handler(BlockPacket packet, URpcBlockBody bsBody, ChannelContext channelContext) throws Exception {
		// TODO Auto-generated method stub
		logger.info("[Server校间通信]：收到" + bsBody.getAppId()+ "获取全部区块信息的请求");
		
		//从本地取出区块进行回应
		return null;
	}

}
