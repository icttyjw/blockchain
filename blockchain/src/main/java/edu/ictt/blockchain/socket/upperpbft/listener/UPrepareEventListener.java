package edu.ictt.blockchain.socket.upperpbft.listener;

import javax.annotation.Resource;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import edu.ictt.blockchain.socket.body.upperbody.UVoteBody;
import edu.ictt.blockchain.socket.client.PacketSender;
import edu.ictt.blockchain.socket.packet.BlockPacket;
import edu.ictt.blockchain.socket.packet.PacketBuilder;
import edu.ictt.blockchain.socket.packet.UPacketType;
import edu.ictt.blockchain.socket.upperpbft.event.UMsgPrepareEvent;
import edu.ictt.blockchain.socket.upperpbft.msg.UVoteMsg;

@Component
public class UPrepareEventListener {
	@Resource
	PacketSender packetSender;
	
	/**
	 * ublock宸茬粡寮�濮嬭繘鍏repare鐘舵��
     *
     * @param msgPrepareEvent
     *         msgIsPrepareEvent
	 */
	@EventListener
	public void msgIsPrepared(UMsgPrepareEvent uMsgPrepareEvent) {
		UVoteMsg uVoteMsg = (UVoteMsg)uMsgPrepareEvent.getSource();
		//缇ゅ彂娑堟伅锛岄�氱煡鍒殑鑺傜偣锛屾垜宸插璇lock prepare
		BlockPacket blockPacket = new PacketBuilder<>().setType(UPacketType.PBFT_VOTE).setBody(new UVoteBody(uVoteMsg)).build();
		
		packetSender.sendUGroup(blockPacket);
	}
}
