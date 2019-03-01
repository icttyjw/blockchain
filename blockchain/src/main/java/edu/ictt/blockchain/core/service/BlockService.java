package edu.ictt.blockchain.core.service;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import edu.ictt.blockchain.Block.block.Block;
import edu.ictt.blockchain.Block.block.BlockBody;
import edu.ictt.blockchain.Block.block.BlockHeader;
import edu.ictt.blockchain.common.CommonUtil;
import edu.ictt.blockchain.common.SHA256;
import edu.ictt.blockchain.core.manager.DbBlockManager;
import edu.ictt.blockchain.core.requestbody.BlockRequesbody;
import edu.ictt.blockchain.socket.body.RpcBlockBody;
import edu.ictt.blockchain.socket.client.PacketSender;
import edu.ictt.blockchain.socket.packet.BlockPacket;
import edu.ictt.blockchain.socket.packet.PacketBuilder;
import edu.ictt.blockchain.socket.packet.PacketType;
@Service
public class BlockService {

	@Resource
	private PacketSender packetSender;
	@Resource
	private DbBlockManager dbBlockManager;
	
	Logger logger=LoggerFactory.getLogger(getClass());
	
	public Block addBlock(BlockRequesbody blockRequesbody){
		BlockBody blockBody=blockRequesbody.getBlockBody();
		BlockHeader blockHeader=new BlockHeader();
		blockHeader.setBlockTimeStamp(CommonUtil.getNow());
		blockHeader.setBlockNumber(dbBlockManager.getLastBlockNumber());
		blockHeader.setHashPreviousBlock(dbBlockManager.getLastBlockHash());
		Block block=new Block();
		block.setBlockHeader(blockHeader);
		block.setBlockHash(SHA256.sha256(blockHeader.toString()));
		RpcBlockBody rpcBlockBody=new RpcBlockBody(block);
		BlockPacket blockPacket=new PacketBuilder<>().setType(PacketType.GENERATE_BLOCK_REQUEST).setBody(
				rpcBlockBody).build();
		logger.info(rpcBlockBody.toString());
		packetSender.sendGroup(blockPacket);
		return block;
	}
}
