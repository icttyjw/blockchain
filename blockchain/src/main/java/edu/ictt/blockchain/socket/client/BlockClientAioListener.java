package edu.ictt.blockchain.socket.client;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.tio.client.ClientGroupContext;
import org.tio.client.intf.ClientAioListener;
import org.tio.core.Tio;
import org.tio.core.ChannelContext;
import org.tio.core.Node;
import org.tio.core.intf.Packet;
import org.tio.utils.json.Json;

import edu.ictt.blockchain.ApplicationContextProvider;
import edu.ictt.blockchain.common.Const;
import edu.ictt.blockchain.core.event.NodeDisconnectedEvent;
import edu.ictt.blockchain.core.event.NodesConnectedEvent;
import edu.ictt.blockchainmanager.groupmodel.NodeState;
import edu.ictt.blockchainmanager.sql.service.NodeService;



/**
 * 暂时没用上
 * client端对各个server连接的情况回调。</p>
 * 当某个server的心跳超时（2min）时，Aio会从group里remove掉该连接，需要在重新connect后重新加入group
 *
 */
@Component
public class BlockClientAioListener implements ClientAioListener {
    private Logger logger = LoggerFactory.getLogger(getClass());
    //@Resource
    //NodeService nodeService;
    

    @Override
    public void onAfterConnected(ChannelContext channelContext, boolean isConnected, boolean isReconnect) throws Exception {
    	
        /*if (isConnected) {
        	NodeService nodeService = ApplicationContextProvider.getBean(NodeService.class);
        	//排查service 空指针用
        	//List<NodeState> nodelist=nodeService.queryAllNodes();
        	//logger.info("[Client]:当前的所有节点为：" + nodelist);
            logger.info("[Client启动]：连接成功：server地址为-" + channelContext.getServerNode());
            
            //根据节点类型将节点简单分组：校内组：GROUP_NAME；校间组：GROUP_SCHOOL；
            NodeState nodeType = nodeService.queryByIp(channelContext.getServerNode().getIp());
            logger.info("[Client]：当前连接的节点类型为：" + nodeType.getNodetype());
            
            if(Integer.parseInt(nodeType.getNodetype())==2) {
            	logger.info("[Client]:绑定进block_group组");
            	Tio.bindGroup(channelContext, Const.GROUP_NAME);
            }else if(Integer.parseInt(nodeType.getNodetype())==3){
            	logger.info("[Client]:绑定进block_group组");
            	Tio.bindGroup(channelContext, Const.GROUP_NAME);
            	logger.info("[Client]:绑定进school_group组");
            	Tio.bindGroup(channelContext, Const.GROUP_SCHOOL);
            }else {
            	//否则为本地节点，绑定进block_group组
				logger.info("[Client]:默认绑定进block_group组");
				Tio.bindGroup(channelContext, Const.GROUP_NAME);
			}
            	  
        } else {
            logger.info("[Client启动]：连接失败：server地址为-" + channelContext.getServerNode());
        }*/
        ClientGroupContext groupContext=(ClientGroupContext)channelContext.getGroupContext();
        if(groupContext.getReconnConf().getRetryCount()<channelContext.getReconnCount()){
			
   		 Tio.remove(channelContext, "false");
   		 System.out.println("remark "+channelContext.closeMeta.getRemark());
   		 System.out.println("close: "+channelContext.isClosed);
   		 }
        else
        ApplicationContextProvider.publishEvent(new NodesConnectedEvent(channelContext));
    }

    @Override
    public void onBeforeClose(ChannelContext channelContext, Throwable throwable, String s, boolean b) {
        logger.info("[启动]：连接关闭：server地址为-" + channelContext.getServerNode());
        String ip=channelContext.getClientNode().getIp();
        Tio.unbindGroup(channelContext);
        ApplicationContextProvider.publishEvent(new NodeDisconnectedEvent(ip));
    }

    @Override
    public void onAfterDecoded(ChannelContext channelContext, Packet packet, int i) throws Exception {

    }

    @Override
    public void onAfterReceivedBytes(ChannelContext channelContext, int i) throws Exception {

    }

    @Override
    public void onAfterSent(ChannelContext channelContext, Packet packet, boolean b) throws Exception {
    	logger.info("[通信状态]：onAfterSent channelContext:{}, packet:{}, packetSize:{}",channelContext,Json.toJson(packet),b);
    }

    @Override
    public void onAfterHandled(ChannelContext channelContext, Packet packet, long l) throws Exception {
    	
    }

}
