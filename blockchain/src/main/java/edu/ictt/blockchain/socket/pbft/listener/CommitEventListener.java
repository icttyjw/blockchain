package edu.ictt.blockchain.socket.pbft.listener;

import java.util.EventListener;

import edu.ictt.blockchain.socket.body.VoteBody;
import edu.ictt.blockchain.socket.client.PacketSender;
import edu.ictt.blockchain.socket.common.Const;
import edu.ictt.blockchain.socket.packet.BlockPacket;
import edu.ictt.blockchain.socket.packet.PacketBuilder;
import edu.ictt.blockchain.socket.packet.PacketType;
import edu.ictt.blockchain.socket.pbft.event.MsgCommitEvent;
import edu.ictt.blockchain.socket.pbft.event.MsgPrepareEvent;
import edu.ictt.blockchain.socket.pbft.msg.VoteMsg;
/*
 * 仿照着写的listener，接收到的prepare信息足够后触发
 */
public class CommitEventListener implements EListener {

	@Override
	public void msgIsCommited(MsgCommitEvent msgCommitEvent){
		VoteMsg voteMsg=(VoteMsg) msgCommitEvent.getSource();
		BlockPacket blockPacket = new PacketBuilder<>().setType(PacketType.PBFT_VOTE).setBody(new
                VoteBody(voteMsg)).build();
		
		PacketSender.sendGroup(blockPacket);
	}

	@Override
	public void msgIsPrepared(MsgPrepareEvent msgPrepareEvent) {
		// TODO Auto-generated method stub
		
	}
	
}
