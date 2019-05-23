package edu.ictt.blockchain.Block.generatorUtil;

import edu.ictt.blockchain.Block.block.Block;
import edu.ictt.blockchain.Block.block.UpperBlock;
import edu.ictt.blockchain.Block.block.UpperBlockBody;
import edu.ictt.blockchain.Block.block.UpperBlockHeader;
import edu.ictt.blockchain.common.CommonUtil;
import edu.ictt.blockchain.common.algorithm.ECDSAAlgorithm;

public class UGenerateBlock {
	
	 static String publicKey = GenerateRecord.sPubKey;
     static String privateKey = GenerateRecord.sPriKey;
     
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
