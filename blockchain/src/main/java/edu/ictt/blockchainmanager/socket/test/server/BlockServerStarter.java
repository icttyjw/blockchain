package edu.ictt.blockchainmanager.socket.test.server;

import org.tio.server.TioServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.tio.server.ServerGroupContext;
import org.tio.server.intf.ServerAioHandler;
import org.tio.server.intf.ServerAioListener;

import edu.ictt.blockchain.common.Const;

import java.io.IOException;

import javax.annotation.PostConstruct;

/**
 * server启动器
 * 仿照其中代码可以放入其他函数中，但是具体的数据处理应该在server的handler中
 * 需要在handler中调用读取数据的函数
 *
 * @author wuweifeng wrote on 2018/3/12.
 */
@Component
public class BlockServerStarter {

	private static Logger logger = LoggerFactory.getLogger(BlockServerStarter.class);
    //@PostConstruct
	private static ServerAioHandler serverAioHandler = new BlockServerAioHandler();
    private static ServerAioListener serverAioListener = new BlockServerAioListener();
     private static ServerGroupContext serverGroupContext = new ServerGroupContext(serverAioHandler, serverAioListener);
    public static void serverStart() throws IOException {
    	logger.info("启动server");
       
        //serverGroupContext.setHeartbeatTimeout(Const.TIMEOUT);
        TioServer tioServer = new TioServer(serverGroupContext);
        //本机启动服务
        tioServer.start(null, Const.PORT);
    }
}
