package edu.ictt.blockchain.core.event;

import org.springframework.context.ApplicationEvent;

import edu.ictt.blockchain.Block.record.RecordParse;

public class DelRecordEvent extends ApplicationEvent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8888094291147559638L;

	public DelRecordEvent(RecordParse recordParse) {
		super(recordParse);
		// TODO Auto-generated constructor stub
	}

}
