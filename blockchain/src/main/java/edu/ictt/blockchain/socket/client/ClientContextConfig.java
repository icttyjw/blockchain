package edu.ictt.blockchain.socket.client;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.tio.client.ClientChannelContext;
import org.tio.client.ClientGroupContext;
import org.tio.client.ReconnConf;
import org.tio.client.TioClient;
import org.tio.client.intf.ClientAioHandler;
import org.tio.client.intf.ClientAioListener;
import org.tio.core.Node;
import org.tio.core.Tio;

import edu.ictt.blockchain.common.Const;
import edu.ictt.blockchain.socket.packet.BlockPacket;
import edu.ictt.blockchain.socket.packet.PacketType;

/**
 * 设置全局的clientgroupcontext，后面的connect应该是没用的
 */
@Component
public class ClientContextConfig {

	private Logger logger=LoggerFactory.getLogger(getClass());
	/**
     * 构建客户端连接的context
     * @return
     * ClientGroupContext
     */
    @Bean
    public ClientGroupContext clientGroupContext() {
    	logger.info("------------------[启动阶段]：启动本地客户端并连接其组内节点------------------" );
    	//logger.info("[启动]初始化clientgroupcontext");
        //handler, 包括编码、解码、消息处理
        ClientAioHandler clientAioHandler = new BlockClientAioHandler();
        //事件监听器，可以为null，但建议自己实现该接口
        ClientAioListener clientAioListener = new BlockClientAioListener();
        //断链后自动连接的，不想自动连接请设为null
        ReconnConf reconnConf = new ReconnConf(5000L,2);
        ClientGroupContext clientGroupContext = new ClientGroupContext(clientAioHandler, clientAioListener,
                reconnConf);
        clientGroupContext.setName("blockgroup");
        logger.info("[启动阶段]：小组名称为blockgroup" );
        clientGroupContext.setHeartbeatTimeout(5000L);
        return clientGroupContext;
    }
    /*
	public static ClientGroupContext clientGroupContext;
	
	{
		 ClientAioHandler clientAioHandler = new BlockClientAioHandler();
	        //事件监听器，可以为null，但建议自己实现该接口
	        ClientAioListener clientAioListener = new BlockClientAioListener();
	        //断链后自动连接的，不想自动连接请设为null
	        ReconnConf reconnConf = new ReconnConf(5000L, 20);
	        clientGroupContext = new ClientGroupContext(clientAioHandler, clientAioListener,
	                reconnConf);

	        clientGroupContext.setHeartbeatTimeout(-1);//Const.TIMEOUT);
	}
	
    
    
    public void connect(ClientGroupContext clientGroupContext,Node servernode,String group) throws Exception{
		
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
		
	}*/
    
    

}
