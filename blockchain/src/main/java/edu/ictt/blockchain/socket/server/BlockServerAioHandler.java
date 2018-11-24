package edu.ictt.blockchain.socket.server;



import edu.ictt.blockchain.socket.common.AbstractAioHandler;
import edu.ictt.blockchain.socket.common.intf.AbstractBlockHandler;
import edu.ictt.blockchain.socket.packet.BlockPacket;
import edu.ictt.blockchain.socket.packet.PacketType;
import edu.ictt.blockchain.socket.server.handler.HeartbeatReqHandler;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.core.exception.AioDecodeException;
import org.tio.core.intf.Packet;
import org.tio.server.intf.ServerAioHandler;

/**
 * server端处理所有client请求的入口
 * @author wuweifeng wrote on 2018/3/12.
 */
public  class BlockServerAioHandler extends AbstractAioHandler  implements ServerAioHandler{

	private static Logger log = LoggerFactory.getLogger(BlockServerAioHandler.class);
	private static Map<Byte, AbstractBlockHandler<?>> handlerMap = new HashMap<Byte, AbstractBlockHandler<?>>();

	static{
		handlerMap.put(PacketType.HEART_BEAT, new HeartbeatReqHandler());
	}
    /**
     * 自己是server，此处接收到客户端来的消息。这里是入口
     * @throws Exception 
     */
    //@Override
    public void handler(Packet packet, ChannelContext channelContext) throws Exception {
    	System.out.println(2);
    	BlockPacket blockPacket = (BlockPacket) packet;
        byte type=blockPacket.getType();
        AbstractBlockHandler<?> blockhandler=handlerMap.get(type);
        if(blockhandler==null)
        {
        	
        	log.error("{}, 找不到处理类，type:{}", channelContext, type);
        	return;
        }
        blockhandler.handler(blockPacket,channelContext);
        return;
        //使用Disruptor来publish消息。所有收到的消息都进入Disruptor，同BlockClientAioHandler
       // ApplicationContextProvider.getBean(MessageProducer.class).publish(new BaseEvent(blockPacket, channelContext));
    }

	public Packet decode(ByteBuffer buffer, int limit, int position, int readableLength, ChannelContext channelContext)
			throws AioDecodeException {
		// TODO Auto-generated method stub
		return null;
	}

	

	
}
