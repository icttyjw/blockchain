package edu.ictt.blockchain.socket.disruptor;

import java.util.HashMap;
import java.util.Map;

import edu.ictt.blockchain.socket.common.intf.AbstractBlockHandler;
import edu.ictt.blockchain.socket.disruptor.base.BaseEvent;
import edu.ictt.blockchain.socket.disruptor.base.MessageConsumer;
import edu.ictt.blockchain.socket.packet.BlockPacket;

public class DisruptorServerConsumer implements MessageConsumer{

	 private static Map<Byte, AbstractBlockHandler<?>> handlerMap = new HashMap<>();

	    static {
	    }

	    @Override
	    public void receive(BaseEvent baseEvent) throws Exception {
	        BlockPacket blockPacket = baseEvent.getBlockPacket();
	        Byte type = blockPacket.getType();
	        AbstractBlockHandler<?> handler = handlerMap.get(type);
	        if (handler == null) {
	            return;
	        }
	        handler.handler(blockPacket, baseEvent.getChannelContext());
	    }
}
