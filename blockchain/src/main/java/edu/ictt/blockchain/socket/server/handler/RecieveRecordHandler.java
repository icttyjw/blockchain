package edu.ictt.blockchain.socket.server.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;

import edu.ictt.blockchain.ApplicationContextProvider;
import edu.ictt.blockchain.Block.record.GradeRecord;
import edu.ictt.blockchain.Block.record.Record;
import edu.ictt.blockchain.common.FastJsonUtil;
import edu.ictt.blockchain.common.SHA256;
import edu.ictt.blockchain.socket.body.RecordBody;
import edu.ictt.blockchain.socket.common.intf.AbstractBlockHandler;
import edu.ictt.blockchain.socket.packet.BlockPacket;
import edu.ictt.blockchain.socket.record.queue.RecordQueue;

public class RecieveRecordHandler extends AbstractBlockHandler<RecordBody>{

	@Override
	public Class<RecordBody> bodyClass() {
		// TODO Auto-generated method stub
		return RecordBody.class;
	}
	
	Logger logger=LoggerFactory.getLogger(getClass());

	@Override
	public Object handler(BlockPacket packet, RecordBody bsBody, ChannelContext channelContext) throws Exception {
		GradeRecord record=bsBody.getGradeRecord();
		logger.info("body:"+bsBody);
		logger.info("record hash:"+ SHA256.sha256(FastJsonUtil.toJSONString(record)));
		logger.info("收到来自于<" + bsBody.getAppId() + "><成绩记录>消息，block信息为[" + record + "]");
		/*
		 * 校验记录判断是否接收
		 */
		ApplicationContextProvider.getBean(RecordQueue.class).receive(bsBody);
		return null;
	}

}
