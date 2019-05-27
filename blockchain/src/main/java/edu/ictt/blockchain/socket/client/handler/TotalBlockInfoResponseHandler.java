package edu.ictt.blockchain.socket.client.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.utils.json.Json;

import edu.ictt.blockchain.socket.body.lowerbody.RpcBlockBody;
import edu.ictt.blockchain.socket.common.intf.AbstractBlockHandler;
import edu.ictt.blockchain.socket.packet.BlockPacket;

public class TotalBlockInfoResponseHandler extends AbstractBlockHandler<RpcBlockBody>{
	 private Logger logger = LoggerFactory.getLogger(TotalBlockInfoResponseHandler.class);

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
