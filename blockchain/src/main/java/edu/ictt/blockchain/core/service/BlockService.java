package edu.ictt.blockchain.core.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import edu.ictt.blockchain.Block.block.Block;
import edu.ictt.blockchain.Block.block.BlockBody;
import edu.ictt.blockchain.Block.block.BlockHeader;
import edu.ictt.blockchain.Block.me.MerkleTree;
import edu.ictt.blockchain.Block.record.Record;
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
	
	public Block addBlock(BlockBody blockbody){
		BlockHeader blockHeader=new BlockHeader();
		blockHeader.setBlockTimeStamp(CommonUtil.getNow());		
		blockHeader.setBlockNumber(dbBlockManager.getLastBlockNumber()+1);
		blockHeader.setHashPreviousBlock(dbBlockManager.getLastBlockHash());
		Block block=new Block(); 
		block.setBlockHeader(blockHeader);
		block.setBlockBody(blockbody);
		block.setBlockHash(SHA256.sha256(blockHeader.toString())+SHA256.sha256(blockbody.toString()));
		logger.info("block"+block);
		RpcBlockBody rpcBlockBody=new RpcBlockBody(block);
		BlockPacket blockPacket=new PacketBuilder<>().setType(PacketType.GENERATE_BLOCK_REQUEST).setBody(
				rpcBlockBody).build();
		logger.info(rpcBlockBody.toString());
		packetSender.sendGroup(blockPacket);
		return block;
	}
	public Block addBlock(BlockRequesbody blockrequesbody){
		BlockBody blockBody=blockrequesbody.getBlockBody();
		List<Record> lr=blockBody.getRecordsList();
		List<String> hashlist=new ArrayList<>();
		for(Record r:lr){
			String hash=SHA256.sha256(r.toString());
			hashlist.add(hash);
		}
		
		BlockHeader blockHeader=new BlockHeader();
		blockHeader.setHashMerkleRoot(new MerkleTree(hashlist).build().getRoot());
		blockHeader.setBlockTimeStamp(CommonUtil.getNow());		
		blockHeader.setBlockNumber(dbBlockManager.getLastBlockNumber()+1);
		blockHeader.setHashPreviousBlock(dbBlockManager.getLastBlockHash());
		Block block=new Block();
		block.setBlockBody(blockBody);
		block.setBlockHeader(blockHeader);
		block.setBlockHash(SHA256.sha256(blockHeader.toString()+blockBody.toString()));
		logger.info("block"+block);
		RpcBlockBody rpcBlockBody=new RpcBlockBody(block);
		BlockPacket blockPacket=new PacketBuilder<>().setType(PacketType.GENERATE_BLOCK_REQUEST).setBody(
				rpcBlockBody).build();
		logger.info(rpcBlockBody.toString());
		packetSender.sendGroup(blockPacket);
		return block;
	}
}
