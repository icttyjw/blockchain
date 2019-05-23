package edu.ictt.blockchain.socket.disruptor;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import edu.ictt.blockchain.Block.record.NewRecord;
import edu.ictt.blockchain.socket.common.intf.AbstractBlockHandler;
import edu.ictt.blockchain.socket.disruptor.base.BaseEvent;
import edu.ictt.blockchain.socket.disruptor.base.MessageConsumer;
import edu.ictt.blockchain.socket.packet.BlockPacket;
import edu.ictt.blockchain.socket.packet.PacketType;
import edu.ictt.blockchain.socket.packet.UPacketType;
import edu.ictt.blockchain.socket.server.handler.GenerateBlockRequestHandler;
import edu.ictt.blockchain.socket.server.handler.HeartbeatReqHandler;
import edu.ictt.blockchain.socket.server.handler.LoginReqHandler;
import edu.ictt.blockchain.socket.server.handler.NextBlockRequestHandler;
import edu.ictt.blockchain.socket.server.handler.PbftVoteHandler;
import edu.ictt.blockchain.socket.server.handler.RecieveRecordHandler;
import edu.ictt.blockchain.socket.server.upperhandler.ReceiveBlockHandler;
import edu.ictt.blockchain.socket.server.upperhandler.UGenerateBlockRequestHandler;
import edu.ictt.blockchain.socket.server.upperhandler.UHeartbeatReqHandler;
import edu.ictt.blockchain.socket.server.upperhandler.ULoginReqHandler;
import edu.ictt.blockchain.socket.server.upperhandler.UNextBlockRequestHandler;
import edu.ictt.blockchain.socket.server.upperhandler.UPbftVoteHandler;

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
	    	
	    	//上层的handler
	    	//TODO 未处理所有类型的
	    	handlerMap.put(UPacketType.PBFT_VOTE, new UPbftVoteHandler());
	    	handlerMap.put(UPacketType.HEART_BEAT, new UHeartbeatReqHandler());
	    	handlerMap.put(UPacketType.LOGIN_REQUEST, new ULoginReqHandler());
	    	handlerMap.put(UPacketType.NEXT_BLOCK_INFO_REQUEST, new UNextBlockRequestHandler());
	    	handlerMap.put(UPacketType.GENERATE_BLOCK_REQUEST, new UGenerateBlockRequestHandler());
	    	handlerMap.put(UPacketType.RECEIVE_BLOCK, new ReceiveBlockHandler());
	    	
	    }

	    @Override
	    public void receive(BaseEvent baseEvent) throws Exception {
	    	;
	        BlockPacket blockPacket = baseEvent.getBlockPacket();
	        byte[] blockPacketBody = blockPacket.getBody();
	        logger.info("[Server通信]:收到消息:"+ new String (blockPacketBody));
	        Byte type = blockPacket.getType();
	        AbstractBlockHandler<?> handler = handlerMap.get(type);
	        if (handler == null) {
	            return;
	        }
	        handler.handler(blockPacket, baseEvent.getChannelContext());
	    }
}
