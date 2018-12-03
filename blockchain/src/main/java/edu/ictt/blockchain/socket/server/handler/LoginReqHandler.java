package edu.ictt.blockchain.socket.server.handler;

import java.util.Scanner;

import org.tio.core.ChannelContext;

import edu.ictt.blockchain.common.FastJsonUtil;
import edu.ictt.blockchain.socket.body.StateBody;
import edu.ictt.blockchain.socket.common.intf.AbstractBlockHandler;
import edu.ictt.blockchain.socket.packet.BlockPacket;

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
		Scanner sc=new Scanner(System.in);
		String t=sc.nextLine();
		if(t.equals("s"))
		System.out.println("true");
		return null;
	}

}
