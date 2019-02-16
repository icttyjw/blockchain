package edu.ictt.blockchain.socket.pbft.listener;


import javax.annotation.Resource;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import edu.ictt.blockchain.socket.body.VoteBody;
import edu.ictt.blockchain.socket.client.PacketSender;
import edu.ictt.blockchain.socket.packet.BlockPacket;
import edu.ictt.blockchain.socket.packet.PacketBuilder;
import edu.ictt.blockchain.socket.packet.PacketType;
import edu.ictt.blockchain.socket.pbft.event.MsgCommitEvent;
import edu.ictt.blockchain.socket.pbft.msg.VoteMsg;
/*
 * 仿照着写的listener，接收到的prepare信息足够后触发
 */
@Component
public class CommitEventListener {

	@Resource
	private PacketSender packetSender;

	@EventListener
	public void msgIsCommited(MsgCommitEvent msgCommitEvent){
		VoteMsg voteMsg=(VoteMsg) msgCommitEvent.getSource();
		
		//群发消息，通知别的节点，我已对该Block commit
		BlockPacket blockPacket = new PacketBuilder<>().setType(PacketType.PBFT_VOTE).setBody(new
                VoteBody(voteMsg)).build();

		packetSender.sendGroup(blockPacket);
	}
	
}
