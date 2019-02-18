package edu.ictt.blockchain.socket.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import edu.ictt.blockchain.socket.disruptor.base.BaseEvent;
import edu.ictt.blockchain.socket.disruptor.base.MessageProducer;

public class DisruptorProducer implements MessageProducer {

	 private Disruptor<BaseEvent> disruptor;

	    public DisruptorProducer(Disruptor<BaseEvent> disruptor) {
	        this.disruptor = disruptor;
	    }

	    @Override
	    public void publish(BaseEvent baseEvent) {
	        RingBuffer<BaseEvent> ringBuffer = disruptor.getRingBuffer();
	        long sequence = ringBuffer.next();
	        try {
	            // Get the entry in the Disruptor
	            BaseEvent event = ringBuffer.get(sequence);
	            // for the sequence   // Fill with data
	            event.setBlockPacket(baseEvent.getBlockPacket());
	            event.setChannelContext(baseEvent.getChannelContext());
	        } finally {
	            ringBuffer.publish(sequence);
	        }
	    }
}
