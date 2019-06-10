package edu.ictt.blockchain.Block.generatorUtil;

import edu.ictt.blockchain.ApplicationContextProvider;
import edu.ictt.blockchain.Block.block.Block;
import edu.ictt.blockchain.Block.block.BlockBody;
import edu.ictt.blockchain.Block.block.BlockHeader;
import edu.ictt.blockchain.Block.check.DbBlockChecker;
import edu.ictt.blockchain.Block.me.MerkleTree;
import edu.ictt.blockchain.Block.record.DegreeRecord;
import edu.ictt.blockchain.Block.record.GradeRecord;
import edu.ictt.blockchain.Block.record.NewRecord;
import edu.ictt.blockchain.Block.record.Record;
import edu.ictt.blockchain.common.FastJsonUtil;
import edu.ictt.blockchain.common.CommonUtil;
import edu.ictt.blockchain.common.SHA256;
import edu.ictt.blockchain.common.algorithm.ECDSAAlgorithm;
import edu.ictt.blockchain.core.manager.DbBlockManager;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author:zoe
 * @Description:产生给定区块号的区块
 * @Date:
 * @Modified By:
 */
@Component
public class GenerateBlock {
	
	DbBlockManager dbBlockManager = ApplicationContextProvider.getBean(DbBlockManager.class);
	
    public static Block generateBlock(int i){
    	
        //生成记录
        List<GradeRecord> records = new ArrayList<>();
        //List<String> recordHash = new ArrayList<>();
        //List<MerkleNode> merkleNodes = new ArrayList<>();
        List<String> hashlist=new ArrayList<String>();
        for(int j=0; j<10; j++){
            GradeRecord record = GenerateRecord.geneGRecord();
            records.add(record);
            hashlist.add(record.toString());
            //注意MerkleHash不是用SHA256生成的
            //MerkleNode merkleNode = new MerkleNode(MerkleHash.create(record.toString()));
           // System.out.println("hash of the merklenode: " + merkleNode.getHash());
           // merkleNodes.add(merkleNode);
            //recordHash.add(merkleNode.getHash().toString());
           // System.out.println("hash of the record: "+ recordHash);
        }

        //生成MerkleRoot
       // MerkleTree merkleTree = new MerkleTree();
        //merkleTree.appendLeaves(merkleNodes);
       // merkleTree.buildTree(merkleNodes);
        
        
        //用记录生成blockbody
        BlockBody blockBody = new BlockBody();
        blockBody.setGrecordsList(records);
        
        String publicKey = GenerateRecord.sPubKey;
        String privateKey = GenerateRecord.sPriKey;

        //生成BlockHeader
        BlockHeader blockHeader = new BlockHeader();
        blockHeader.setBlockNumber(i);
        blockHeader.setBlockTimeStamp(System.currentTimeMillis());
        blockHeader.setRecordCount(records.size());
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
        //blockHeader.setHashList(recordHash);
        System.out.println(1);
        String merkle=new MerkleTree(hashlist).build().getRoot();
        blockHeader.setHashMerkleRoot(merkle);
        System.out.println(2);
        //生成区块
        Block block = new Block();
        block.setBlockHeader(blockHeader);
        block.setBlockBody(blockBody);

        return block;
    }
    
    /**
     * generate Block by the given BlockBody
     * @param blockBody
     * @return
     */
    public Block generateBlock(BlockBody blockBody) {
    	//TODO 公私钥暂时给定
    	String publicKey = GenerateRecord.sPubKey;
        String privateKey = GenerateRecord.sPriKey;
        
        //fill blockheader 
    	BlockHeader blockHeader=new BlockHeader();
		blockHeader.setBlockTimeStamp(CommonUtil.getNow());	
		//if there is no block in local DB,then generates a genesis block
		
		if(dbBlockManager.getLastBlockNumber()==0) {
			blockHeader.setBlockNumber(1);
			//TODO it is going to be generated by schoolID of newRecord
			blockHeader.setHashPreviousBlock(SHA256.sha256("123456"));
			System.out.println("[^^^^^^^]" + SHA256.sha256("123456"));
			//blockHeader.setHashPreviousBlock(SHA256.sha256(String.valueOf(blockBody.getRecordList().get(0).getGradeInfo().getSchoolInfo().getSchoolId())));
		}else {
			blockHeader.setBlockNumber(dbBlockManager.getLastBlockNumber() + 1);
			blockHeader.setHashPreviousBlock(dbBlockManager.getLastBlockHash());
		}
		
		//TODO give a fixed nonce and difficultgoal
		blockHeader.setNonce(1);
        blockHeader.setDifficultGoal(1);
        blockHeader.setPublicKey(publicKey);
        
       //get the records to set recordcount and compute hash of these records
        //TODO  use SHA256 to do hash temporarily
        List<String> hashList=new ArrayList<String>();
        if(blockBody.getGrecordsList()!=null) {
        	blockHeader.setRecordCount(blockBody.getGrecordsList().size());
        	//blockHeader.setHashPreviousBlock(SHA256.sha256(String.valueOf(blockBody.getGrecordsList().get(0).getSchoolInfo().getSchoolId())));
        	for(GradeRecord gRecord:blockBody.getGrecordsList()) {
        		hashList.add(SHA256.sha256(FastJsonUtil.toJSONString(gRecord)));
        		//blockHeader.setHashPreviousBlock(SHA256.sha256(String.valueOf(gRecord.getSchoolInfo().getSchoolId())));
        	}
        }else if(blockBody.getDrecordsList()!=null) {
        	blockHeader.setRecordCount(blockBody.getDrecordsList().size());
        	for(DegreeRecord dRecord: blockBody.getDrecordsList()) {
        		hashList.add(SHA256.sha256(FastJsonUtil.toJSONString(dRecord)));
        	}
        }else if(blockBody.getRecordList()!=null) {
        	blockHeader.setRecordCount(blockBody.getRecordList().size());
        	for(NewRecord nRecord: blockBody.getRecordList()) {
        		hashList.add(SHA256.sha256(FastJsonUtil.toJSONString(nRecord)));
        	}
        }else {
			blockHeader.setRecordCount(0);
		}
		
        //sign of the block
        try {
            String blockStr = blockHeader.getHashPreviousBlock()+blockHeader.getHashMerkleRoot()+
    				 blockHeader.getNonce()+blockHeader.getDifficultGoal()+blockHeader.getBlockTimeStamp()
    				 +blockHeader.getRecordCount()+blockHeader.getPublicKey();
    			blockHeader.setBlockHeaderSign(ECDSAAlgorithm.sign(privateKey, blockStr));
    		} catch (UnsupportedEncodingException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        
        //merkle root
        //TODO a simple implement for temporary
        String merkle=new MerkleTree(hashList).build().getRoot();
        blockHeader.setHashMerkleRoot(merkle);    

		return new Block(blockHeader,blockBody); 
    }
    
    
    /*public static void main(String[] args) {
    	Block block = generateBlock(1);
    	DbBlockChecker dbBlockChecker = new DbBlockChecker();
    	dbBlockChecker.checkBlock(block);
    }*/
}
