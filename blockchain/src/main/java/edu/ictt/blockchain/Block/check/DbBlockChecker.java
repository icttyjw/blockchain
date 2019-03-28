package edu.ictt.blockchain.Block.check;

import edu.ictt.blockchain.Block.block.Block;
import edu.ictt.blockchain.Block.merkle.MerkleHash;
import edu.ictt.blockchain.Block.merkle.MerkleNode;
import edu.ictt.blockchain.Block.merkle.MerkleTree;
import edu.ictt.blockchain.Block.record.Record;
import edu.ictt.blockchain.common.SHA256;
import edu.ictt.blockchain.core.manager.DbBlockManager;
import edu.ictt.blockchain.core.service.BlockService;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import cn.hutool.core.util.StrUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用本地Block信息对新来的block进行校验,暂时没有考虑权限
 * @author wuweifeng wrote on 2018/3/13.
 */
@Component
public class DbBlockChecker implements BlockChecker {
    @Resource
    private DbBlockManager dbBlockManager;

    // @Resource
   // private PermissionManager permissionManager;
    
    @Resource
    private BlockService blockService;

    //检查区块的time，hash，sign和num;
    public boolean checkAll(Block block){

        int checkResult = checkNum(block)+checkHash(block)+checkSign(block)+checkTime(block);

        if (checkResult == 0)
            return true;
        return false;
    }


    @Override
    public int checkNum(Block block) {
        Block localBlock = dbBlockManager.getLastBlock();//getLastBlock();
        long localNum = 0;
        if (localBlock != null) {
            localNum = localBlock.getBlockHeader().getBlockNumber();
        }
        //本地区块+1等于新来的区块时才同意
        if (localNum + 1 == block.getBlockHeader().getBlockNumber()) {
            //同意生成区块
            System.out.println("同意生成区块");
            return 0;
        }

        //拒绝
        System.out.println("该区块号不正确，拒绝区块");
        return -1;
    }


    @Override
    public int checkHash(Block block) {
        Block localLast = getLastBlock();
        //创世块可以，或者新块的prev等于本地的last hash也可以
        if (localLast == null && block.getBlockHeader().getHashPreviousBlock() == null) {
            return 0;
        }
        if (localLast != null && StrUtil.equals(localLast.getBlockHash(), block.getBlockHeader().getHashPreviousBlock())) {
            return 0;
        }
        return -1;
    }

    @Override
    public int checkTime(Block block) {
        Block localBlock = getLastBlock();
        //新区块的生成时间比本地的还小
        if (localBlock != null && block.getBlockHeader().getBlockTimeStamp() <= localBlock.getBlockHeader().getBlockTimeStamp()) {
            //拒绝
            return -1;
        }
        return 0;
    }
    
    @Override
    public int checkSign(Block block) {
    	if(!checkBlockHashSign(block)) {
    		return -1;
    	}
    	return 0;
    }

    /**
     * 校验区块本身内容，即记录联合merkleroot
     * @param  block
     * @return
     */
    @Override
    public int checkBlock(Block block) {

        List<Record> records = new ArrayList<>();
        //获取记录列表重新生成记录的哈希列表
        records.addAll(block.getBlockBody().getRecordsList());

        //重新建树
        MerkleTree merkleTree = new MerkleTree();
        List<MerkleNode> merkleNodes = new ArrayList<>();
        for (Record record:records){
            merkleNodes.add(new MerkleNode(MerkleHash.create(record.toString())));
        }

        merkleTree.buildTree(merkleNodes);

        if(merkleTree.getRoot().getHash().toString() == block.getBlockHeader().getHashMerkleRoot()){
            return 0;
        }

    	return -1;
    }

    /**
     * 检测区块签名及hash是否符合
     * @param block
     * @return
     */

    private boolean checkBlockHashSign(Block block) {


        //签名校验.利用公钥解密原始签名，比对摘要信息和公钥

        //hash校验
		String hash = SHA256.sha256(block.getBlockHeader().toString() + block.getBlockBody().toString());
		if(!StrUtil.equals(block.getBlockHash(),hash)) return false;
		
		return true;
	}


    private Block getLastBlock() {
        return dbBlockManager.getLastBlock();
    }

    public DbBlockManager getDbBlockManager() {
        return dbBlockManager;
    }

    public void setDbBlockManager(DbBlockManager dbBlockManager) {
        this.dbBlockManager = dbBlockManager;
    }

    public BlockService getBlockService() {
        return blockService;
    }

    public void setBlockService(BlockService blockService) {
        this.blockService = blockService;
    }

    /** @Override
    public int checkPermission(Block block) {
    校验对表的操作权限
    return permissionManager.checkPermission(block) ? 0 : -1;
    }*/

}