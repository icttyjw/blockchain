package edu.ictt.blockchain.core.event;

import org.springframework.context.ApplicationEvent;

import edu.ictt.blockchain.Block.block.Block;

public class AddBlockEvent extends ApplicationEvent{
	/**
	 * 
	 */
	private static final long serialVersionUID = 211310475665269020L;

	public AddBlockEvent(Block block){
		super(block);
	}
}
