package edu.ictt.blockchain.socket.server;

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

	private Logger logger = LoggerFactory.getLogger(getClass());
    @PostConstruct
    public void serverStart() throws IOException {
    	logger.info("[启动]：启动本地server");
        ServerAioHandler serverAioHandler = new BlockServerAioHandler();
        ServerAioListener serverAioListener = new BlockServerAioListener();
        ServerGroupContext serverGroupContext = new ServerGroupContext(serverAioHandler, serverAioListener);
        //serverGroupContext.setHeartbeatTimeout(Const.TIMEOUT);
        TioServer tioServer = new TioServer(serverGroupContext);
        //本机启动服务
        tioServer.start(null, Const.PORT);
    }
}
