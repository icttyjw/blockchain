package edu.ictt.blockchain.socket.disruptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lmax.disruptor.EventHandler;

import edu.ictt.blockchain.ApplicationContextProvider;
import edu.ictt.blockchain.socket.disruptor.base.BaseEvent;

public class DisruptorServerHandler implements EventHandler<BaseEvent>{

	private Logger logger = LoggerFactory.getLogger(DisruptorServerHandler.class);

    @Override
    public void onEvent(BaseEvent baseEvent, long sequence, boolean endOfBatch) throws Exception {
    	try {
    		ApplicationContextProvider.getBean(DisruptorServerConsumer.class).receive(baseEvent);
		} catch (Exception e) {
			logger.error("Disruptor事件执行异常",e);
		}
    }
}
