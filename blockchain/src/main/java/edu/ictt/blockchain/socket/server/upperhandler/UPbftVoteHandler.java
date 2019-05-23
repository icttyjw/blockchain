package edu.ictt.blockchain.socket.server.upperhandler;

import static org.hamcrest.CoreMatchers.nullValue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;

import edu.ictt.blockchain.ApplicationContextProvider;
import edu.ictt.blockchain.socket.body.lowerbody.VoteBody;
import edu.ictt.blockchain.socket.body.upperbody.UVoteBody;
import edu.ictt.blockchain.socket.common.intf.AbstractBlockHandler;
import edu.ictt.blockchain.socket.packet.BlockPacket;
import edu.ictt.blockchain.socket.upperpbft.msg.UVoteMsg;
import edu.ictt.blockchain.socket.upperpbft.queue.UMsgQueueManager;

/**
 * Upper层收到投票请求的处理
 *
 */
public class UPbftVoteHandler extends AbstractBlockHandler<UVoteBody>{

	private Logger logger= LoggerFactory.getLogger(getClass());
	
	@Override
	public Class<UVoteBody> bodyClass() {
		return UVoteBody.class;
	}

	@Override
	public Object handler(BlockPacket packet, UVoteBody bsBody, ChannelContext channelContext) throws Exception {
		// TODO Auto-generated method stub
		UVoteMsg uVoteMsg = bsBody.getuVoteMsg();
		logger.info("[Server校内校间共识投票]:收到投票信息" + uVoteMsg);
		
		//把该投票交给UMsgQueueManager去处理
		ApplicationContextProvider.getBean(UMsgQueueManager.class).push(uVoteMsg);
		return null;
	}

}
