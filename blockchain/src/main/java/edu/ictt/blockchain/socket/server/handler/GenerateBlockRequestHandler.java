package edu.ictt.blockchain.socket.server.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;

import edu.ictt.blockchain.ApplicationContextProvider;
import edu.ictt.blockchain.Block.check.CheckerManager;
import edu.ictt.blockchain.Block.block.Block;
import edu.ictt.blockchain.socket.body.RpcBlockBody;
import edu.ictt.blockchain.socket.body.RpcCheckBlockBody;
import edu.ictt.blockchain.socket.common.intf.AbstractBlockHandler;
import edu.ictt.blockchain.socket.packet.BlockPacket;
import edu.ictt.blockchain.socket.pbft.VoteType;
import edu.ictt.blockchain.socket.pbft.msg.VotePreMsg;
import edu.ictt.blockchain.socket.pbft.queue.MsgQueueManager;

public class GenerateBlockRequestHandler extends AbstractBlockHandler<RpcBlockBody>{

	private Logger logger = LoggerFactory.getLogger(GenerateBlockRequestHandler.class);


    @Override
    public Class<RpcBlockBody> bodyClass() {
        return RpcBlockBody.class;
    }

    @Override
    public Object handler(BlockPacket packet, RpcBlockBody rpcBlockBody, ChannelContext channelContext) {
        Block block = rpcBlockBody.getBlock();
        logger.info("收到来自于<" + rpcBlockBody.getAppId() + "><请求生成Block>消息，block信息为[" + block + "]");

        CheckerManager checkerManager = ApplicationContextProvider.getBean(CheckerManager.class);
        //对区块的基本信息进行校验，校验通过后进入pbft的Pre队列
        RpcCheckBlockBody rpcCheckBlockBody = checkerManager.check(block);
        logger.info("校验结果:" + rpcCheckBlockBody.toString());
        if (rpcCheckBlockBody.getCode() == 0) {
            VotePreMsg votePreMsg = new VotePreMsg();
            votePreMsg.setBlock(block);
            votePreMsg.setVoteType(VoteType.prepare);
            votePreMsg.setNumber(111);//block.getBlockHeader().getNumber()
            votePreMsg.setAppId(rpcBlockBody.getAppId());
            votePreMsg.setHash(block.getBlockHash());
            votePreMsg.setAgree(true);
            //将消息推入PrePrepare队列
            ApplicationContextProvider.getBean(MsgQueueManager.class).push(votePreMsg);
        }

        return null;
    }
}
