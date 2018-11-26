package edu.ictt.blockchain.socket.server;

import edu.ictt.blockchain.socket.common.Const;
import org.tio.server.TioServer;
import org.tio.server.ServerGroupContext;
import org.tio.server.intf.ServerAioHandler;
import org.tio.server.intf.ServerAioListener;


import java.io.IOException;

/**
 * server启动器
 * 仿照其中代码可以放入其他函数中，但是具体的数据处理应该在handler中
 * 需要在handler中调用读取数据的函数
 *
 * @author wuweifeng wrote on 2018/3/12.
 */
public class BlockServerStarter {

    
    public static void main(String args[]) throws IOException {
        ServerAioHandler serverAioHandler = new BlockServerAioHandler();
        ServerAioListener serverAioListener = new BlockServerAioListener();
        ServerGroupContext serverGroupContext = new ServerGroupContext(serverAioHandler, serverAioListener);
        serverGroupContext.setHeartbeatTimeout(Const.TIMEOUT);
        TioServer tioServer = new TioServer(serverGroupContext);
        //本机启动服务
        tioServer.start(null, Const.PORT);
    }
}
