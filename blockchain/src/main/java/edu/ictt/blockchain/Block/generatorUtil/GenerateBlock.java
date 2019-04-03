package edu.ictt.blockchain.Block.generatorUtil;

import edu.ictt.blockchain.Block.block.Block;
import edu.ictt.blockchain.Block.block.BlockBody;
import edu.ictt.blockchain.Block.block.BlockHeader;
import edu.ictt.blockchain.Block.merkle.MerkleHash;
import edu.ictt.blockchain.Block.merkle.MerkleNode;
import edu.ictt.blockchain.Block.merkle.MerkleTree;
import edu.ictt.blockchain.Block.record.Record;
import edu.ictt.blockchain.common.SHA256;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:zoe
 * @Description:
 * @Date:
 * @Modified By:
 */
public class GenerateBlock {

    public static Block generateBlock(int i){

        //生成记录
        List<Record> records = new ArrayList<>();
        List<String> recordHash = new ArrayList<>();
        List<MerkleNode> merkleNodes = new ArrayList<>();
        for(int j=0; j<10; j++){
            Record record = GenerateRecord.geneGRecord();
            records.add(record);

            //注意MerkleHash不是用SHA256生成的
            MerkleNode merkleNode = new MerkleNode(MerkleHash.create(record.toString()));
            merkleNodes.add(merkleNode);
            recordHash.add(merkleNode.getHash().toString());
        }

        //生成MerkleRoot
        MerkleTree merkleTree = new MerkleTree();
        //merkleTree.appendLeaves(merkleNodes);
        merkleTree.buildTree(merkleNodes);

        //用记录生成blockbody
        BlockBody blockBody = new BlockBody();
        blockBody.setRecordsList(records);

        //生成BlockHeader
        BlockHeader blockHeader = new BlockHeader();
        blockHeader.setBlockNumber(i);
        blockHeader.setBlockTimeStamp(System.currentTimeMillis());
        blockHeader.setRecordCount(records.size());
        blockHeader.setNounce(1);
        blockHeader.setHashPreviousBlock("0");
        blockHeader.setDifficultGoal(1);
        blockHeader.setHashList(recordHash);
        blockHeader.setHashMerkleRoot(merkleTree.getRoot().getHash().toString());

        //生成区块
        Block block = new Block();
        block.setBlockHeader(blockHeader);
        block.setBlockBody(blockBody);

        return block;
    }
}
