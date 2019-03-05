package test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.tio.client.ClientChannelContext;
import org.tio.client.ClientGroupContext;
import org.tio.client.ReconnConf;
import org.tio.client.TioClient;
import org.tio.core.ChannelContext;
import org.tio.core.ChannelContextFilter;
import org.tio.core.Node;
import org.tio.core.Tio;
import org.tio.server.ServerGroupContext;
import org.tio.server.TioServer;
import org.tio.server.intf.ServerAioHandler;
import org.tio.server.intf.ServerAioListener;
import org.tio.utils.json.Json;

import edu.ictt.blockchain.common.Const;
import edu.ictt.blockchain.socket.body.VoteBody;
import edu.ictt.blockchain.socket.client.BlockClientAioHandler;
import edu.ictt.blockchain.socket.client.BlockClientAioListener;
import edu.ictt.blockchain.socket.client.ClientContextConfig;
import edu.ictt.blockchain.socket.common.GroupContext;
import edu.ictt.blockchain.socket.packet.BlockPacket;
import edu.ictt.blockchain.socket.packet.PacketType;
import edu.ictt.blockchain.socket.pbft.msg.VoteMsg;
import edu.ictt.blockchain.socket.server.BlockServerAioHandler;
import edu.ictt.blockchain.socket.server.BlockServerAioListener;

public class IOtest {

	@Test
	public void contexttest() throws Exception{
		ServerAioHandler serverAioHandler = new BlockServerAioHandler();
        ServerAioListener serverAioListener = new BlockServerAioListener();
        ServerGroupContext serverGroupContext = new ServerGroupContext(serverAioHandler, serverAioListener);
        serverGroupContext.setHeartbeatTimeout(Const.TIMEOUT);
        TioServer tioServer = new TioServer(serverGroupContext);
        //本机启动服务
        tioServer.start(null, 6784);
		 Node serverNode = new Node("127.0.0.1", Const.PORT);
    	//用来自动连接的，不想自动连接请设为null
    	ReconnConf reconnConf = null;
    	 BlockClientAioHandler bcah=new BlockClientAioHandler();
    	 BlockClientAioListener bcal=new BlockClientAioListener();
    	 ClientGroupContext clientGroupContext = new ClientGroupContext(bcah, bcal, reconnConf);
    	 clientGroupContext.setHeartbeatTimeout(0);
    	 TioClient tioClient = null;
    	 ClientChannelContext clientChannelContext = null;
 		 tioClient = new TioClient(clientGroupContext);
 		 clientChannelContext = tioClient.connect(serverNode);
 		 BlockPacket pack=new BlockPacket();
 		 pack.setType(PacketType.Connect_Request);//添加数据类型
 		 Tio.send(clientChannelContext, pack);
	}
	//public static ClientChannelContext clientChannelContext = null;
	//public static  TioClient tioClient = null;
	public static void main(String args[]) throws Exception{
		/*	ReconnConf reconnConf = null;
		BlockClientAioHandler bcah=new BlockClientAioHandler();
   	    BlockClientAioListener bcal=new BlockClientAioListener();
   	    ClientGroupContext clientGroupContext = new ClientGroupContext(bcah, bcal, reconnConf);
   	    clientGroupContext.setHeartbeatTimeout(0);*/
		ServerAioHandler serverAioHandler = new BlockServerAioHandler();
        ServerAioListener serverAioListener = new BlockServerAioListener();
        ServerGroupContext serverGroupContext = new ServerGroupContext(serverAioHandler, serverAioListener);
        serverGroupContext.setHeartbeatTimeout(-1);
        TioServer tioServer = new TioServer(serverGroupContext);
        //本机启动服务
        tioServer.start(null, Const.PORT);
		BlockPacket pack=new BlockPacket();
 		pack.setType(PacketType.Connect_Request);//添加数据类型
 		Node serverNode1 = new Node("127.0.0.1", Const.PORT);
		//Node serverNode2 = new Node("127.0.0.1",5566);
		//Node serverNode3 = new Node("10.170.5.134",Const.PORT);
		ClientContextConfig ccc=new ClientContextConfig();
		
		//ccc.connect(clientGroupContext, serverNode1, Const.GROUP_SCHOOL);
		VoteMsg voteMsg=new VoteMsg();
		VoteBody voteBody=new VoteBody();
		voteMsg.setHash("111");
		voteMsg.setAppId("ss");
		voteMsg.setNumber(1);
		byte pre=1;
		voteMsg.setVoteType(pre);
		voteBody.setVoteMsg(voteMsg);
		BlockPacket packet = new BlockPacket();
		byte type=10;
		packet.setType(type);
		packet.setBody(Json.toJson(voteBody));
		//Tio.sendToGroup(clientGroupContext,Const.GROUP_SCHOOL , packet);
		//ccc.connect(clientGroupContext, serverNode2, Const.GROUP_SCHOOL);
		//connect(clientGroupContext, serverNode1, Const.GROUP_SCHOOL);
		//ClientGroupContext clientGroupContext1=connect(serverNode1,Const.GROUP_SCHOOL);
		//ClientGroupContext clientGroupContext2=connect(serverNode2,Const.GROUP_NAME);
		//Tio.sendToGroup(clientGroupContext1, Const.GROUP_SCHOOL, pack);
		//Tio.sendToGroup(clientGroupContext2, Const.GROUP_NAME, pack);
		//Tio.sendToGroup(clientGroupContext, Const.GROUP_SCHOOL, pack);
	}
	public static void connect(ClientGroupContext clientGroupContext,Node servernode,String group) throws Exception{
	
    	TioClient tioClient = null;
    	ClientChannelContext clientChannelContext = null;
    	 
 		tioClient = new TioClient(clientGroupContext);
		
		clientChannelContext = tioClient.connect(servernode);
		Tio.bindGroup(clientChannelContext, group);
		//clientChannelContext = tioClient.connect(serverNode2);
		//Tio.bindGroup(clientChannelContext, Const.GROUP_SCHOOL);
		//clientChannelContext = tioClient.connect(serverNode3);
		//connectgroup(clientGroupContext,clientChannelContext,Const.GROUP_SCHOOL,serverNode1);
		//connectgroup(tioClient,clientChannelContext,Const.GROUP_SCHOOL,serverNode2);
		BlockPacket pack=new BlockPacket();
 		pack.setType(PacketType.Connect_Request);//添加数据类型
 		//Tio.bindGroup(clientChannelContext, Const.GROUP_SCHOOL);
 		//Tio.send(clientChannelContext, pack);
 		//return clientGroupContext;
 		//Tio.sendToGroup(clientGroupContext, Const.GROUP_SCHOOL, pack);
 		//Tio.send( , packet)
		
	}
	private static void connectgroup( ClientGroupContext clientGroupContext,ClientChannelContext clientchannelcontext,String group,Node serverNode){
		try {
			TioClient tioClient = new TioClient(clientGroupContext);
			clientchannelcontext=tioClient.connect(serverNode);
			Tio.bindGroup(clientchannelcontext, group);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
