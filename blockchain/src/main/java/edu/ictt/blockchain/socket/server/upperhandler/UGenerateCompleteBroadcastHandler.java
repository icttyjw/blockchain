package edu.ictt.blockchain.socket.server.upperhandler;

import static org.hamcrest.CoreMatchers.nullValue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;

import edu.ictt.blockchain.ApplicationContextProvider;
import edu.ictt.blockchain.Block.block.UpperBlock;
import edu.ictt.blockchain.common.timer.TimerManager;
import edu.ictt.blockchain.core.manager.UDbBlockManager;
import edu.ictt.blockchain.socket.body.upperbody.URpcSimpleBlockBody;
import edu.ictt.blockchain.socket.client.PacketSender;
import edu.ictt.blockchain.socket.common.intf.AbstractBlockHandler;
import edu.ictt.blockchain.socket.packet.BlockPacket;
import edu.ictt.blockchain.socket.packet.UNextBlockPacketBuilder;
import edu.ictt.blockchain.socket.server.handler.GenerateCompleteRequestHandler;

/**
 * 产生新区块并通过共识后，再次全网广播该新区块
 * @author zoe
 *
 */
public class UGenerateCompleteBroadcastHandler  extends AbstractBlockHandler<URpcSimpleBlockBody>{

	private Logger logger = LoggerFactory.getLogger(UGenerateCompleteBroadcastHandler.class);
	@Override
	public Class<URpcSimpleBlockBody> bodyClass() {
		// TODO Auto-generated method stub
		return URpcSimpleBlockBody.class;
	}

	@Override
	public Object handler(BlockPacket packet, URpcSimpleBlockBody bsBody, ChannelContext channelContext)
			throws Exception {
		
		logger.info("[Server校内校间通信]：收到来自于<" + bsBody.getAppId() + "><生成了新的uBlock>消息，uhash为[" + bsBody.getUhash() + "，bhash为：" + bsBody.getBhash()+ "]"
				+ "；当前系统时间为：" + System.currentTimeMillis());

		//延迟2秒校验一下本地是否有该区块，如果没有，则发请求去获取新Block
        //延迟的目的是可能刚好自己也马上就要校验通过同样的Block了，就可以省一次请求
		TimerManager.schedule(()->{
			UpperBlock upperBlock = ApplicationContextProvider.getBean(UDbBlockManager.class).getBlockByHash(bsBody.getUhash());
			
			//如果本地没有
			if(upperBlock == null) {
				logger.info("[Server校内校间通信]：本地没有该新区块，开始去获取别人的新区块");
				BlockPacket blockPacket = UNextBlockPacketBuilder.build();
				ApplicationContextProvider.getBean(PacketSender.class).sendGroup(blockPacket);
				
			}
			return null;
		}, 2000);
		
		return null;
	}

}
