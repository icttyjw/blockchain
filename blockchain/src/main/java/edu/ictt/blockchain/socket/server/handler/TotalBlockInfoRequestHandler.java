package edu.ictt.blockchain.socket.server.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.utils.json.Json;

import edu.ictt.blockchain.socket.body.lowerbody.RpcBlockBody;
import edu.ictt.blockchain.socket.common.intf.AbstractBlockHandler;
import edu.ictt.blockchain.socket.packet.BlockPacket;

/**
 * 获取全部区块信息的请求，全网广播
 *
 */
public class TotalBlockInfoRequestHandler extends AbstractBlockHandler<RpcBlockBody>{

	 private Logger logger = LoggerFactory.getLogger(TotalBlockInfoRequestHandler.class);

	    @Override
	    public Class<RpcBlockBody> bodyClass() {
	        return RpcBlockBody.class;
	    }

	    @Override
	    public Object handler(BlockPacket packet, RpcBlockBody rpcBlockBody, ChannelContext channelContext) throws Exception {
	        logger.info("[通信]：收到<请求生成Block的回应>消息", Json.toJson(rpcBlockBody));

	        //TODO check合法性
	        //TODO response

	        return null;
	    }
}
