package edu.ictt.blockchain.socket.client.upperhandler;

import static org.mockito.Matchers.byteThat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;

import edu.ictt.blockchain.ApplicationContextProvider;
import edu.ictt.blockchain.socket.body.upperbody.UBlockHash;
import edu.ictt.blockchain.socket.body.upperbody.URpcNextBlockBody;
import edu.ictt.blockchain.socket.client.handler.TotalBlockInfoResponseHandler;
import edu.ictt.blockchain.socket.common.intf.AbstractBlockHandler;
import edu.ictt.blockchain.socket.packet.BlockPacket;
import edu.ictt.blockchain.socket.upperpbft.queue.UNextBlockQueue;

/**
 * 请求下一区块
 * @author zoe
 *
 */
public class UNextBlockResHandler extends AbstractBlockHandler<URpcNextBlockBody>{

	private Logger logger = LoggerFactory.getLogger(TotalBlockInfoResponseHandler.class);
	
	@Override
	public Class<URpcNextBlockBody> bodyClass() {
		// TODO Auto-generated method stub
		return URpcNextBlockBody.class;
	}

	@Override
	public Object handler(BlockPacket packet, URpcNextBlockBody bsBody, ChannelContext channelContext)
			throws Exception {
		logger.info("[Client校间通信]：收到来自于<" + bsBody.getAppId() + ">的回复，下一个Block hash为：" + bsBody.getUhash() + "其封装的校内区块的Block hash为：" + bsBody.getBhash());
		
		String uhash = bsBody.getUhash();
		if(uhash==null) {
			logger.info("[Client校间通信]：和<" + bsBody.getAppId() + ">相比，本地已是最新块了");
		}else {
			UBlockHash uBlockHash = new UBlockHash(uhash, bsBody.getBhash(), bsBody.getPreUHash(), bsBody.getPreBHash(), bsBody.getAppId());
			ApplicationContextProvider.getBean(UNextBlockQueue.class).push(uBlockHash);
		}
		return null;
	}
	
}
