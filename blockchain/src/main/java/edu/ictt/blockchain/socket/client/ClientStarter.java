package edu.ictt.blockchain.socket.client;

import static edu.ictt.blockchain.common.Const.GROUP_NAME;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.tio.client.TioClient;
import org.tio.client.ClientGroupContext;
import org.tio.core.Tio;
import org.tio.core.ChannelContext;
import org.tio.core.Node;
import org.tio.utils.lock.SetWithLock;

import com.google.common.collect.Maps;

import edu.ictt.blockchain.common.CommonUtil;
import edu.ictt.blockchain.common.Const;
import edu.ictt.blockchain.core.event.NodesConnectedEvent;
import edu.ictt.blockchain.core.requestbody.BlockRequesbody;
import edu.ictt.blockchain.core.service.BlockService;
import edu.ictt.blockchain.socket.body.BaseBody;
import edu.ictt.blockchain.socket.packet.BlockPacket;
import edu.ictt.blockchain.socket.packet.NextBlockPacketBuilder;
import edu.ictt.blockchain.socket.packet.PacketBuilder;
import edu.ictt.blockchain.socket.packet.PacketType;
import edu.ictt.blockchain.sql.service.NodeService;
import edu.ictt.blockchainmanager.NodeState;



/*
 * 客户端启动器，暂时没有发送具体信息，所有的输出都是在处理过程中的
 * 发送的代码也和下面的类似，具体的发送处理会在client的handler里
 */
@Component
public class ClientStarter {

	@Resource
    private ClientGroupContext clientGroupContext;
    @Resource
    private PacketSender packetSender;
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private NodeService nodeService;
    @Resource
    private BlockService blockService;
    //@Resource
    //private PermissionManager permissionManager;
    //@Value("${managerUrl}")
    //private String managerUrl;
    //@Value("${appId}")
    //private String appId;
    //@Value("${name}")
    //private String name;
    //@Value("${singeNode:true}")
    private Boolean singeNode=true;

    private Logger logger = LoggerFactory.getLogger(getClass());

    private Set<Node> nodes = new HashSet<>();
	
    // 节点连接状态
    private Map<String,Integer> nodesStatus = Maps.newConcurrentMap();
    private volatile boolean isNodesReady = false; // 节点是否已准备好
    
    /**
     * 通过数据库获取其他服务器信息
     * 隔5分钟去获取一次
     */
    @Scheduled(fixedRate = 300000)
    public void fetchOtherServer() {
        String localIp = CommonUtil.getLocalIp();
        logger.info("本机IP：{}",localIp);
        //校内和组内信息为长连，组间在投票时连接
       /* List<NodeState> nodelist=nodeService.queryAllNodes();
        if(nodelist.isEmpty())
        {
        	logger.info("请先初始化");
        	 System.exit(0);
        }
        logger.info("共有" + nodelist.size() + "个成员需要连接：" + nodelist.toString());
        nodes.clear();
        for(NodeState nodestate:nodelist){
        	 Node node = new Node(nodestate.getIp(), Const.PORT);
             nodes.add(node);
        }*/
        Node node=new Node(localIp,Const.PORT);
        nodes.add(node);
        bindServerGroup(nodes);
    }
    
    /**
     * 每30秒群发一次消息，和别人对比最新的Block
     */
    @Scheduled(initialDelay=5000,fixedDelay = 60000)
    public void heartBeat() {
    	if(!isNodesReady)return;
        logger.info("---------开始心跳包--------");
        //logger.info(""+clientGroupContext.getName());
        //BlockPacket blockPacket = new PacketBuilder<>().setType(PacketType.HEART_BEAT).setBody(new BaseBody()).build();//NextBlockPacketBuilder.build();
        //packetSender.sendGroup(blockPacket);
        BlockRequesbody blockRequesbody=new BlockRequesbody();
        blockService.addBlock(blockRequesbody);
        //Tio.sendToGroup(clientGroupContext, GROUP_NAME, blockPacket);
    }

    public void onNodesReady() {
        logger.info("开始群发信息获取next Block");
        //在这里发请求，去获取group别人的新区块
        BlockPacket nextBlockPacket = NextBlockPacketBuilder.build();
        packetSender.sendGroup(nextBlockPacket);
    }

    /**
     * client在此绑定多个服务器，多个服务器为一个group，将来发消息时发给一个group。
     * 此处连接的server的ip需要和服务器端保持一致，服务器删了，这边也要踢出Group
     */
    private void bindServerGroup(Set<Node> serverNodes) {
        //当前已经连接的
        SetWithLock<ChannelContext> setWithLock = Tio.getAllChannelContexts(clientGroupContext);
        Lock lock2 = setWithLock.getLock().readLock();
        lock2.lock();
        try {
            Set<ChannelContext> set = setWithLock.getObj();
            //已连接的节点集合
            Set<Node> connectedNodes = set.stream().map(ChannelContext::getServerNode).collect(Collectors.toSet());

            //连接新增的，删掉已在管理端不存在的
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
            lock2.unlock();
        }

    }

    private void connect(Node serverNode) {
        try {
            TioClient tioClient = new TioClient(clientGroupContext);
            logger.info("开始绑定" + ":" + serverNode.toString());
            tioClient.asynConnect(serverNode);
        } catch (Exception e) {
            logger.info("异常");
        }
    }
    
    @EventListener(NodesConnectedEvent.class)
    public void onConnected(NodesConnectedEvent connectedEvent){
    	ChannelContext channelContext = connectedEvent.getSource();
    	Node node = channelContext.getServerNode();
    	if (channelContext.isClosed) {
            logger.info("连接" + node.toString() + "失败");
            nodesStatus.put(node.getIp(), -1);
            return;
        }else{
        	logger.info("连接" + node.toString() + "成功");
        	nodesStatus.put(node.getIp(), 1);
        	//绑group是将要连接的各个服务器节点做为一个group
        	Tio.bindGroup(channelContext, Const.GROUP_NAME);

        	int csize = Tio.getAllChannelContexts(clientGroupContext).size();
        	if(csize >= pbftAgreeCount()){
        		synchronized (nodesStatus) {
        			if(!isNodesReady){
        				isNodesReady = true;
        				onNodesReady();
        			}
				}
        	}
        }
    }

    public int halfGroupSize() {
        SetWithLock<ChannelContext> setWithLock = clientGroupContext.groups.clients(clientGroupContext, Const.GROUP_NAME);
        return setWithLock.getObj().size() / 2;
    }

    /**
     * pbft算法中拜占庭节点数量f，总节点数3f+1
     *
     * @return f
     */
    public int pbftSize() {
        //Group内共有多少个节点
        int total = nodes.size();
        int pbft = (total - 1) / 3;
        if (pbft <= 0) {
            pbft = 1;
        }
        //如果要单节点测试，此处返回值改为0
        if(singeNode) return 0;
        return pbft;
    }

    public int pbftAgreeCount() {
        return pbftSize() * 2 + 1;
    }
}
