package edu.ictt.blockchain.core.event;

import org.springframework.context.ApplicationEvent;

import edu.ictt.blockchain.Block.block.UpperBlock;

public class UAddBlockEvent extends ApplicationEvent{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6118034124166047967L;

	/**
	 * 
	 */

	public UAddBlockEvent(UpperBlock uBlock) {
		super(uBlock);
	}

}
