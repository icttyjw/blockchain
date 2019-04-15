package edu.ictt.blockchain.socket.common;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;

import org.tio.client.ClientChannelContext;
import org.tio.client.ClientGroupContext;
import org.tio.client.TioClient; 
import org.tio.core.ChannelContext;
import org.tio.core.Node;
import org.tio.core.Tio;
import org.tio.server.ServerGroupContext;
import org.tio.utils.lock.SetWithLock;
/*
 * 这个应该是实验全局使用相同context失败了，暂时没删
 */
public class GroupContext {

	private  ClientGroupContext clientgroupcontext;
	
	public ClientGroupContext getClientgroupcontext() {
		return clientgroupcontext;
	}
	public void setClientgroupcontext(ClientGroupContext clientgroupcontext) {
		this.clientgroupcontext = clientgroupcontext;
	}
	private Set<Node> nodes=new HashSet<Node>();
	
	public void bindServer(Set<Node> serverNodes){
		SetWithLock<ChannelContext> setwithlock=Tio.getAllChannelContexts(clientgroupcontext);
		Lock lock=setwithlock.getLock().readLock();
		lock.lock();
		try{
			Set<ChannelContext> set=setwithlock.getObj();
			Set<Node> connectedNodes = set.stream().map(ChannelContext::getServerNode).collect(Collectors.toSet());
			for (Node node : serverNodes) {
                if (!connectedNodes.contains(node)) {
                    connect(node);
                }
            }
            //删掉已经不存在
            for (ChannelContext channelContext : set) {
                Node node = channelContext.getServerNode();
                if (!serverNodes.contains(node)) {
                    Tio.remove(channelContext, "主动关闭" + node.getIp());
                }

            }
        } finally {
            lock.unlock();
        
		}
	}
private void connect(Node serverNode) {
    try {
        TioClient aioClient = new TioClient(clientgroupcontext);
       
        aioClient.asynConnect(serverNode);
    } catch (Exception e) {
       e.printStackTrace();
    }
}
}
