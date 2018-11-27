package edu.ictt.blockchain.socket.client.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.utils.json.Json;

import edu.ictt.blockchain.bean.Runstate;
import edu.ictt.blockchain.common.FastJsonUtil;
import edu.ictt.blockchain.socket.body.BaseBody;
import edu.ictt.blockchain.socket.body.StateBody;
import edu.ictt.blockchain.socket.common.intf.AbstractBlockHandler;
import edu.ictt.blockchain.socket.packet.BlockPacket;

public class HeartbeatHandler extends AbstractBlockHandler<StateBody> {

	private static Logger log = LoggerFactory.getLogger(HeartbeatHandler.class);

	/**
	 * @param args
	 * @author tanyaowu
	 */
	public static void main(String[] args) {

	}

	/**
	 *
	 * @author tanyaowu
	 */
	public HeartbeatHandler() {
	}

	/**
	 * @return
	 * @author tanyaowu
	 */
	

	/**
	 * @param packet
	 * @param bsBody
	 * @param channelContext
	 * @return
	 * @throws Exception
	 * @author tanyaowu
	 */
	public Object handler(BlockPacket packet, StateBody bsBody, ChannelContext channelContext) throws Exception {
		//心跳消息,啥也不用做
		System.out.println("ff");
		System.out.println(FastJsonUtil.toJSONString(bsBody));
		return null;
	}

	@Override
	public Class<StateBody> bodyClass() {
		// TODO Auto-generated method stub
		return StateBody.class;
	}
}
