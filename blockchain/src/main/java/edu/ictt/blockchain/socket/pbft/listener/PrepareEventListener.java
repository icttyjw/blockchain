package edu.ictt.blockchain.socket.pbft.listener;


import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import edu.ictt.blockchain.socket.body.lowerbody.VoteBody;
import edu.ictt.blockchain.socket.client.PacketSender;
import edu.ictt.blockchain.socket.packet.BlockPacket;
import edu.ictt.blockchain.socket.packet.PacketBuilder;
import edu.ictt.blockchain.socket.packet.PacketType;
import edu.ictt.blockchain.socket.pbft.event.MsgPrepareEvent;
import edu.ictt.blockchain.socket.pbft.msg.VoteMsg;


import javax.annotation.Resource;

@Component
public class PrepareEventListener {

	@Resource
	private PacketSender packetSender;
	
	 /**
     * block已经开始进入Prepare状态
     *
     * @param msgPrepareEvent
     *         msgIsPrepareEvent
     */
	@EventListener
	public void msgIsPrepared(MsgPrepareEvent msgPrepareEvent){
		VoteMsg voteMsg=(VoteMsg) msgPrepareEvent.getSource();
		BlockPacket blockPacket = new PacketBuilder<>().setType(PacketType.PBFT_VOTE).setBody(new
                VoteBody(voteMsg)).build();
		
		packetSender.sendGroup(blockPacket);
	}
}
