package edu.ictt.blockchain.socket.client;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.tio.client.ClientGroupContext;
import org.tio.client.TioClient;
import org.tio.core.ChannelContext;
import org.tio.core.Node;
import org.tio.core.Tio;
import org.tio.utils.lock.SetWithLock;

import com.google.common.collect.Maps;

import edu.ictt.blockchain.Block.block.Block;
import edu.ictt.blockchain.Block.db.CreateGenesisBlock;
import edu.ictt.blockchain.common.CommonUtil;
import edu.ictt.blockchain.common.Const;
import edu.ictt.blockchain.core.event.NodeDisconnectedEvent;
import edu.ictt.blockchain.core.event.NodesConnectedEvent;
import edu.ictt.blockchain.core.service.BlockService;
import edu.ictt.blockchain.socket.body.common.BaseBody;
import edu.ictt.blockchain.socket.packet.BlockPacket;
import edu.ictt.blockchain.socket.packet.NextBlockPacketBuilder;
import edu.ictt.blockchain.socket.packet.PacketBuilder;
import edu.ictt.blockchain.socket.packet.PacketType;
import edu.ictt.blockchain.socket.packet.UPacketType;
import edu.ictt.blockchainmanager.groupmodel.NodeState;
import edu.ictt.blockchainmanager.sql.service.NodeService;



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
    
    //从本地恢复区块
    //@Resource
    //private RecoverLocalBlock recoverLocalBlock;
    
    //@Resource
    //private PermissionManager permissionManager;
    //@Value("${managerUrl}")
    //private String managerUrl;
    //@Value("${appId}")
    //private String appId;
    //@Value("${name}")
    //private String name;
    //@Value("${singeNode:true}")
    private Boolean singeNode=false;

    private Logger logger = LoggerFactory.getLogger(getClass());

    private Set<Node> nodes = new HashSet<>();
    //创世块
    //public static final CreateGenesisBlock GENESIS_BLCOK = new CreateGenesisBlock();
    
    //尝试从本地恢复
    //{try {
	//	recoverLocalBlock.recoverFromDisk();
	//} catch (RocksDBException e) {
		// TODO Auto-generated catch block
	//	e.printStackTrace();
	//}}
	
    // 节点连接状态
    private Map<String,Integer> nodesStatus = Maps.newConcurrentMap();
    private volatile boolean isNodesReady = false; // 节点是否已准备好
    //校内节点计数
    private volatile int gcount=0;
    //校间节点计数
    private volatile int scount=0;
    
    /**
     * 通过数据库获取其他服务器信息
     * 隔5分钟去获取一次
     */
    @Scheduled(initialDelay=5000,fixedRate = 300000)
    public void fetchOtherServer() {
        String localIp = CommonUtil.getLocalIp();
        logger.info("[启动]：本机IP：{}",localIp);
        //校内和组内信息为长连，组间在投票时连接
        List<NodeState> nodelist=nodeService.queryAllNodes();
        if(nodelist.isEmpty())
        {
        	logger.info("请先初始化");
        	 System.exit(0);
        }
        logger.info("共有" + nodelist.size() + "个成员需要连接：" + nodelist.toString());
        synchronized (nodes) {
        	 nodes.clear();
             for(NodeState nodestate:nodelist){
             	 Node node = new Node(nodestate.getIp(), Const.PORT);
             	 if(nodestate.getConnectstate().equals("1"))
                  nodes.add(node);
             }
             //Node node=new Node(localIp,Const.PORT);
             //Node node2=new Node("10.170.72.102",Const.PORT);
             //nodes.add(node);
             
             //Node node=new Node(localIp,Const.PORT);
             //Node node2=new  Node(localIp, Const.PORT);
             //nodes.add(node);
             //nodes.add(node2);
             bindServerGroup(nodes);
		}
       
    }
    
    /**
     * 每30秒群发一次消息，和别人对比最新的Block
     */
    @Scheduled(initialDelay=5000,fixedDelay = 60000)
    public void heartBeat() {
    	if(!isNodesReady)return;
        logger.info("---------开始心跳包--------");
        NodeState nodestate = nodeService.queryByIp(CommonUtil.getLocalIp());
    	if(Integer.parseInt(nodestate.getMain())==0) {
    		BlockPacket blockPacket = new PacketBuilder<>().setType(PacketType.HEART_BEAT).setBody(new BaseBody()).build();
    		packetSender.sendGroup(blockPacket);
    	}else if(Integer.parseInt(nodestate.getMain())==1){
    		BlockPacket blockPacket = new PacketBuilder<>().setType(UPacketType.HEART_BEAT).setBody(new BaseBody()).build();
    		packetSender.sendUGroup(blockPacket);
    	}
        //logger.info(""+clientGroupContext.getName());
       // BlockPacket blockPacket = new PacketBuilder<>().setType(PacketType.HEART_BEAT).setBody(new BaseBody()).build();//NextBlockPacketBuilder.build();
        //packetSender.sendGroup(blockPacket);
        //List<Record> records = new ArrayList<>();
        //GradeRecord record = GenerateRecord.geneGRecord();
        //records.add(record);
        //List<String> recordsHash = new ArrayList<>();
        //recordsHash.add(MerkleHash.create(record.toString()).toString());
        //BlockBody blockBody = new BlockBody(records,recordsHash);
        //BlockBody blockBody = new BlockBody(records);
        //BlockRequesbody blockRequesbody=new BlockRequesbody(blockBody);
        //System.out.println("本地生成的新区块体为：" + blockRequesbody);
        //blockService.addBlock(blockRequesbody);
        //RecordBody recordBody=new RecordBody(record, "test");
        //BlockPacket blockPacket=new PacketBuilder<>().setType(PacketType.RECEIVE_RECORD).setBody(recordBody).build();
        //sendType(blockPacket);
        //Tio.sendToGroup(clientGroupContext, GROUP_NAME, blockPacket);
    }

    public void onNodesReady() {
        logger.info("[]开始群发信息获取next Block");
        //在这里发请求，去获取group别人的新区块
        BlockPacket nextBlockPacket = NextBlockPacketBuilder.build();
        sendType(nextBlockPacket);
        
    }
    
    /**
     * 根据节点类型发送到对应组
     * @param blockPacket
     */
    public void sendType(BlockPacket blockPacket) {
    	NodeState nodestate = nodeService.queryByIp(CommonUtil.getLocalIp());
    	if(Integer.parseInt(nodestate.getMain())==0) {
    		packetSender.sendGroup(blockPacket);
    	}else if(Integer.parseInt(nodestate.getMain())==1){
    		packetSender.sendUGroup(blockPacket);
    	}
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
    
    
    public void addNode(Node node){
    	synchronized(nodes){
    		nodes.add(node);
    	}
    	connect(node);
    }
    
    public void removeNode(Node node){
    	synchronized(nodes){
    		Iterator<Node> it=nodes.iterator();
    		while(it.hasNext()){
    			Node n=it.next();
    			if(n.getIp().equals(node.getIp()))
    				{
    					it.remove();
    					
    				}
    		}
    	}
    	//connect(node);
    	
    }

    private void connect(Node serverNode) {
        try {
            TioClient tioClient = new TioClient(clientGroupContext);
            logger.info("[启动]：开始绑定" + ":" + serverNode.toString());
            tioClient.connect(serverNode);
        } catch (Exception e) {
            logger.info("[启动]：异常");
        }
    }
    
    @EventListener(NodesConnectedEvent.class)
    public void onConnected(NodesConnectedEvent connectedEvent){
    	ChannelContext channelContext = connectedEvent.getSource();
    	Node node = channelContext.getServerNode();
    	NodeState ns=nodeService.queryByIp(node.getIp());
    	channelContext.setBsId("sss");
    	Tio.bindBsId(channelContext, ns.getName()+" client");
    	if (channelContext.isClosed) {
            logger.info("[启动]：连接" + node.toString() + "失败");
            nodesStatus.put(node.getIp(), -1);
            return;
        }else{
        	logger.info("[启动]：连接" + node.toString() + "成功");
        	nodesStatus.put(node.getIp(), 1);
        	//绑group是将要连接的各个服务器节点做为一个group
        	NodeState nodestate = nodeService.queryByIp(node.getIp());
        	if(Integer.parseInt(nodestate.getNodetype())==2 ) {
        		Tio.bindGroup(channelContext, Const.GROUP_NAME);
        		gcount++;
        		logger.info("[Client]:绑定进block_group组");
        	}else if(Integer.parseInt(nodestate.getNodetype())==3){
        		Tio.bindGroup(channelContext, Const.GROUP_SCHOOL);
        		scount++;
        		logger.info("[Client]:block_school组");
        	}else if(Integer.parseInt(nodestate.getNodetype())==1) {
        		Tio.bindGroup(channelContext, Const.GROUP_SCHOOL);
        		Tio.bindGroup(channelContext, Const.GROUP_NAME);
        		gcount++;
        		scount++;
        	}
        	//Tio.bindGroup(channelContext, Const.GROUP_NAME);
        	
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
    
    @EventListener(NodeDisconnectedEvent.class)
    public void onDisConnect(NodeDisconnectedEvent nodeDisconnectedEvent){
    	String ip=(String)nodeDisconnectedEvent.getSource();
    	NodeState nodestate = nodeService.queryByIp(ip);
    	nodestate.changestate(0);
    	nodeService.saveLocalNode(nodestate);
    	if(Integer.parseInt(nodestate.getNodetype())==2 ) {
    		gcount--;
    		logger.info("[Client]:绑定进block_group组");
    	}else if(Integer.parseInt(nodestate.getNodetype())==3){
    		scount--;
    		logger.info("[Client]:block_school组");
    	}else if(Integer.parseInt(nodestate.getNodetype())==1) {
    		gcount--;
    		scount--;
    	}
    }

    public int halfGroupSize() {
    	SetWithLock<ChannelContext> setWithLock = null;
    	NodeState nodestate = nodeService.queryByIp(CommonUtil.getLocalIp());
    	if(Integer.parseInt(nodestate.getMain())==0) {
    		setWithLock = clientGroupContext.groups.clients(clientGroupContext, Const.GROUP_NAME);
    	}else if(Integer.parseInt(nodestate.getMain())==1){
    		setWithLock = clientGroupContext.groups.clients(clientGroupContext, Const.GROUP_SCHOOL);
    	}
        
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
        return pbftSize() * 2;// + 1;
    }
}
