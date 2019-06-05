package edu.ictt.blockchain.Block.db;

import static edu.ictt.blockchain.common.algorithm.ECDSAAlgorithm.generatePrivateKey;
import static edu.ictt.blockchain.common.algorithm.ECDSAAlgorithm.generatePublicKey;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import edu.ictt.blockchain.ApplicationContextProvider;
import edu.ictt.blockchain.Block.block.Block;
import edu.ictt.blockchain.Block.block.BlockBody;
import edu.ictt.blockchain.Block.block.BlockHeader;
import edu.ictt.blockchain.Block.me.MerkleTree;
import edu.ictt.blockchain.Block.record.NewRecord;
import edu.ictt.blockchain.common.FastJsonUtil;
import edu.ictt.blockchain.common.SHA256;
import edu.ictt.blockchain.common.algorithm.ECDSAAlgorithm;
import edu.ictt.blockchain.core.manager.DbBlockManager;

/**
 * 给定校内的创世块，程序运行即存储创世块，在此基础上创新块
 * @author zoe
 *
 */
//@Component
public class CreateGenesisBlock {
	
	//@Resource
	//DbBlockManager dbBlockManager;
	DbBlockManager dbBlockManager = ApplicationContextProvider.getBean(DbBlockManager.class);
	
	private String privateKey = generatePrivateKey();
	private String publicKey = generatePublicKey(privateKey, true);
	
	//生成BlockHeader
    public CreateGenesisBlock() {
    	
    	//填充一条记录
    	NewRecord nRecord = new NewRecord();
    	nRecord.setComment("创世区块");
    	List<NewRecord> nRecords = new ArrayList<>();
    	nRecords.add(nRecord);
    	List<String> hashlist=new ArrayList<String>();
		String str=FastJsonUtil.toJSONString(nRecord);
		hashlist.add(SHA256.sha256(str.toString()));
		String merkle=new MerkleTree(hashlist).build().getRoot();
		
		BlockHeader blockHeader = new BlockHeader();
        blockHeader.setHashMerkleRoot(merkle);
        blockHeader.setBlockNumber(0);
      //TODO 创世块的时间给定。。
        blockHeader.setBlockTimeStamp(15596989);
        blockHeader.setRecordCount(nRecords.size());
        blockHeader.setNonce(1);
        blockHeader.setHashPreviousBlock("0");
        blockHeader.setDifficultGoal(1);
        blockHeader.setPublicKey(publicKey);
        //增加了对区块头的签名
        try {
        String blockStr = blockHeader.getHashPreviousBlock()+blockHeader.getHashMerkleRoot()+
    			 blockHeader.getNonce()+blockHeader.getDifficultGoal()+blockHeader.getBlockTimeStamp()
    			 +blockHeader.getRecordCount()+blockHeader.getPublicKey();
    		blockHeader.setBlockHeaderSign(ECDSAAlgorithm.sign(privateKey, blockStr));
    	} catch (UnsupportedEncodingException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}

        BlockBody blockBody = new BlockBody(nRecords);
        //生成区块
        Block block = new Block(blockHeader,blockBody);
        System.out.println("创世块已生成" + block.toString());
        System.out.println("hash:"+ block.getBlockHash());
        
      //存入数据库
      	dbBlockManager.put(block.getBlockHash(), JSON.toJSONString(block));
      	dbBlockManager.put("key_last_block", block.getBlockHash());
      	dbBlockManager.put("key_first_block", block.getBlockHash());
      	dbBlockManager.put("GENESIS_BLOCK",block.getBlockHash());
      	System.out.println("创世区块已存入");

	}
}
