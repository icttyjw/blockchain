package edu.ictt.blockchain.socket.client;

import java.io.IOException;

import org.tio.client.ClientChannelContext;
import org.tio.client.ClientGroupContext;
import org.tio.client.ReconnConf;
import org.tio.client.TioClient;
import org.tio.core.Node;
import org.tio.core.Tio;

import edu.ictt.blockchain.socket.common.Const;
import edu.ictt.blockchain.socket.packet.BlockPacket;
import edu.ictt.blockchain.socket.packet.PacketType;

public class ClientStarter {

	static String serverIp = "127.0.0.1";
	static int serverPort = Const.PORT;

	private static Node serverNode = new Node(serverIp, serverPort);

	//用来自动连接的，不想自动连接请设为null
	private static ReconnConf reconnConf = new ReconnConf(5000);

	private static BlockClientAioHandler bcah=new BlockClientAioHandler();
	private static BlockClientAioListener bcal=new BlockClientAioListener();
	private static ClientGroupContext clientGroupContext = new ClientGroupContext(bcah, bcal, reconnConf);
	
	private static TioClient tioClient = null;
	public static ClientChannelContext clientChannelContext = null;
	
	public static void main(String args[]) throws Exception{
		clientGroupContext.setHeartbeatTimeout(Const.TIMEOUT);
		tioClient = new TioClient(clientGroupContext);
		clientChannelContext = tioClient.connect(serverNode);
		BlockPacket pack=new BlockPacket();
		pack.setType(PacketType.HEART_BEAT);
		Tio.send(clientChannelContext, pack);
	}
}
