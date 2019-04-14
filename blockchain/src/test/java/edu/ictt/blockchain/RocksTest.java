package edu.ictt.blockchain;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import edu.ictt.blockchain.common.SHA256;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import edu.ictt.blockchain.Block.block.Block;
import edu.ictt.blockchain.Block.check.DbBlockChecker;
import edu.ictt.blockchain.Block.generatorUtil.GenerateBlock;
import edu.ictt.blockchain.core.manager.DbBlockManager;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={BlockChainApplication.class})
@ContextConfiguration
public class RocksTest {


	@Resource
	private DbBlockManager dbBlockManager;
	@Resource
	private DbBlockChecker dbBlockChecker;
	
	@Test
	public void saveblock(){
		//GenerateBlock generateBlock=new GenerateBlock();
		//Block block=new Block();
		//
		Block block = GenerateBlock.generateBlock(1);
		block.setBlockHash(SHA256.sha256(block.getBlockHeader().toString()));
		dbBlockManager.put(block.getBlockHash(), JSON.toJSONString(block));
		dbBlockManager.put("key_last_block",block.getBlockHash());
	}
	
	@Test
	public void readblock(){
		String hash=dbBlockManager.getLastBlockHash();
		Block block=dbBlockManager.getBlockByHash(hash);
		System.out.println("区块hash：" + hash);
		System.out.println("区块" + block);
	}
	
	@Test
	public void checkertest(){
		Block block=GenerateBlock.generateBlock(3);
		dbBlockChecker.checkMerkleRoot(block);
	}
	
}
