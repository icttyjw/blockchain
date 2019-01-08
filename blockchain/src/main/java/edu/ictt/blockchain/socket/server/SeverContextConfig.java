package edu.ictt.blockchain.socket.server;

import org.tio.client.ClientChannelContext;
import org.tio.core.ChannelContext;
import org.tio.core.Node;
import org.tio.core.intf.Packet;
import org.tio.server.ServerGroupContext;
import org.tio.server.TioServer;
import org.tio.server.intf.ServerAioHandler;
import org.tio.server.intf.ServerAioListener;

public class SeverContextConfig {

	public static ServerGroupContext serverGroupContext;
	static {
		ServerAioListener serverAioListener=new BlockServerAioListener();
		ServerAioHandler serverAioHandler=new BlockServerAioHandler();
		serverGroupContext=new ServerGroupContext(serverAioHandler, serverAioListener);
		serverGroupContext.setHeartbeatTimeout(0);
	}
	
	public void connect(ServerGroupContext serverGroupContext,Node node,String group){
		TioServer tioserver=new TioServer(serverGroupContext);
		
	}
}
