package edu.ictt.blockchain.socket.client;

import edu.ictt.blockchain.socket.common.AbstractAioHandler;
import edu.ictt.blockchain.socket.common.intf.AbstractBlockHandler;
import edu.ictt.blockchain.socket.disruptor.base.BaseEvent;
import edu.ictt.blockchain.socket.disruptor.base.MessageProducer;
import edu.ictt.blockchain.socket.packet.BlockPacket;
import edu.ictt.blockchain.socket.packet.PacketBuilder;
import edu.ictt.blockchain.socket.packet.PacketType;
import edu.ictt.blockchain.ApplicationContextProvider;
import edu.ictt.blockchain.common.Const;
import edu.ictt.blockchain.common.FastJsonUtil;
import edu.ictt.blockchain.socket.body.common.BaseBody;
import edu.ictt.blockchain.socket.body.common.StateBody;
import edu.ictt.blockchain.socket.client.handler.HeartbeatHandler;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.tio.client.intf.ClientAioHandler;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.core.exception.AioDecodeException;
import org.tio.core.intf.Packet;

/**
 * 客户端handler分发处
 */
public class BlockClientAioHandler extends AbstractAioHandler implements ClientAioHandler {

    @Override
    public BlockPacket heartbeatPacket() {
        //心跳包的内容就是隔一段时间向别的节点获取一次下一步区块（带着自己的最新Block获取别人的next Block）
        //return NextBlockPacketBuilder.build();
    	StateBody sb=new StateBody();
		sb.setId("2");
		sb.setIp("223");
		sb.setName("sss");
		BlockPacket bs=new BlockPacket();
		bs.setType(PacketType.HEART_BEAT);
		try {
			bs.setBody(FastJsonUtil.toJSONString(sb).getBytes(Const.CHARSET));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BlockPacket blockPacket = new PacketBuilder<>().setType(PacketType.HEART_BEAT).setBody(new BaseBody()).build();
        return blockPacket;
    }

    /**
     * 调用handler中的具体的方法
     * @throws Exception 
     */
    @Override
    public void handler(Packet packet, ChannelContext channelContext) throws Exception  {
    	BlockPacket blockPacket = (BlockPacket) packet;
       
        //使用Disruptor来publish消息。所有收到的消息都进入Disruptor，同BlockServerAioHandler
        ApplicationContextProvider.getBean(MessageProducer.class).publish(new BaseEvent(blockPacket, channelContext));
    }

	
}
