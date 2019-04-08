package edu.ictt.blockchain.socket.server.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;

import edu.ictt.blockchain.ApplicationContextProvider;
import edu.ictt.blockchain.Block.record.Record;
import edu.ictt.blockchain.socket.body.GRecordBody;
import edu.ictt.blockchain.socket.common.intf.AbstractBlockHandler;
import edu.ictt.blockchain.socket.packet.BlockPacket;
import edu.ictt.blockchain.socket.record.queue.GRecordQueue;

public class RecieveRecordHandler extends AbstractBlockHandler<GRecordBody>{

	@Override
	public Class<GRecordBody> bodyClass() {
		// TODO Auto-generated method stub
		return GRecordBody.class;
	}
	
	Logger logger=LoggerFactory.getLogger(getClass());

	@Override
	public Object handler(BlockPacket packet, GRecordBody bsBody, ChannelContext channelContext) throws Exception {
		Record record=bsBody.getRecord();
		logger.info("body:"+bsBody);
		logger.info("收到来自于<" + bsBody.getAppId() + "><成绩记录>消息，block信息为[" + record + "]");
		/*
		 * 校验记录判断是否接收
		 */
		ApplicationContextProvider.getBean(GRecordQueue.class).receive(bsBody);
		return null;
	}

}
