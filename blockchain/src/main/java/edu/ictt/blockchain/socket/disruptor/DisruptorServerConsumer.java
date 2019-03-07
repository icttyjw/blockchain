package edu.ictt.blockchain.socket.disruptor;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import edu.ictt.blockchain.socket.common.intf.AbstractBlockHandler;
import edu.ictt.blockchain.socket.disruptor.base.BaseEvent;
import edu.ictt.blockchain.socket.disruptor.base.MessageConsumer;
import edu.ictt.blockchain.socket.packet.BlockPacket;
import edu.ictt.blockchain.socket.packet.PacketType;
import edu.ictt.blockchain.socket.server.handler.GenerateBlockRequestHandler;
import edu.ictt.blockchain.socket.server.handler.HeartbeatReqHandler;
import edu.ictt.blockchain.socket.server.handler.LoginReqHandler;
import edu.ictt.blockchain.socket.server.handler.NextBlockRequestHandler;
import edu.ictt.blockchain.socket.server.handler.PbftVoteHandler;
import edu.ictt.blockchain.socket.server.handler.RecieveRecordHandler;

@Component
public class DisruptorServerConsumer implements MessageConsumer{

	 private static Map<Byte, AbstractBlockHandler<?>> handlerMap = new HashMap<>();
	 private Logger logger = LoggerFactory.getLogger(getClass());
	    static {
	    	handlerMap.put(PacketType.PBFT_VOTE, new PbftVoteHandler());
	    	handlerMap.put(PacketType.HEART_BEAT, new HeartbeatReqHandler());
	    	handlerMap.put(PacketType.LOGIN_REQUEST, new LoginReqHandler());
	    	handlerMap.put(PacketType.NEXT_BLOCK_INFO_REQUEST, new NextBlockRequestHandler());
	    	handlerMap.put(PacketType.GENERATE_BLOCK_REQUEST, new GenerateBlockRequestHandler());
	    	handlerMap.put(PacketType.RECEIVE_RECORD,new RecieveRecordHandler());
	    }

	    @Override
	    public void receive(BaseEvent baseEvent) throws Exception {
	    	logger.info("收到消息");
	        BlockPacket blockPacket = baseEvent.getBlockPacket();
	        logger.info(blockPacket.getBody().toString());
	        Byte type = blockPacket.getType();
	        AbstractBlockHandler<?> handler = handlerMap.get(type);
	        if (handler == null) {
	            return;
	        }
	        handler.handler(blockPacket, baseEvent.getChannelContext());
	    }
}
