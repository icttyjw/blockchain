package edu.ictt.blockchain.socket.disruptor;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.tio.utils.json.Json;



import cn.hutool.core.util.StrUtil;
import edu.ictt.blockchain.socket.body.common.BaseBody;
import edu.ictt.blockchain.socket.client.handler.FetchBlockResponseHandler;
import edu.ictt.blockchain.socket.client.handler.HeartbeatHandler;
import edu.ictt.blockchain.socket.client.handler.NextBlockResponseHandler;
import edu.ictt.blockchain.socket.client.handler.TotalBlockInfoResponseHandler;
import edu.ictt.blockchain.socket.client.upperhandler.UFetchBlockResHandler;
import edu.ictt.blockchain.socket.client.upperhandler.UHeartbeatHandler;
import edu.ictt.blockchain.socket.client.upperhandler.UNextBlockResHandler;
import edu.ictt.blockchain.socket.client.upperhandler.UTotalBlockInfoResHandler;
import edu.ictt.blockchain.socket.common.intf.AbstractBlockHandler;
import edu.ictt.blockchain.socket.disruptor.base.BaseEvent;
import edu.ictt.blockchain.socket.disruptor.base.MessageConsumer;
import edu.ictt.blockchain.socket.packet.BlockPacket;
import edu.ictt.blockchain.socket.packet.PacketType;
import edu.ictt.blockchain.socket.packet.UPacketType;
import edu.ictt.blockchain.socket.server.upperhandler.UFetchBlockRequestHandler;

@Component
public class DisruptorClientConsumer implements MessageConsumer{

	private static Map<Byte, AbstractBlockHandler<?>> handlerMap = new HashMap<>();
    private Logger logger = LoggerFactory.getLogger(getClass());

    static {
    	handlerMap.put(PacketType.FETCH_BLOCK_INFO_RESPONSE, new FetchBlockResponseHandler());
    	handlerMap.put(PacketType.HEART_BEAT, new HeartbeatHandler());
    	handlerMap.put(PacketType.TOTAL_BLOCK_INFO_RESPONSE, new TotalBlockInfoResponseHandler());
    	handlerMap.put(PacketType.NEXT_BLOCK_INFO_RESPONSE, new NextBlockResponseHandler());
    	//上层处理
    	handlerMap.put(UPacketType.FETCH_BLOCK_INFO_RESPONSE, new UFetchBlockResHandler());
    	handlerMap.put(UPacketType.HEART_BEAT, new UHeartbeatHandler());
    	handlerMap.put(UPacketType.TOTAL_BLOCK_INFO_RESPONSE, new UTotalBlockInfoResHandler());
    	handlerMap.put(UPacketType.NEXT_BLOCK_INFO_RESPONSE, new UNextBlockResHandler());
    }

    @Override
    public void receive(BaseEvent baseEvent) throws Exception {
        BlockPacket blockPacket = baseEvent.getBlockPacket();
        Byte type = blockPacket.getType();
        AbstractBlockHandler<?> blockHandler = handlerMap.get(type);
        if (blockHandler == null) {
            return;
        }

        //消费消息
        BaseBody baseBody = Json.toBean(new String(blockPacket.getBody()), BaseBody.class);
        logger.info("[通信]：收到来自于<" + baseBody.getAppId() + ">针对msg<>的回应");

        //String appId = baseBody.getAppId();
        //if (StrUtil.equals(AppId.value, appId)) {
            //是本机
            //return;
        //}

        blockHandler.handler(blockPacket, baseEvent.getChannelContext());
    }
}
