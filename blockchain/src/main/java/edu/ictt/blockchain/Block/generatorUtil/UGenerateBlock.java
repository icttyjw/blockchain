package edu.ictt.blockchain.Block.generatorUtil;

import edu.ictt.blockchain.ApplicationContextProvider;
import edu.ictt.blockchain.Block.block.Block;
import edu.ictt.blockchain.Block.block.UpperBlock;
import edu.ictt.blockchain.Block.block.UpperBlockBody;
import edu.ictt.blockchain.Block.block.UpperBlockHeader;
import edu.ictt.blockchain.common.CommonUtil;
import edu.ictt.blockchain.common.algorithm.ECDSAAlgorithm;
import edu.ictt.blockchain.core.manager.UDbBlockManager;

public class UGenerateBlock {
	
	 static String publicKey = GenerateRecord.sPubKey;
     static String privateKey = GenerateRecord.sPriKey;
     
     UDbBlockManager uDbBlockManager = ApplicationContextProvider.getBean(UDbBlockManager.class);
     
	public static UpperBlock geneUBlock(Block block,long i) {
		//生成区块头
		UpperBlockHeader uBlockHeader = new UpperBlockHeader();
		uBlockHeader.setUhashPreviousBlock("0");
		uBlockHeader.setBhash(block.getBlockHash());
		uBlockHeader.setUblockTimeStamp(CommonUtil.getNow());
		uBlockHeader.setUblockNumber(i);
		uBlockHeader.setUnonce(0001);
		uBlockHeader.setUdifficultGoal(0002);
		uBlockHeader.setUpublicKey(publicKey);
		geneSign(uBlockHeader);
		
		//生成区块体
		UpperBlockBody uBlockBody = new UpperBlockBody(block);
		
		//生成区块
		UpperBlock uBlock = new UpperBlock(uBlockHeader, uBlockBody);
		return uBlock;
		
	}

	/**
	 * generate Ublock by the given uBlockBody
	 * @param uBlockBody
	 * @return
	 */
	public UpperBlock geneUBlock(UpperBlockBody uBlockBody) {
		//generate the ublock header
		UpperBlockHeader uBlockHeader = new UpperBlockHeader();
		uBlockHeader.setUhashPreviousBlock(uDbBlockManager.getLastBlockHash());
		uBlockHeader.setBhash(uBlockBody.getBlock().getBlockHash());
		uBlockHeader.setUblockTimeStamp(CommonUtil.getNow());
		uBlockHeader.setUblockNumber(uDbBlockManager.getLastBlockNumber() + 1);
		//TODO give a fixed nonce and difficultgoal
		uBlockHeader.setUnonce(0001);
		uBlockHeader.setUdifficultGoal(0002);
		uBlockHeader.setUpublicKey(publicKey);
		geneSign(uBlockHeader);	
		return new UpperBlock(uBlockHeader, uBlockBody);
		
	}
	public static void geneSign(UpperBlockHeader uBlockHeader) {
		// TODO Auto-generated method stub
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
	}
}
