package edu.ictt.blockchain.Block.generatorUtil;

import edu.ictt.blockchain.Block.block.Block;
import edu.ictt.blockchain.Block.block.BlockBody;
import edu.ictt.blockchain.Block.block.BlockHeader;
import edu.ictt.blockchain.Block.check.DbBlockChecker;
import edu.ictt.blockchain.Block.merkle.MerkleHash;
import edu.ictt.blockchain.Block.merkle.MerkleNode;
import edu.ictt.blockchain.Block.merkle.MerkleTree;
import edu.ictt.blockchain.Block.record.GradeRecord;
import edu.ictt.blockchain.Block.record.Record;
import edu.ictt.blockchain.common.SHA256;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author:zoe
 * @Description:产生给定区块号的区块
 * @Date:
 * @Modified By:
 */
public class GenerateBlock {
	
    public static Block generateBlock(int i){

        //生成记录
        List<GradeRecord> records = new ArrayList<>();
        //List<String> recordHash = new ArrayList<>();
        List<MerkleNode> merkleNodes = new ArrayList<>();
        for(int j=0; j<10; j++){
            GradeRecord record = GenerateRecord.geneGRecord();
            records.add(record);

            //注意MerkleHash不是用SHA256生成的
            MerkleNode merkleNode = new MerkleNode(MerkleHash.create(record.toString()));
            System.out.println("hash of the merklenode: " + merkleNode.getHash());
            merkleNodes.add(merkleNode);
            //recordHash.add(merkleNode.getHash().toString());
           // System.out.println("hash of the record: "+ recordHash);
        }

        //生成MerkleRoot
        MerkleTree merkleTree = new MerkleTree();
        //merkleTree.appendLeaves(merkleNodes);
        merkleTree.buildTree(merkleNodes);
        
        

        //用记录生成blockbody
        BlockBody blockBody = new BlockBody();
        blockBody.setGrecordsList(records);

        //生成BlockHeader
        BlockHeader blockHeader = new BlockHeader();
        blockHeader.setBlockNumber(i);
        blockHeader.setBlockTimeStamp(System.currentTimeMillis());
        blockHeader.setRecordCount(records.size());
        blockHeader.setNounce(1);
        blockHeader.setHashPreviousBlock("0");
        blockHeader.setDifficultGoal(1);
        //blockHeader.setHashList(recordHash);
        blockHeader.setHashMerkleRoot(merkleTree.getRoot().getHash().toString());

        //生成区块
        Block block = new Block();
        block.setBlockHeader(blockHeader);
        block.setBlockBody(blockBody);

        return block;
    }
    
    /*public static void main(String[] args) {
    	Block block = generateBlock(1);
    	DbBlockChecker dbBlockChecker = new DbBlockChecker();
    	dbBlockChecker.checkBlock(block);
    }*/
}
