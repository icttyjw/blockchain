package edu.ictt.blockchain.socket.client;

import java.io.IOException;

import org.tio.client.ClientChannelContext;
import org.tio.client.ClientGroupContext;
import org.tio.client.ReconnConf;
import org.tio.client.TioClient;
import org.tio.core.ChannelContext;
import org.tio.core.Node;
import org.tio.core.Tio;

import edu.ictt.blockchain.socket.common.Const;
import edu.ictt.blockchain.socket.packet.BlockPacket;
import edu.ictt.blockchain.socket.packet.PacketType;

/*
 * 客户端启动器，暂时没有发送具体信息，所有的输出都是在处理过程中的
 * 发送的代码也和下面的类似，具体的发送处理会在client的handler里
 */
public class ClientStarter {

	static String serverIp = "127.0.0.1";
	static int serverPort = Const.PORT;

	private static Node serverNode = new Node(serverIp, serverPort);

	//用来自动连接的，不想自动连接请设为null
	private static ReconnConf reconnConf = null;

	private static BlockClientAioHandler bcah=new BlockClientAioHandler();
	private static BlockClientAioListener bcal=new BlockClientAioListener();
	private static ClientGroupContext clientGroupContext = new ClientGroupContext(bcah, bcal, reconnConf);
	
	private static TioClient tioClient = null;
	public static ClientChannelContext clientChannelContext = null;
	
	public static void main(String args[]) throws Exception {
		clientGroupContext.setHeartbeatTimeout(Const.TIMEOUT);
		
		tioClient = new TioClient(clientGroupContext);
		clientChannelContext = tioClient.connect(serverNode);
		BlockPacket pack=new BlockPacket();
		pack.setType(PacketType.Connect_Request);//添加数据类型
		
		//tioClient.connect(serverNode);
		Tio.bindGroup(clientChannelContext, Const.GROUP_SCHOOL);
		Tio.bSendToGroup(clientGroupContext, Const.GROUP_SCHOOL, pack);
		//Tio.send(clientChannelContext, pack);
		//System.out.println(1);
	}
}
