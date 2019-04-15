package edu.ictt.blockchain.socket.server;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.core.intf.Packet;
import org.tio.server.intf.ServerAioListener;
import org.tio.utils.json.Json;

import edu.ictt.blockchain.socket.common.BlockSessionContext;

/**
 * @author wuweifeng wrote on 2018/3/12.
 * 连接状态的监听器
 * 2017年3月26日 下午8:22:31
 */
public class BlockServerAioListener implements ServerAioListener {
	private static Logger log = LoggerFactory.getLogger(BlockServerAioListener.class);

	@Override
	public void onAfterConnected(ChannelContext channelContext, boolean isConnected, boolean isReconnect) {
		log.info("[通信状态]：onAfterConnected channelContext:{}, isConnected:{}, isReconnect:{}", channelContext, isConnected, isReconnect);
		/*System.out.println(channelContext.getId());
		Scanner sc=new Scanner(System.in);
		String t=sc.nextLine();
		if(t.equals("s"))
		System.out.println("true");
		else
			channelContext.setAttribute(new BlockSessionContext());*/
		//连接后，需要把连接会话对象设置给channelContext
		//
	}

	@Override
	public void onAfterDecoded(ChannelContext channelContext, Packet packet, int i) throws Exception {
		log.info("[通信状态]：onAfterReceived channelContext:{}, packet:{}, packetSize:{}",channelContext,Json.toJson(packet),i);
	}

	@Override
	public void onAfterReceivedBytes(ChannelContext channelContext, int i) throws Exception {
		
	}


	@Override
	public void onAfterSent(ChannelContext channelContext, Packet packet, boolean isSentSuccess) {
		log.info("[通信状态]：onAfterSent channelContext:{}, packet:{}, isSentSuccess:{}", channelContext, Json.toJson(packet), isSentSuccess);
	}

	@Override
	public void onAfterHandled(ChannelContext channelContext, Packet packet, long l) throws Exception {
		
	}

	@Override
	public void onBeforeClose(ChannelContext channelContext, Throwable throwable, String remark, boolean isRemove) {
	}
}
