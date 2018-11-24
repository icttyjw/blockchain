package edu.ictt.blockchain.socket.server;

import edu.ictt.blockchain.socket.common.Const;
import org.tio.server.TioServer;
import org.tio.server.ServerGroupContext;
import org.tio.server.intf.ServerAioHandler;
import org.tio.server.intf.ServerAioListener;


import java.io.IOException;

/**
 * server启动器
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
