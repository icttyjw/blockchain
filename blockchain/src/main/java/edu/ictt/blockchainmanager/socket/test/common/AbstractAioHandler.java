package edu.ictt.blockchainmanager.socket.test.common;

import edu.ictt.blockchain.socket.packet.BlockPacket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.core.exception.AioDecodeException;
import org.tio.core.intf.AioHandler;
import org.tio.core.intf.Packet;

import java.nio.ByteBuffer;

/**
 * 编码解码函数
 */
public abstract  class AbstractAioHandler implements AioHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());
    /**
	 * 解码：把接收到的ByteBuffer，解码成应用可以识别的业务消息包
	 * 消息头：type + bodyLength
	 * 消息体：byte[]
	 */
	@Override
	public Packet decode(ByteBuffer buffer, int limit, int position, int readableLength, ChannelContext channelContext) throws AioDecodeException {
		//可读数据，小于头部的固定长度，直接返回null，这样tio框架会自动把本次收到的数据暂存起来，并和下次收到的数据组合起来
		if (readableLength < BlockPacket.HEADER_LENGTH) {
			return null;
		}

		//position的值不一定是0，但是
		//消息类型
		byte type = buffer.get();

		int bodyLength = buffer.getInt();

		if (bodyLength < 0) {
			throw new AioDecodeException("bodyLength [" + bodyLength + "] is not right, remote:" + channelContext.getClientNode());
		}

		int neededLength = BlockPacket.HEADER_LENGTH + bodyLength;
		int test = readableLength - neededLength;
		if (test < 0) // 不够消息体长度(剩下的buffe组不了消息体)
		{
			return null;
		} else {
			BlockPacket imPacket = new BlockPacket();
			imPacket.setType(type);
			if (bodyLength > 0) {
				byte[] dst = new byte[bodyLength];
				buffer.get(dst);
				imPacket.setBody(dst);
			}
			return imPacket;
		}
	}

    /**
     * 编码：把业务消息包编码为可以发送的ByteBuffer
     * 消息头：type + bodyLength
     * 消息体：byte[]
     */
    @Override
    public ByteBuffer encode(Packet packet, GroupContext groupContext, ChannelContext channelContext) {
    	BlockPacket showcasePacket = (BlockPacket) packet;
        byte[] body = showcasePacket.getBody();
        int bodyLen = 0;
        if (body != null) {
            bodyLen = body.length;
        }
        //总长度是消息头的长度+消息体的长度
        int allLen = BlockPacket.HEADER_LENGTH + bodyLen;

        ByteBuffer buffer = ByteBuffer.allocate(allLen);
        buffer.order(groupContext.getByteOrder());

        //写入消息类型
        buffer.put(showcasePacket.getType());
        //写入消息体长度
        buffer.putInt(bodyLen);

        //写入消息体
        if (body != null) {
            buffer.put(body);
        }
        return buffer;
    }
}
