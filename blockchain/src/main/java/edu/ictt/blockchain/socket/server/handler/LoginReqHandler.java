package edu.ictt.blockchain.socket.server.handler;

import java.util.Scanner;

import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.core.Tio;
import org.tio.utils.lock.SetWithLock;

import edu.ictt.blockchain.common.Const;
import edu.ictt.blockchain.common.FastJsonUtil;
import edu.ictt.blockchain.socket.body.StateBody;
import edu.ictt.blockchain.socket.client.ClientContextConfig;
import edu.ictt.blockchain.socket.common.intf.AbstractBlockHandler;
import edu.ictt.blockchain.socket.packet.BlockPacket;
import edu.ictt.blockchain.socket.packet.PacketType;

public class LoginReqHandler extends AbstractBlockHandler<StateBody>{

	@Override
	public Class<StateBody> bodyClass() {
		// TODO Auto-generated method stub
		return StateBody.class;
	}

	@Override
	public Object handler(BlockPacket packet, StateBody bsBody, ChannelContext channelContext) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(FastJsonUtil.toJSONString(bsBody));
		//Scanner sc=new Scanner(System.in);
		//String t=sc.nextLine();
		//if(t.equals("s"))
		//System.out.println("true");
		System.out.println(channelContext.getClientNode().getIp());
		BlockPacket pack=new BlockPacket();
 		pack.setType(PacketType.Connect_Request);
		//Tio.sendToGroup(clientGroupContext, Const.GROUP_SCHOOL, pack);
		//Tio.bindGroup(channelContext, "s");
		//GroupContext groupContext=null;
		//SetWithLock<ChannelContext> setWithLock=Tio.getAllChannelContexts(groupContext);
		return null;
	}

}
