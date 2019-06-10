package edu.ictt.blockchain.socket.upperpbft.listener;

import java.util.EventObject;

import javax.annotation.Resource;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import edu.ictt.blockchain.socket.body.upperbody.UVoteBody;
import edu.ictt.blockchain.socket.client.PacketSender;
import edu.ictt.blockchain.socket.packet.BlockPacket;
import edu.ictt.blockchain.socket.packet.PacketBuilder;
import edu.ictt.blockchain.socket.packet.UPacketType;
import edu.ictt.blockchain.socket.upperpbft.event.UMsgCommitEvent;
import edu.ictt.blockchain.socket.upperpbft.msg.UVoteMsg;

@Component
public class UCommitEventListener{
	
	@Resource 
	private PacketSender packetSender;
	
	@EventListener
	public void msgIsCommited(UMsgCommitEvent uMsgCommitEvent){
		UVoteMsg uVoteMsg = (UVoteMsg)uMsgCommitEvent.getSource();
		//缇ゅ彂娑堟伅锛岄�氱煡鍒殑鑺傜偣锛屾垜宸插璇lock commit
		BlockPacket blockPacket = new PacketBuilder<>().setType(UPacketType.PBFT_VOTE).setBody(new UVoteBody(uVoteMsg)).build();
		
		packetSender.sendUGroup(blockPacket);
	}
}
