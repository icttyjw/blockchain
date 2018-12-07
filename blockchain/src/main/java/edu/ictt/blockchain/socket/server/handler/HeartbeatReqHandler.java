package edu.ictt.blockchain.socket.server.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.utils.json.Json;

import edu.ictt.blockchain.bean.Runstate;
import edu.ictt.blockchain.common.FastJsonUtil;
import edu.ictt.blockchain.socket.body.BaseBody;
import edu.ictt.blockchain.socket.body.StateBody;
import edu.ictt.blockchain.socket.common.Const;
import edu.ictt.blockchain.socket.common.intf.AbstractBlockHandler;
import edu.ictt.blockchain.socket.packet.BlockPacket;
import edu.ictt.blockchain.socket.packet.PacketType;

public class HeartbeatReqHandler extends AbstractBlockHandler<StateBody>{

	private static Logger log = LoggerFactory.getLogger(HeartbeatReqHandler.class);

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
	public HeartbeatReqHandler() {
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
		//心跳交流信息，顺便测试了多线程
		System.out.println(FastJsonUtil.toJSONString(bsBody));
		StateBody sb=new StateBody();
		sb.setId("1");
		sb.setIp("123");
		sb.setName("fff");
		//System.out.println(FastJsonUtil.toJSONString(sb));
		BlockPacket bs=new BlockPacket();
		bs.setType(PacketType.HEART_BEAT);
		bs.setBody(FastJsonUtil.toJSONString(sb).getBytes(Const.CHARSET));
		System.out.println(channelContext.getClientNode().getIp());
		Tio.send(channelContext, bs);
		return null;
	}

	@Override
	public Class<StateBody> bodyClass() {
		// TODO Auto-generated method stub
		return StateBody.class;
	}
}
