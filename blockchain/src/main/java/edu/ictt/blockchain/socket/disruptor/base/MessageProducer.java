package edu.ictt.blockchain.socket.disruptor.base;

public interface MessageProducer {

	void publish(BaseEvent baseEvent);
}
