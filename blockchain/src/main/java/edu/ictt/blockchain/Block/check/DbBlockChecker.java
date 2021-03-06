package edu.ictt.blockchain.Block.check;

import edu.ictt.blockchain.Block.block.Block;
import edu.ictt.blockchain.Block.block.BlockHeader;
import edu.ictt.blockchain.Block.block.UpperBlock;
import edu.ictt.blockchain.Block.me.MerkleTree;
import edu.ictt.blockchain.Block.record.NewRecord;
import edu.ictt.blockchain.Block.record.Record;
import edu.ictt.blockchain.common.FastJsonUtil;
import edu.ictt.blockchain.common.SHA256;
import edu.ictt.blockchain.common.algorithm.ECDSAAlgorithm;
import edu.ictt.blockchain.core.manager.DbBlockManager;
import edu.ictt.blockchain.core.service.BlockService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.validation.constraints.Null;

import cn.hutool.core.util.StrUtil;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用本地Block信息对新来的block进行校验,暂时没有考虑权限。
 * 如果是学校私钥持有者对记录进行修改，会保留操作记录但是目前的校验依然会判定正确而不是被修改过。
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

    private Logger logger = LoggerFactory.getLogger(getClass());


    //检查区块的time,hash,sign,num,merkleroot;
    public int checkBlock(Block block){
        //if ((checkNum(block)==0) && (checkHash(block)==0) && (checkSign(block)==0) &&(checkTime(block)==0) && (checkMerkleRoot(block)== 0))
          //  return 0;
    	//newRecord完整校验用这个
    	//if (checkNum(block)+checkHash(block)+checkSign(block)+checkTime(block) + checkNewRecordMR(block)!= 0)
       //       return -1;
    	//单测试用这个
        if ((checkSign(block)==0) && (checkNewRecordMR(block)==0))
            return 0;
        return -1;
    }

    //校验区块号
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
           // logger.info("[校验]：同意生成区块");
        	logger.info("[校验]：区块号正确，进行下一步校验");
            return 0;
        }

        //拒绝
        logger.info("[校验]：该区块号不正确，拒绝区块");
        return -1;
    }

    //校验区块哈希
    @Override
    public int checkHash(Block block) {
        Block localLast = getLastBlock();
        //logger.info("[校验]：本地的最后一个区块是：" + localLast);
        //创世块可以，或者新块的prev等于本地的last hash也可以
        /*if (localLast.equals(null) && block.getBlockHeader().getHashPreviousBlock().equals(null)) {
        	logger.info("[校验]：本地无前续区块，当前区块为第一个区块");
        	logger.info("[校验]：区块哈希正确，进行下一步校验");
        	return 0;
        }else if (StrUtil.equals(localLast.getBlockHash(), block.getBlockHeader().getHashPreviousBlock())) {
        	logger.info("[校验]：区块哈希正确，进行下一步校验");
        	return 0;
        }else {
        	logger.info("[校验]：区块哈希不正确，拒绝区块");
            return -1;
        }   }*/
        if (localLast != null) {
        	if (StrUtil.equals(localLast.getBlockHash(), block.getBlockHeader().getHashPreviousBlock())) {
            	logger.info("[校验]：区块哈希正确，进行下一步校验");
            	return 0;
        }else {
        	logger.info("[校验]：区块哈希不正确，拒绝区块");
        	return -1;
        	}
        }else {
        	logger.info("[校验]：本地无前续区块，当前区块为第一个区块");
        	return 0;
        }
    }

    
    //校验区块时间
    @Override
    public int checkTime(Block block) {
        Block localBlock = getLastBlock();
        //新区块的生成时间比本地的还早
        if (localBlock != null && block.getBlockHeader().getBlockTimeStamp() <= localBlock.getBlockHeader().getBlockTimeStamp()) {
            //拒绝
        	logger.info("[校验]：区块时间有误，拒绝区块");
            return -1;
        }
        logger.info("[校验]：区块时间正确，进行下一步校验");
        return 0;
    }
    
    //校验
    @Override
    public int checkSign(Block block) {
    	//if(checkBlockHashSign(block) != 0) {
    		//return -1;
    	//}
    	//return 0;
    	
    	//logger.info("block header:" + block.getBlockHeader().toString()); 
        String hash;
      //需要对body判空,否则verify会出现空指针错误
        if(block.getBlockBody()==null){
        	logger.info("[校验]：block body 为空 ,为测试方便只校验blockhearder");
            hash = SHA256.sha256(block.getBlockHeader().toString());
        }else {
        	//暂时注释掉了这块,因为blockbody为空,nullpoint影响后续校验
    		//hash = SHA256.sha256(FastJsonUtil.toJSONString(block.getBlockHeader()) +FastJsonUtil.toJSONString(block.getBlockBody()));
        	
    		
    		//签名校验应当是此处的部分
    		try {
    		  	BlockHeader blockHeader = block.getBlockHeader();
    		  	String blockStr = blockHeader.getHashPreviousBlock()+blockHeader.getHashMerkleRoot()+blockHeader.getBlockNumber()+
    	        		blockHeader.getBlockTimeStamp()+blockHeader.getRecordCount()+blockHeader.getNonce()+
    	        		blockHeader.getDifficultGoal()+ +blockHeader.getRecordCount()+blockHeader.getPublicKey();
				if(ECDSAAlgorithm.verify(blockStr, block.getBlockHeader().getBlockHeaderSign(), block.getBlockHeader().getPublicKey())) {
					logger.info("[校验]：区块签名正确，进行下一步校验");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
		/*if(!StrUtil.equals(block.getBlockHash(),hash)) {
			logger.info("[校验]：区块签名不正确，拒绝区块");
			return -1;
		} 
			
		logger.info("[校验]：区块签名正确，进行下一步校验");*/
		return 0;
    }

    /**
     * 校验merkleroot,即校验区块内记录
     * @param  block
     * @return
     */
    @Override
    public int checkMerkleRoot(Block block) {

    	//logger.info("block: "+block);
    	
        List<Record> records = new ArrayList<>();
        //获取记录列表重新生成记录的哈希列表
        if(block.getBlockBody().getGrecordsList()!=null) {
        	 records.addAll(block.getBlockBody().getGrecordsList());
        }else if(block.getBlockBody().getDrecordsList()!=null){
        	records.addAll(block.getBlockBody().getDrecordsList());
        }else if(block.getBlockBody().getRecordList() != null){
        	return checkNewRecordMR(block);
        }else {
        	logger.info("[校验]：区块体为空");
        	return -1;
		}
        
       
       // List<String> recordsHash = block.getBlockHeader().getHashList();
       // System.out.println(recordsHash);

        //重新建树
        //MerkleTree merkleTree = new MerkleTree();
        //List<MerkleNode> merkleNodes = new ArrayList<>();
        List<String> recordsHash=new ArrayList<String>();
        int i = 1;
        for (Record record:records){
        	
        	//MerkleNode merkleNode = new MerkleNode(MerkleHash.create(record.toString()));
        	//if(merkleNode.getHash().equals(recordsHash.get(i))) {
        	//	System.out.println("当前记录的hash无误");
        	//}
        	//i++;
            //merkleNodes.add(merkleNode);
        	String str=FastJsonUtil.toJSONString(record);
        	logger.info("record hash:"+str);
        	recordsHash.add(SHA256.sha256(str));
            //System.out.println("hash of merklenode: " + merkleNodes);
        }

        //merkleTree.buildTree(merkleNodes);
        String merkle=new MerkleTree(recordsHash).build().getRoot();
        
        //logger.info("校验时重新生成的MerkleRoot: " + merkle);
       // logger.info("该区块的MerkleRoot: " + block.getBlockHeader().getHashMerkleRoot()  );

        if(merkle.equals(block.getBlockHeader().getHashMerkleRoot()) ){
        	logger.info("[校验]：区块MerkleRoot和记录校验成功");
            return 0;
        }
        logger.info("[校验]：区块MerkleRoot和记录校验失败，拒绝区块");
    	return -1;
    }

    /**
     * 校验用新记录格式的merkleroot
     * @param block
     * @return
     */
    public int checkNewRecordMR(Block block) {
    	List<String> recordsHash=new ArrayList<String>();
    	List<NewRecord> newRecords = block.getBlockBody().getRecordList();
    	
    	for (NewRecord record:newRecords){
        	String str=FastJsonUtil.toJSONString(record);
        	logger.info("record hash:"+str);
        	recordsHash.add(SHA256.sha256(str));
        }

        String merkle=new MerkleTree(recordsHash).build().getRoot();
        
        if(merkle.equals(block.getBlockHeader().getHashMerkleRoot()) ){
        	logger.info("[校验]：区块MerkleRoot和记录校验成功");
            return 0;
        }
        logger.info("[校验]：区块MerkleRoot和记录校验失败，拒绝区块");
    	return -1;
    }
    /**
     * 检测区块签名是否符合
     * @param block
     * @return
     */

    /*private int checkBlockHashSign(Block block) {

       //logger.info("block header:" + block.getBlockHeader().toString()); 
        String hash;
      //需要对body判空,否则verify会出现空指针错误
        if(block.getBlockBody()==null){
        	logger.info("block body 为空 ,为测试方便只校验blockhearder");
            hash = SHA256.sha256(block.getBlockHeader().toString());
        }else {
        	//暂时注释掉了这块,因为blockbody为空,nullpoint影响后续校验
    		hash = SHA256.sha256(FastJsonUtil.toJSONString(block.getBlockHeader()) +FastJsonUtil.toJSONString(block.getBlockBody()));
        }
        
		if(!StrUtil.equals(block.getBlockHash(),hash)) 
			return -1;
		
		return 0;
	}*/

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