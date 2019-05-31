package edu.ictt.blockchain.core.event;

import org.springframework.context.ApplicationEvent;

public class ChangeEvent extends ApplicationEvent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1343734730430412421L;

	public ChangeEvent(String source) {
		super(source);
		// TODO Auto-generated constructor stub
	}

}
