package edu.ictt.blockchain.Block.generatorUtil;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import edu.ictt.blockchain.Block.block.Block;
import edu.ictt.blockchain.Block.block.BlockBody;
import edu.ictt.blockchain.Block.block.BlockHeader;
import edu.ictt.blockchain.Block.me.MerkleTree;
import edu.ictt.blockchain.Block.record.GradeRecord;
import edu.ictt.blockchain.Block.record.NewRecord;
import edu.ictt.blockchain.common.FastJsonUtil;
import edu.ictt.blockchain.common.SHA256;
import edu.ictt.blockchain.common.algorithm.ECDSAAlgorithm;

public class GenerateNewBlock {
	
	public Block GenerateNewBlock() throws UnsupportedEncodingException {
		
		GenerateNewRecord geneRecord = new GenerateNewRecord();
		NewRecord record = geneRecord.generateNewGRecord();
		
		List<NewRecord> newRecords = new ArrayList<>();
		newRecords.add(record);
		List<String> hashlist=new ArrayList<String>();
		String str=FastJsonUtil.toJSONString(record);
		hashlist.add(SHA256.sha256(str.toString()));
		String merkle=new MerkleTree(hashlist).build().getRoot();
        
	     
		Block block = new Block();
		
		//生成BlockHeader
		BlockHeader blockHeader = new BlockHeader();
        blockHeader.setHashPreviousBlock("1230000");
        blockHeader.setHashMerkleRoot(merkle);
        blockHeader.setBlockNumber(0);
        blockHeader.setBlockTimeStamp(System.currentTimeMillis());
        blockHeader.setRecordCount(newRecords.size());
        blockHeader.setNonce(1);
        blockHeader.setDifficultGoal(1);
        blockHeader.setPublicKey(record.getGradeInfo().getSchoolInfo().getSchoolPubKey());
        //增加了对区块头的签名
        try {
        String blockStr = blockHeader.getHashPreviousBlock()+blockHeader.getHashMerkleRoot()+blockHeader.getBlockNumber()+
        		blockHeader.getBlockTimeStamp()+blockHeader.getRecordCount()+blockHeader.getNonce()+
        		blockHeader.getDifficultGoal()+ +blockHeader.getRecordCount()+blockHeader.getPublicKey();
			blockHeader.setBlockHeaderSign(ECDSAAlgorithm.sign(geneRecord.getsPriKey(), blockStr));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		block.setBlockHeader(blockHeader);
		block.setBlockBody(new BlockBody(newRecords));
        block.setBlockHash();
        
		return block;
		
		
	} 
}
