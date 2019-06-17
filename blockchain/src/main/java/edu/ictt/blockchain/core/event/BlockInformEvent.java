package edu.ictt.blockchain.core.event;

import org.springframework.context.ApplicationEvent;

public class BlockInformEvent extends ApplicationEvent{

	public BlockInformEvent(String hash) {
		super(hash);
		// TODO Auto-generated constructor stub
	}

	
}
