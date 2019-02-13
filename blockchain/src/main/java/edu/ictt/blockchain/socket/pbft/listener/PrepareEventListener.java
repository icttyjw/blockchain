package edu.ictt.blockchain.socket.pbft.listener;


import org.tio.client.ClientGroupContext;
import org.tio.core.Tio;

import edu.ictt.blockchain.socket.body.VoteBody;
import edu.ictt.blockchain.socket.client.ClientContextConfig;
import edu.ictt.blockchain.socket.client.PacketSender;
import edu.ictt.blockchain.socket.common.Const;
import edu.ictt.blockchain.socket.packet.BlockPacket;
import edu.ictt.blockchain.socket.packet.PacketBuilder;
import edu.ictt.blockchain.socket.packet.PacketType;
import edu.ictt.blockchain.socket.pbft.event.MsgCommitEvent;
import edu.ictt.blockchain.socket.pbft.event.MsgPrepareEvent;
import edu.ictt.blockchain.socket.pbft.msg.VoteMsg;

import static edu.ictt.blockchain.socket.client.ClientContextConfig.clientGroupContext;

public class PrepareEventListener implements EListener{

	@Override
	public void msgIsPrepared(MsgPrepareEvent msgPrepareEvent){
		System.out.println("isprepared");
		VoteMsg voteMsg=(VoteMsg) msgPrepareEvent.getSource();
		
		//群发消息，通知别的节点，我已对该Block Prepare
		BlockPacket blockPacket = new PacketBuilder<>().setType(PacketType.PBFT_VOTE).setBody(new
                VoteBody(voteMsg)).build();
		
		System.out.println("准备广播准备消息");
		
		//广播给给group_school我已Prepare
		Tio.sendToGroup(clientGroupContext, Const.GROUP_SCHOOL,  blockPacket);
		
		System.out.println("已广播准备消息");
		//PacketSender.sendGroup(Const.GROUP_NAME, blockPacket);
	}

	@Override
	public void msgIsCommited(MsgCommitEvent msgCommitEvent) {
		// TODO Auto-generated method stub
		
	}
}
