package edu.ictt.blockchain.socket.server.handler;

import org.tio.core.ChannelContext;

import edu.ictt.blockchain.ApplicationContextProvider;
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

	@Override
	public Object handler(BlockPacket packet, RecordBody bsBody, ChannelContext channelContext) throws Exception {
		ApplicationContextProvider.getBean(RecordQueue.class).receive(bsBody);
		return null;
	}

}
