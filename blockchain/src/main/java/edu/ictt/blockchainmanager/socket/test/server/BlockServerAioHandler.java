package edu.ictt.blockchainmanager.socket.test.server;



import edu.ictt.blockchain.ApplicationContextProvider;
import edu.ictt.blockchain.common.Const;
import edu.ictt.blockchain.socket.common.AbstractAioHandler;
import edu.ictt.blockchain.socket.common.intf.AbstractBlockHandler;
import edu.ictt.blockchain.socket.disruptor.base.BaseEvent;
import edu.ictt.blockchain.socket.disruptor.base.MessageProducer;
import edu.ictt.blockchain.socket.packet.BlockPacket;
import edu.ictt.blockchain.socket.packet.PacketType;
import edu.ictt.blockchain.socket.server.handler.HeartbeatReqHandler;
import edu.ictt.blockchain.socket.server.handler.LoginReqHandler;
import edu.ictt.blockchain.socket.server.handler.PbftVoteHandler;
import edu.ictt.blockchainmanager.Application;
import edu.ictt.blockchainmanager.bean.MessageProperty;
import edu.ictt.blockchainmanager.groupmodel.UITask;
import edu.ictt.blockchainmanager.groupmodel.UiBaseService;
import edu.ictt.blockchainmanager.groupmodel.userinterface.AlertController;
import edu.ictt.blockchainmanager.view.AlertView;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Modality;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.core.exception.AioDecodeException;
import org.tio.core.intf.Packet;
import org.tio.server.intf.ServerAioHandler;

/**
 * server端处理所有client请求的入口
 * @author wuweifeng wrote on 2018/3/12.
 */
@Component
public  class BlockServerAioHandler extends AbstractAioHandler  implements ServerAioHandler{

    /**
     * 自己是server，此处接收到客户端来的消息。这里是入口
     * @throws Exception 
     */
	
	//@Resource
	//private AlertController alertController;
	@Resource 
	private MessageProperty messageProperty;
	Logger logger=LoggerFactory.getLogger(getClass());
    @Override
    public void handler(Packet packet, ChannelContext channelContext) throws Exception {
    	BlockPacket blockPacket = (BlockPacket) packet;
        logger.info(blockPacket.getBody().toString());
        //使用Disruptor来publish消息。所有收到的消息都进入Disruptor，同BlockClientAioHandler
        //完成确认，这里需要线程等待界面的确认操作，确认之前不能进行其余的通信
       
		Thread th=new Thread( UITask.task);
		th.setDaemon(true);
		th.start();
    }

    

	

	
}
