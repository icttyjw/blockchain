package edu.ictt.blockchain.socket.pbft.listener;

import java.util.EventListener;

import edu.ictt.blockchain.socket.pbft.event.MsgCommitEvent;
import edu.ictt.blockchain.socket.pbft.event.MsgPrepareEvent;

public interface EListener extends EventListener{

	public void msgIsCommited(MsgCommitEvent msgCommitEvent);
	public void msgIsPrepared(MsgPrepareEvent msgPrepareEvent);
}
