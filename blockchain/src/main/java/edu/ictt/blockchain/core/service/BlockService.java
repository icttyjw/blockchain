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
import edu.ictt.blockchain.Block.generatorUtil.GenerateBlock;
import edu.ictt.blockchain.Block.me.MerkleTree;
import edu.ictt.blockchain.Block.record.GradeRecord;
import edu.ictt.blockchain.Block.record.Record;
import edu.ictt.blockchain.common.CommonUtil;
import edu.ictt.blockchain.common.FastJsonUtil;
import edu.ictt.blockchain.common.SHA256;
import edu.ictt.blockchain.core.manager.DbBlockManager;
import edu.ictt.blockchain.core.requestbody.BlockRequesbody;
import edu.ictt.blockchain.socket.body.lowerbody.RpcBlockBody;
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
	//从本地生成新区块并发送到群组
	public Block addBlock(BlockBody blockbody){
		/*BlockHeader blockHeader=new BlockHeader();
		blockHeader.setBlockTimeStamp(CommonUtil.getNow());		
		blockHeader.setBlockNumber(dbBlockManager.getLastBlockNumber()+1);
		blockHeader.setHashPreviousBlock(dbBlockManager.getLastBlockHash());
		Block block=new Block(); 
		block.setBlockHeader(blockHeader);
		block.setBlockBody(blockbody);
		block.setBlockHash(SHA256.sha256(blockHeader.toString())+SHA256.sha256(blockbody.toString()));*/
		GenerateBlock generateBlock = new GenerateBlock();
		Block block = generateBlock.generateBlock(blockbody);
		logger.info("[本地生成新区块]："+block);
		RpcBlockBody rpcBlockBody=new RpcBlockBody(block);
		BlockPacket blockPacket=new PacketBuilder<>().setType(PacketType.GENERATE_BLOCK_REQUEST).setBody(
				rpcBlockBody).build();
		logger.info(rpcBlockBody.toString());
		packetSender.sendGroup(blockPacket);
		logger.info("[本地新区块已发送到group]");
		return block;
	}
	//收到远程blockrequesbody生成区块并发送到群组
	public Block addBlock(BlockRequesbody blockrequesbody){
		BlockBody blockBody=blockrequesbody.getBlockBody();
		/*List<GradeRecord> lr=blockBody.getGrecordsList();
		List<String> hashlist=new ArrayList<>();
		for(Record r:lr){
			String hash=SHA256.sha256(FastJsonUtil.toJSONString(r));
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
		block.setBlockHash(SHA256.sha256(FastJsonUtil.toJSONString(blockHeader)+FastJsonUtil.toJSONString(blockBody)));*/
		GenerateBlock generateBlock = new GenerateBlock();
		Block block = generateBlock.generateBlock(blockBody);
		logger.info("[本地根据远程blockrequesbody生成新区块]："+block);
		RpcBlockBody rpcBlockBody=new RpcBlockBody(block);
		BlockPacket blockPacket=new PacketBuilder<>().setType(PacketType.GENERATE_BLOCK_REQUEST).setBody(
				rpcBlockBody).build();
		logger.info(rpcBlockBody.toString());
		packetSender.sendGroup(blockPacket);
		logger.info("[该新区块已发送到group]");
		return block;
	}
}
