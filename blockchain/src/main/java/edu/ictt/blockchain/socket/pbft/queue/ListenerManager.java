package edu.ictt.blockchain.socket.pbft.queue;


import java.util.EventListener;
import java.util.EventObject;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

import edu.ictt.blockchain.socket.pbft.VoteType;
import edu.ictt.blockchain.socket.pbft.event.MsgCommitEvent;
import edu.ictt.blockchain.socket.pbft.event.MsgPrepareEvent;
import edu.ictt.blockchain.socket.pbft.listener.EListener;
import edu.ictt.blockchain.socket.pbft.msg.VoteMsg;

public class ListenerManager {

	private final Set<EListener> listeners=new CopyOnWriteArraySet<EListener>();
	//添加listener
	public void addEListener(EListener listener){
        listeners.add(listener);
	}
	//移除listener
	public void removeElistener(EListener listener){
		 if (listeners == null)
	            return;
	        listeners.remove(listener);
	}
	
	public void publishEvent(EventObject event){
		System.out.println("publish");
		notifyListeners(event);
	}
	
	
	private void notifyListeners(EventObject event){
		VoteMsg votemsg=(VoteMsg) event.getSource();
		switch(votemsg.getVoteType()){
		case VoteType.prepare:
		for(EListener listener: listeners){
			System.out.println(listener.getClass());
			listener.msgIsPrepared((MsgPrepareEvent)event);
		}
		break;
		case VoteType.commit:
			for(EListener listener: listeners){
				listener.msgIsCommited((MsgCommitEvent)event);
			}
			break;
		}
	}
}
