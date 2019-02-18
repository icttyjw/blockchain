package edu.ictt.blockchain.socket.disruptor.base;

import java.io.Serializable;

import org.tio.core.ChannelContext;

import edu.ictt.blockchain.socket.packet.BlockPacket;

public class BaseEvent implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3881197926925935308L;
	private BlockPacket blockPacket;
	private ChannelContext channelContext;
	public BaseEvent(BlockPacket blockPacket, ChannelContext channelContext) {
        this.blockPacket = blockPacket;
        this.channelContext = channelContext;
    }

    public BaseEvent(BlockPacket blockPacket) {
        this.blockPacket = blockPacket;
    }

    public BaseEvent() {
    }

    public ChannelContext getChannelContext() {
        return channelContext;
    }

    public void setChannelContext(ChannelContext channelContext) {
        this.channelContext = channelContext;
    }

    public BlockPacket getBlockPacket() {
        return blockPacket;
    }

    public void setBlockPacket(BlockPacket blockPacket) {
        this.blockPacket = blockPacket;
    }
}
