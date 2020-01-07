package edu.ictt.blockchain.socket.server.handler;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.core.Tio;
import org.tio.utils.lock.SetWithLock;

import edu.ictt.blockchain.ApplicationContextProvider;
import edu.ictt.blockchain.common.Const;
import edu.ictt.blockchain.common.FastJsonUtil;
import edu.ictt.blockchain.socket.body.common.StateBody;
import edu.ictt.blockchain.socket.client.ClientContextConfig;
import edu.ictt.blockchain.socket.common.intf.AbstractBlockHandler;
import edu.ictt.blockchain.socket.packet.BlockPacket;
import edu.ictt.blockchain.socket.packet.PacketType;
import edu.ictt.blockchainmanager.groupmodel.NodeState;
import edu.ictt.blockchainmanager.sql.service.NodeService;

public class LoginReqHandler extends AbstractBlockHandler<StateBody>{

	private Logger logger = LoggerFactory.getLogger(HeartbeatReqHandler.class);
	@Override
	public Class<StateBody> bodyClass() {
		// TODO Auto-generated method stub
		return StateBody.class;
	}

	@Override
	public Object handler(BlockPacket packet, StateBody bsBody, ChannelContext channelContext) throws Exception {
		// TODO Auto-generated method stub
		//System.out.println(FastJsonUtil.toJSONString(bsBody));
		
		logger.info(FastJsonUtil.toJSONString(bsBody));
		int i=ApplicationContextProvider.getBean(NodeService.class).isnull(bsBody.getName());
		if(i==0){//新的节点
			NodeState node=new NodeState(bsBody.getName(), channelContext.getClientNode().getIp(), "0", "0", "0", "3", null, null, null);
			ApplicationContextProvider.getBean(NodeService.class).saveLocalNode(node);
			Tio.remove(channelContext, null);
		}else{
			NodeState node=new NodeState(bsBody.getName(), channelContext.getClientNode().getIp(), "1", "1", "0", "3", null, null, null);
			ApplicationContextProvider.getBean(NodeService.class).saveLocalNode(node);
		}
		//Scanner sc=new Scanner(System.in);
		//String t=sc.nextLine();
		//if(t.equals("s"))
		//System.out.println("true");
		logger.info(channelContext.getClientNode().getIp());
		BlockPacket pack=new BlockPacket();
 		pack.setType(PacketType.Connect_Request);
		//Tio.sendToGroup(clientGroupContext, Const.GROUP_SCHOOL, pack);
		//Tio.bindGroup(channelContext, "s");
		//GroupContext groupContext=null;
		//SetWithLock<ChannelContext> setWithLock=Tio.getAllChannelContexts(groupContext);
		return null;
	}

}
