package edu.ictt.blockchain.socket.server.upperhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;

import edu.ictt.blockchain.ApplicationContextProvider;
import edu.ictt.blockchain.Block.block.UpperBlock;
import edu.ictt.blockchain.Block.check.UCheckerManager;
import edu.ictt.blockchain.socket.body.upperbody.URpcBlockBody;
import edu.ictt.blockchain.socket.body.upperbody.URpcCheckBlockBody;
import edu.ictt.blockchain.socket.common.intf.AbstractBlockHandler;
import edu.ictt.blockchain.socket.packet.BlockPacket;
import edu.ictt.blockchain.socket.server.handler.GenerateBlockRequestHandler;
import edu.ictt.blockchain.socket.upperpbft.UVoteType;
import edu.ictt.blockchain.socket.upperpbft.msg.UVoteMsg;
import edu.ictt.blockchain.socket.upperpbft.msg.UVotePreMsg;
import edu.ictt.blockchain.socket.upperpbft.queue.UMsgQueueManager;
import javassist.expr.NewArray;

/**
 * 上层节点收到某个同级节点请求生成block后的处理
 * @author zoe
 *
 */
public class UGenerateBlockRequestHandler extends AbstractBlockHandler<URpcBlockBody>{
	
	private Logger logger = LoggerFactory.getLogger(UGenerateBlockRequestHandler.class);
	
	@Override
	public Class<URpcBlockBody> bodyClass() {
		// TODO Auto-generated method stub
		return URpcBlockBody.class;
	}

	@Override
	public Object handler(BlockPacket packet, URpcBlockBody bsBody, ChannelContext channelContext) throws Exception {
		//先取出block
		UpperBlock uBlock = bsBody.getUbBlock();
		logger.info("[Server校内校间通信]：收到来自<" + bsBody.getAppId() + "><请求生成uBlock>消息，uBlock信息为：[" + uBlock + "]");
		//再对block进行校验
		UCheckerManager uCheckerManager = ApplicationContextProvider.getBean(UCheckerManager.class);
		URpcCheckBlockBody uRpcCheckBlockBody = uCheckerManager.check(uBlock);
		logger.info("[Server校内校间通信]：uBlock校验结果：" + uRpcCheckBlockBody.getCode());
		//校验通过后生成投票信息
		if(uRpcCheckBlockBody.getCode() == 0) {
			UVotePreMsg uVotePreMsg = new UVotePreMsg();
			uVotePreMsg.setuBlock(uBlock);
			uVotePreMsg.setVoteType(UVoteType.pre);
			uVotePreMsg.setAppId(bsBody.getAppId());
			uVotePreMsg.setNumber(uBlock.getuBlockHeader().getUblockNumber());
			uVotePreMsg.setUhash(uBlock.getUpperBlockHash());
			uVotePreMsg.setBhash(uBlock.getuBlockHeader().getBhash());
			uVotePreMsg.setAgree(true);			
			//最后将投票信息推入本地队列
			logger.info("[Server校内校间通信-进入共识]：votepremsg：" + uVotePreMsg);
			ApplicationContextProvider.getBean(UMsgQueueManager.class).push(uVotePreMsg);
			//TODO 这里不需要将投票信息sendGroup吗
		}
		
		return null;
	}

}
