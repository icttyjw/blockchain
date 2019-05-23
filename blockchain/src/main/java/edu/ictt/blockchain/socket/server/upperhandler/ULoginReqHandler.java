package edu.ictt.blockchain.socket.server.upperhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;

import edu.ictt.blockchain.common.FastJsonUtil;
import edu.ictt.blockchain.socket.body.common.StateBody;
import edu.ictt.blockchain.socket.common.intf.AbstractBlockHandler;
import edu.ictt.blockchain.socket.packet.BlockPacket;
import edu.ictt.blockchain.socket.packet.PacketType;
import edu.ictt.blockchain.socket.packet.UPacketType;

/**
 * 登陆请求响应处理
 * @author zoe
 *
 */
public class ULoginReqHandler extends AbstractBlockHandler<StateBody>{

	private Logger logger = LoggerFactory.getLogger(ULoginReqHandler.class);
	
	@Override
	public Class<StateBody> bodyClass() {
		// TODO Auto-generated method stub
		return StateBody.class;
	}

	@Override
	public Object handler(BlockPacket packet, StateBody bsBody, ChannelContext channelContext) throws Exception {
		logger.info("[Server校内校间通信]：收到节点" + channelContext.getClientNode().getIp() + "的连接请求：" + FastJsonUtil.toJSONString(bsBody));
		
		//把该连接请求广播到组
		BlockPacket blockPacket = new BlockPacket();
		blockPacket.setType(UPacketType.Connect_Request);
		//Scanner sc=new Scanner(System.in);
		//String t=sc.nextLine();
		//if(t.equals("s"))
		//System.out.println("true");
		//Tio.sendToGroup(clientGroupContext, Const.GROUP_SCHOOL, pack);
		//Tio.bindGroup(channelContext, "s");
		//GroupContext groupContext=null;
		//SetWithLock<ChannelContext> setWithLock=Tio.getAllChannelContexts(groupContext);
		return null;
	}

}
