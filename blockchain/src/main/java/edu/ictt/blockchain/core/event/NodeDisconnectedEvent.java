package edu.ictt.blockchain.core.event;

import org.springframework.context.ApplicationEvent;

public class NodeDisconnectedEvent extends ApplicationEvent{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1091287445215595891L;

	public NodeDisconnectedEvent(String ip) {
		super(ip);
		// TODO Auto-generated constructor stub
	}

}
