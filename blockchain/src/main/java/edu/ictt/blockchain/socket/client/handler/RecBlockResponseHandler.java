package edu.ictt.blockchain.socket.client.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;

import edu.ictt.blockchain.ApplicationContextProvider;
import edu.ictt.blockchain.Block.block.Block;
import edu.ictt.blockchain.Block.check.CheckerManager;
import edu.ictt.blockchain.common.FastJsonUtil;
import edu.ictt.blockchain.core.manager.DbBlockManager;
import edu.ictt.blockchain.socket.body.RpcBlockBody;
import edu.ictt.blockchain.socket.common.intf.AbstractBlockHandler;
import edu.ictt.blockchain.socket.packet.BlockPacket;

public class RecBlockResponseHandler extends AbstractBlockHandler<RpcBlockBody>{
	
	private Logger logger = LoggerFactory.getLogger(RecBlockResponseHandler.class);

	@Override
	public Class<RpcBlockBody> bodyClass() {
		// TODO Auto-generated method stub
		return RpcBlockBody.class;
	}

	@Override
	public Object handler(BlockPacket packet, RpcBlockBody bsBody, ChannelContext channelContext) throws Exception {
		Block block=bsBody.getBlock();
		CheckerManager checkerManager = ApplicationContextProvider.getBean(CheckerManager.class);
        int res = checkerManager.periodcheck(block);
        if(res==0){
        	DbBlockManager dbBlockManager=ApplicationContextProvider.getBean(DbBlockManager.class);
        	String str=FastJsonUtil.toJSONString(block);
        	dbBlockManager.put(block.getBlockHash(), str);
        }
		return null;
	}

}
