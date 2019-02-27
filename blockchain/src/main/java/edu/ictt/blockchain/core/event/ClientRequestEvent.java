package edu.ictt.blockchain.core.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;

import edu.ictt.blockchain.socket.packet.BlockPacket;

/**
 * 客户端对外发请求时会触发该Event
 * 
 */
public class ClientRequestEvent extends ApplicationEvent{


	public ClientRequestEvent(BlockPacket blockPacket) {
		super(blockPacket);
		// TODO Auto-generated constructor stub
	}

}
