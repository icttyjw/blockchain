package edu.ictt.blockchain.core.event;

import org.springframework.context.ApplicationEvent;
import org.tio.core.ChannelContext;


public class NodesConnectedEvent extends ApplicationEvent{

	public NodesConnectedEvent(ChannelContext channelContext) {
        super(channelContext);
    }
	
	public ChannelContext getSource() {
        return (ChannelContext) source;
    }
	
}
