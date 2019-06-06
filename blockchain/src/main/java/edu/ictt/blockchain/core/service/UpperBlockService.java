package edu.ictt.blockchain.core.service;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import edu.ictt.blockchain.Block.block.UpperBlock;
import edu.ictt.blockchain.Block.block.UpperBlockBody;
import edu.ictt.blockchain.Block.block.UpperBlockHeader;
import edu.ictt.blockchain.Block.generatorUtil.UGenerateBlock;
import edu.ictt.blockchain.common.CommonUtil;
import edu.ictt.blockchain.core.manager.UDbBlockManager;
import edu.ictt.blockchain.core.requestbody.UpperBlockRequestBody;
import edu.ictt.blockchain.socket.body.upperbody.URpcBlockBody;
import edu.ictt.blockchain.socket.client.PacketSender;
import edu.ictt.blockchain.socket.packet.BlockPacket;
import edu.ictt.blockchain.socket.packet.PacketBuilder;
import edu.ictt.blockchain.socket.packet.PacketType;
import edu.ictt.blockchain.socket.packet.UPacketType;

/**
 * 本地生成新区块或收到远程请求生成新区块看的请求后，生成新区块并发送到群组
 * @author zoe
 *
 */
@Service
public class UpperBlockService {
	@Resource
	private PacketSender packetSender;
	
	private UDbBlockManager uDbBlockManager;
	
	Logger logger=LoggerFactory.getLogger(getClass());
	
	/**
	 * 本地生成新区块
	 * @param uBlockBody
	 * @return
	 */
	public UpperBlock addBlock(UpperBlockBody uBlockBody) {
		//TODO 生成区块头,目前有些字段未设置,包括签名等
		/*UpperBlockHeader uBlockHeader = new UpperBlockHeader();
		uBlockHeader.setUhashPreviousBlock(uDbBlockManager.getLastBlockHash());
		uBlockHeader.setBhash(uBlockBody.getBlock().getBlockHash());
		uBlockHeader.setUblockTimeStamp(CommonUtil.getNow());
		uBlockHeader.setUblockNumber(uDbBlockManager.getLastBlockNumber()+1);*/
		UGenerateBlock uGBlock = new UGenerateBlock();
		//生成区块
		UpperBlock uBlock = uGBlock.geneUBlock(uBlockBody);
		logger.info("[本地产块]：uBlock信息为：[" + uBlock + "]");
		URpcBlockBody uRpcBlockBody = new URpcBlockBody(uBlock);
		
		//TODO sendgroup
		BlockPacket blockPacket = new PacketBuilder<>().setType(UPacketType.GENERATE_BLOCK_REQUEST).setBody(uRpcBlockBody).build();
		logger.info("[校间通信]：向组内其他节点广播该新区块：["+ blockPacket + "]");
		packetSender.sendGroup(blockPacket);
		return uBlock;
	}
	
	/**
	 * 收到远程请求生成新区块的请求后，生成新区块并发送到群组
	 * @param uBlockRequestBody
	 * @return
	 */
	public UpperBlock addBlock(UpperBlockRequestBody uBlockRequestBody) {
		UpperBlockBody uBlockBody = uBlockRequestBody.getuBlockBody();
		/*UpperBlockHeader uBlockHeader = new UpperBlockHeader();
		uBlockHeader.setUhashPreviousBlock("0000");
		//uBlockHeader.setUhashPreviousBlock(uDbBlockManager.getLastBlockHash());
		uBlockHeader.setBhash(uBlockBody.getBlock().getBlockHash());
		uBlockHeader.setUblockTimeStamp(CommonUtil.getNow());
		uBlockHeader.setUblockNumber(1);
		//uBlockHeader.setUblockNumber(uDbBlockManager.getLastBlockNumber()+1);
*/		
		UGenerateBlock uGBlock = new UGenerateBlock();
		//生成区块
		UpperBlock uBlock = uGBlock.geneUBlock(uBlockBody);
		logger.info("[收到远程产块消息]：uBlock信息为：[" + uBlock + "]");
		URpcBlockBody uRpcBlockBody = new URpcBlockBody(uBlock);		
		//TODO sendgroup
		BlockPacket blockPacket = new PacketBuilder<>().setType(UPacketType.GENERATE_BLOCK_REQUEST).setBody(uRpcBlockBody).build();
		logger.info("[校间通信]：向组内其他节点广播该新区块：["+ blockPacket + "]");
		packetSender.sendGroup(blockPacket);
		return uBlock;
	}
	
}
