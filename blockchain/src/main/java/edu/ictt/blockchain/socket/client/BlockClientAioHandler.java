package edu.ictt.blockchain.socket.client;

import edu.ictt.blockchain.socket.common.AbstractAioHandler;
import edu.ictt.blockchain.socket.common.intf.AbstractBlockHandler;
import edu.ictt.blockchain.socket.packet.BlockPacket;
import edu.ictt.blockchain.socket.packet.PacketType;
import edu.ictt.blockchain.socket.server.handler.HeartbeatReqHandler;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.tio.client.intf.ClientAioHandler;
import org.tio.core.ChannelContext;
import org.tio.core.exception.AioDecodeException;
import org.tio.core.intf.Packet;

/**
 * @author wuweifeng wrote on 2018/3/12.
 */
public class BlockClientAioHandler extends AbstractAioHandler implements ClientAioHandler {

	private static Map<Byte, AbstractBlockHandler<?>> handlerMap = new HashMap<Byte, AbstractBlockHandler<?>>();
	static{
		handlerMap.put(PacketType.HEART_BEAT, new HeartbeatReqHandler());
	}
    //@Override
    public BlockPacket heartbeatPacket() {
        //心跳包的内容就是隔一段时间向别的节点获取一次下一步区块（带着自己的最新Block获取别人的next Block）
        //return NextBlockPacketBuilder.build();
        return null;
    }

    /**
     * server端返回的响应会先进到该方法，将消息全丢到Disruptor中
     * @throws Exception 
     */
    //@Override
    public void handler(Packet packet, ChannelContext channelContext) throws Exception  {
        BlockPacket blockPacket = (BlockPacket) packet;
        byte type=blockPacket.getType();
        AbstractBlockHandler<?> blockhandler=handlerMap.get(type);
        blockhandler.handler(blockPacket,channelContext);
        System.out.println("client handler");
        return;
        //使用Disruptor来publish消息。所有收到的消息都进入Disruptor，同BlockServerAioHandler
        //ApplicationContextProvider.getBean(MessageProducer.class).publish(new BaseEvent(blockPacket, channelContext));
    }

	public Packet decode(ByteBuffer buffer, int limit, int position, int readableLength, ChannelContext channelContext)
			throws AioDecodeException {
		// TODO Auto-generated method stub
		//System.out.println(1);
		return null;
	}
}
