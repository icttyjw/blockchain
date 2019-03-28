package test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.ictt.blockchain.Block.block.Block;
import edu.ictt.blockchain.Block.generatorUtil.GenerateBlock;
import edu.ictt.blockchain.core.manager.DbBlockManager;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RocksTest {

	@Resource
	private DbBlockManager dbBlockManager;
	
	
	@Test
	public void saveblock(){
		GenerateBlock generateBlock=new GenerateBlock();
		Block block=new Block();
		//
	}
	
	@Test
	public void readblock(){
		String hash=dbBlockManager.getLastBlockHash();
		Block block=dbBlockManager.getBlockByHash(hash);
	}
	
}
