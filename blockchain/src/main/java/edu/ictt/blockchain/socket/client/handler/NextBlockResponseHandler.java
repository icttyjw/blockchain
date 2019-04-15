package edu.ictt.blockchain.socket.client.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;

import edu.ictt.blockchain.ApplicationContextProvider;
import edu.ictt.blockchain.socket.body.BlockHash;
import edu.ictt.blockchain.socket.body.RpcNextBlockBody;
import edu.ictt.blockchain.socket.common.intf.AbstractBlockHandler;
import edu.ictt.blockchain.socket.packet.BlockPacket;
import edu.ictt.blockchain.socket.pbft.queue.NextBlockQueue;

public class NextBlockResponseHandler extends AbstractBlockHandler<RpcNextBlockBody>{

	private Logger logger = LoggerFactory.getLogger(TotalBlockInfoResponseHandler.class);

    @Override
    public Class<RpcNextBlockBody> bodyClass() {
        return RpcNextBlockBody.class;
    }

    @Override
    public Object handler(BlockPacket packet, RpcNextBlockBody rpcBlockBody, ChannelContext channelContext) {
        logger.info("[通信]：收到来自于<" + rpcBlockBody.getAppId() + ">的回复，下一个Block hash为：" + rpcBlockBody.getHash());

        String hash = rpcBlockBody.getHash();
        //如果为null，说明对方根据我们传过去的hash，找不到next block。说明要么已经是最新了，要么对方的block比自己的少
        if (hash == null) {
            logger.info("[通信]：和<" + rpcBlockBody.getAppId() + ">相比，本地已是最新块了");
        } else {
            BlockHash blockHash = new BlockHash(hash, rpcBlockBody.getPrevHash(), rpcBlockBody.getAppId());
            //此处进行搜集next block的hash，相同的hash过2f+1时可以确认
            ApplicationContextProvider.getBean(NextBlockQueue.class).push(blockHash);
        }

        return null;
    }
}
