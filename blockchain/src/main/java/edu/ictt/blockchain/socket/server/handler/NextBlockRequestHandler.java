package edu.ictt.blockchain.socket.server.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.Tio;
import org.tio.core.ChannelContext;
import org.tio.utils.json.Json;

import edu.ictt.blockchain.ApplicationContextProvider;
import edu.ictt.blockchain.Block.block.Block;
import edu.ictt.blockchain.core.manager.DbBlockManager;
import edu.ictt.blockchain.socket.body.lowerbody.RpcNextBlockBody;
import edu.ictt.blockchain.socket.body.lowerbody.RpcSimpleBlockBody;
import edu.ictt.blockchain.socket.common.intf.AbstractBlockHandler;
import edu.ictt.blockchain.socket.packet.BlockPacket;
import edu.ictt.blockchain.socket.packet.PacketBuilder;
import edu.ictt.blockchain.socket.packet.PacketType;

/**
 * 获取某个区块下一块的请求，发起方带着自己的lastBlock hash，接收方则将自己的区块中，在传来的hash后面的那块返回回去。<p>
 * 如A传来了3，而我本地有5个区块，则返回区块4。
 */
public class NextBlockRequestHandler extends AbstractBlockHandler<RpcSimpleBlockBody>{

	private Logger logger = LoggerFactory.getLogger(TotalBlockInfoRequestHandler.class);

    @Override
    public Class<RpcSimpleBlockBody> bodyClass() {
        return RpcSimpleBlockBody.class;
    }

    @Override
    public Object handler(BlockPacket packet, RpcSimpleBlockBody rpcBlockBody, ChannelContext channelContext) {
        logger.info("[通信]：收到来自于<" + rpcBlockBody.getAppId() + ">的<请求下一Block>消息，请求者的block hash为：" + Json.toJson
                (rpcBlockBody.getHash()));
        //传来的Block，如果为null，说明发起方连一个Block都没有
        String hash = rpcBlockBody.getHash();

        //查询自己的next block hash，返回给对方，让对方搜集2f+1后确定哪个是对的
        Block nextBlock = ApplicationContextProvider.getBean(DbBlockManager.class).getNextBlockByHash(hash);
        String nextHash = null;
        if (nextBlock != null) {
            nextHash = nextBlock.getBlockHash();
        }
        RpcNextBlockBody respBody = new RpcNextBlockBody(nextHash, hash);
        //respBody.setResponseMsgId("111");//rpcBlockBody.getMessageId()
        BlockPacket blockPacket = new PacketBuilder<RpcNextBlockBody>().setType(PacketType
                .NEXT_BLOCK_INFO_RESPONSE).setBody(respBody).build();
        Tio.send(channelContext, blockPacket);
        logger.info("[通信]：回复给<" + rpcBlockBody.getAppId() + ">，我的nextBlock是" + respBody.toString());

        return null;
    }
}
