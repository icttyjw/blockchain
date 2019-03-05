package edu.ictt.blockchain.socket.server.handler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;

import edu.ictt.blockchain.ApplicationContextProvider;
import edu.ictt.blockchain.socket.body.VoteBody;
import edu.ictt.blockchain.socket.common.intf.AbstractBlockHandler;
import edu.ictt.blockchain.socket.packet.BlockPacket;
import edu.ictt.blockchain.socket.pbft.msg.VoteMsg;
import edu.ictt.blockchain.socket.pbft.queue.MsgQueueManager;

import static edu.ictt.blockchain.socket.pbft.Message.msgQueueManager;
/*
 * 收到pbft投票信息的处理
 */
public class PbftVoteHandler extends AbstractBlockHandler<VoteBody>{
	
	private Logger logger= LoggerFactory.getLogger(getClass());

	@Override
	public Class<VoteBody> bodyClass() {
		// TODO Auto-generated method stub
		return VoteBody.class;
	}

	@Override
	public Object handler(BlockPacket packet, VoteBody bsBody, ChannelContext channelContext) throws Exception {
		// TODO Auto-generated method stub
		VoteMsg votemsg=bsBody.getVoteMsg();
		logger.info("收到投票信息"+votemsg);
		//进入投票阶段
		ApplicationContextProvider.getBean(MsgQueueManager.class).push(votemsg);
		return null;
	}

}
