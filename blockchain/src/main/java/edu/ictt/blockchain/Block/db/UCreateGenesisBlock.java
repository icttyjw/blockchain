package edu.ictt.blockchain.Block.db;

import static edu.ictt.blockchain.common.algorithm.ECDSAAlgorithm.generatePrivateKey;
import static edu.ictt.blockchain.common.algorithm.ECDSAAlgorithm.generatePublicKey;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.tio.utils.json.Json;

import com.alibaba.fastjson.JSON;

import edu.ictt.blockchain.ApplicationContextProvider;
import edu.ictt.blockchain.Block.block.Block;
import edu.ictt.blockchain.Block.block.UpperBlock;
import edu.ictt.blockchain.Block.block.UpperBlockBody;
import edu.ictt.blockchain.Block.block.UpperBlockHeader;
import edu.ictt.blockchain.Block.generatorUtil.GenerateRecord;
import edu.ictt.blockchain.common.CommonUtil;
import edu.ictt.blockchain.common.Constants;
import edu.ictt.blockchain.common.algorithm.ECDSAAlgorithm;
import edu.ictt.blockchain.core.manager.DbBlockManager;
import edu.ictt.blockchain.core.manager.UDbBlockManager;

/**
 * 给定校间的创世块，程序运行即存储创世块，在此基础上build新块
 * @author zoe
 *
 */
//@Component
public class UCreateGenesisBlock {
	
	private Logger logger=LoggerFactory.getLogger(getClass());
	//@Resource
	UDbBlockManager uBlockManager = ApplicationContextProvider.getBean(UDbBlockManager.class);
	
	
	static String privateKey = generatePrivateKey();
    static String  publicKey = generatePublicKey(privateKey, true);
    
    
	
	public UCreateGenesisBlock(Block block) {
		UpperBlockHeader uBlockHeader = new UpperBlockHeader();
		uBlockHeader.setUhashPreviousBlock("0");
		uBlockHeader.setBhash(block.getBlockHash());
		uBlockHeader.setUblockTimeStamp(CommonUtil.getNow());
		uBlockHeader.setUblockNumber(0);
		uBlockHeader.setUnonce(0001);
		uBlockHeader.setUdifficultGoal(0002);
		uBlockHeader.setUpublicKey(publicKey);
		//生成区块头的签名字段
		try {
		  	String blockStr = uBlockHeader.getUhashPreviousBlock()+uBlockHeader.getUblockNumber()+ uBlockHeader.getBhash() +
		  			uBlockHeader.getUblockTimeStamp()+ uBlockHeader.getUnonce()+uBlockHeader.getUblockNumber() +
		  			uBlockHeader.getUdifficultGoal() + uBlockHeader.getUpublicKey();
		  	uBlockHeader.setUblockHeaderSign(ECDSAAlgorithm.sign(privateKey, blockStr));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//生成区块体
		UpperBlockBody uBlockBody = new UpperBlockBody(block);
				
		//生成区块
		UpperBlock uBlock = new UpperBlock(uBlockHeader, uBlockBody);
		logger.info("[创世]：创世U块已生成" + block.toString());
		//logger.info("hash:"+ uBlock.getUpperBlockHash());
		String uhash = uBlock.getUpperBlockHash();
		String bhash = uBlock.getuBlockBody().getBlock().getBlockHash();
		
		//存入数据库
		uBlockManager.put(Constants.U_KEY_LAST_BLOCK,uhash);
		uBlockManager.put(Constants.U_KEY_FIRST_BLOCK,uhash);
		uBlockManager.put(Constants.U_GENESIS_BLOCK,uhash);
		uBlockManager.put(Constants.U_KEY_IN_BLOCK_PREFIX + bhash,uhash);
		uBlockManager.put(Constants.U_KEY_BLOCK_HASH_PREFIX + uhash, Json.toJson(uBlock));
		logger.info("[创世]：创世U区块已存入");
	}
		
}
