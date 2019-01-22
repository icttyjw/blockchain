package edu.ictt.blockchain.socket.server.handler;


import org.tio.core.ChannelContext;

import edu.ictt.blockchain.socket.body.VoteBody;
import edu.ictt.blockchain.socket.common.intf.AbstractBlockHandler;
import edu.ictt.blockchain.socket.packet.BlockPacket;
import edu.ictt.blockchain.socket.pbft.msg.VoteMsg;
import static edu.ictt.blockchain.socket.pbft.Message.msgQueueManager;
/*
 * 收到pbft投票信息的处理
 */
public class PbftVoteHandler extends AbstractBlockHandler<VoteBody>{

	@Override
	public Class<VoteBody> bodyClass() {
		// TODO Auto-generated method stub
		return VoteBody.class;
	}

	@Override
	public Object handler(BlockPacket packet, VoteBody bsBody, ChannelContext channelContext) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("handler");
		VoteMsg votemsg=bsBody.getVoteMsg();
		//进入投票阶段
		msgQueueManager.push(votemsg);
		System.out.println("part vote end");
		return null;
	}

}
