package edu.ictt.blockchain.Block.check;

import static org.hamcrest.CoreMatchers.nullValue;

import javax.annotation.Resource;

import org.apache.commons.codec.net.URLCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.hutool.core.util.StrUtil;
import edu.ictt.blockchain.Block.block.Block;
import edu.ictt.blockchain.Block.block.BlockHeader;
import edu.ictt.blockchain.Block.block.UpperBlock;
import edu.ictt.blockchain.Block.block.UpperBlockHeader;
import edu.ictt.blockchain.common.algorithm.ECDSAAlgorithm;
import edu.ictt.blockchain.core.manager.UDbBlockManager;
import edu.ictt.blockchain.core.service.UpperBlockService;

/**
 * 根据本地uBlock信息校验新的uBlock:
 * (1)本地至少有一个创世块
 * (2)本地只要在线，则时刻与网络节点的区块保持同步更新
 * (3)所以如果本地渠道的lastBlock为空，说明本地数据被恶意破坏
 *
 */
@Component
public class UBlockChecker {
	
	 private Logger logger = LoggerFactory.getLogger(getClass());
	 
	 @Resource
	 private UDbBlockManager uDbBlockManager;
	 
	 @Resource
	 private DbBlockChecker dbBlockChecker;
	 
	 @Resource
	 private UpperBlockService uBlockService;

	 /**
	  * 校验所有字段
	  */
	 public int checkAllUBlock(UpperBlock uBlock) {
		 UpperBlock ulocalBlock = uDbBlockManager.getLastBlock();
		 if(ulocalBlock == null) {
			 logger.info("[校间校验]：本地没有区块");
			 //TODO 如果本地没有区块需要先请求其他节点，然后进行校验
			 //TODO 如果收到其他节点的回应也没有，则该区块为创世块
			 
		 }
		 if((checkBlockHash(uBlock)==0) && (checkBlockSign(uBlock)==0) && (checkNum(uBlock)==0) && (checkPreHash(uBlock)==0) && (checkTime(uBlock)==0)) {
			 logger.info("[校间校验：uBlock校验成功]");
			 return 0;
		 }
		 return -1;
	 }
	 
	 
	/**
	 * 校验UpperBlock区块号
	 * @param uBlock
	 * @return
	 */
	 int checkNum(UpperBlock uBlock) {
		UpperBlock ulocalBlock = uDbBlockManager.getLastBlock();
		long localNum = 0;
		if(ulocalBlock != null) {
			localNum = ulocalBlock.getuBlockHeader().getUblockNumber();
		}
		 //本地区块+1等于新来的区块时才同意
        if (localNum + 1 == uBlock.getuBlockHeader().getUblockNumber()) {
            //同意生成区块
           // logger.info("[校验]：同意生成区块");
        	logger.info("[校间校验]：区块号正确，进行下一步校验");
            return 0;
        }
      //拒绝
        logger.info("[校间校验]：该区块号不正确，拒绝区块");
        return -1;
		
	}
	
	/**
	 * 校验UpperBlock时间戳
	 * @param uBlock
	 * @return
	 */
	int checkTime(UpperBlock uBlock) {
		UpperBlock ulocalBlock = uDbBlockManager.getLastBlock();
		 //新区块的生成时间比本地的还早
		if(ulocalBlock != null &&  uBlock.getuBlockHeader().getUblockTimeStamp() <= ulocalBlock.getuBlockHeader().getUblockTimeStamp()) {
			//拒绝
        	logger.info("[校间校验]：区块时间有误，拒绝区块");
            return -1;
		} 
		logger.info("[校间校验]：区块时间正确，进行下一步校验");
        return 0;
	}
	
	/**
	 * 校验UpperBlock的Nonce
	 * @param upperBlock
	 * @return
	 */
	/*int checkNonce(UpperBlock upperBlock) {
		if(Long.parseLong(upperBlock.getUpperBlockHash()) < upperBlock.getuBlockHeader().getUdifficultGoal()) {
			logger.info("[校间校验]：区块nonce正确，进行下一步校验");
			return 0;
		}else {
			logger.info("[校间校验]：区块nonce不正确，拒绝区块");
        	return -1;
		}
		
	}*/
	
	/**
	 * 校验UpperBlock的前一区块hash
	 * @param uBlock
	 * @return
	 */
	int checkPreHash(UpperBlock uBlock) {
		UpperBlock ulocalBlock = uDbBlockManager.getLastBlock();
		if(ulocalBlock != null) {
			if(StrUtil.equals(ulocalBlock.getUpperBlockHash(), uBlock.getuBlockHeader().getUhashPreviousBlock())) {
				logger.info("[校间校验]：区块哈希正确，进行下一步校验");
            	return 0;
			}else {
	        	logger.info("[校间校验]：区块哈希不正确，拒绝区块");
	        	return -1;
	        	}
		}else {
        	logger.info("[校间校验]：本地无前续区块，当前区块为第一个区块");
        	return 0;
        }
	}
	
	/**
	 * 校验UpperBlock内的blockhash：需要先校验block，然后重新生成blockhash比较
	 * @param uBlock
	 * @return
	 */
	int checkBlockHash(UpperBlock uBlock) {
		//先校验uBlock内的block
		if(uBlock != null) {
			Block block = uBlock.getuBlockBody().getBlock();
			//校验成功后将blockhash和区块头中blockhash比对
			if(dbBlockChecker.checkBlock(block) == 0 && block.getBlockHash().equals(uBlock.getuBlockHeader().getBhash())) {
				logger.info("[校间校验]：区块内容校验正确，进行下一步校验");
				return 0;
			}else {
	        	logger.info("[校间校验]：区块哈希不正确，拒绝区块");
	        	return -1;
	        }
		}else {
        	logger.info("[校间校验]：本地无前续区块，当前区块为第一个区块");
        	return 0;
        }
	}
	
	/**
	 * 校验区块头的签名
	 */
	int checkBlockSign(UpperBlock uBlock) {
		try {
			UpperBlockHeader uBlockHeader = uBlock.getuBlockHeader();
		  	String blockStr = uBlockHeader.getUhashPreviousBlock()+uBlockHeader.getUblockNumber()+ uBlockHeader.getBhash() +
		  			uBlockHeader.getUblockTimeStamp()+ uBlockHeader.getUnonce()+uBlockHeader.getUblockNumber() +
		  			uBlockHeader.getUdifficultGoal() + uBlockHeader.getUpublicKey();
			if(ECDSAAlgorithm.verify(blockStr, uBlock.getuBlockHeader().getUblockHeaderSign(), uBlock.getuBlockHeader().getUpublicKey())) {
				logger.info("[校间校验]：区块签名正确，进行下一步校验");
				return 0;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.info("[校间校验]：区块签名校验失败，拒绝该区块");
	    return -1;
	}
	
	/**
	 * 创世块单独校验：number,sign和previousehash以及nonce
	 */
	public int checkCreateBlock(UpperBlock upperBlock) {
		
		if(upperBlock.getuBlockHeader().getUblockNumber() == 0 && Long.parseLong(upperBlock.getuBlockHeader().getUhashPreviousBlock()) == 0) {
			logger.info("[创世块校验]"); 
			return checkBlockSign(upperBlock);
		}
		
		logger.info("[创世块校验失败]");
		return -1;
	}

	public void setDbBlockChecker(DbBlockChecker dbBlockChecker) {
		this.dbBlockChecker = dbBlockChecker;
	}
	
	
}