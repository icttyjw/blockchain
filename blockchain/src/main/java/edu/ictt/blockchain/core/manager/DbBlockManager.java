package edu.ictt.blockchain.core.manager;

import java.util.List;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import edu.ictt.blockchain.Block.generatorUtil.GenerateBlock;
import edu.ictt.blockchain.common.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.hutool.core.util.StrUtil;
import edu.ictt.blockchain.Block.db.DbStore;
import edu.ictt.blockchain.Block.record.Record;
import edu.ictt.blockchain.Block.block.Block;
import edu.ictt.blockchain.common.Constants;
import edu.ictt.blockchain.common.FastJsonUtil;

@Service
public class DbBlockManager {

	 @Resource
	 private DbStore dbStore;
	 private Logger logger=LoggerFactory.getLogger(getClass());

	    /**
	     * 查找第一个区块
	     *
	     * @return 第一个Block
	     */
	    public Block getFirstBlock() {
	        String firstBlockHash = dbStore.get(Constants.KEY_FIRST_BLOCK);
	        if (StrUtil.isEmpty(firstBlockHash)) {
	            return null;
	        }
	        return getBlockByHash(firstBlockHash);
	    }

	    /**
	     * 获取最后一个区块信息
	     *
	     * @return 最后一个区块
	     */
	    public Block getLastBlock() {
	    	logger.info("[共识准备]：get lastblock");
	        String lastBlockHash = dbStore.get(Constants.KEY_LAST_BLOCK);
	        if (StrUtil.isEmpty(lastBlockHash)) {
				Block block = GenerateBlock.generateBlock(1);
				block.setBlockHash(SHA256.sha256(block.getBlockHeader().toString()));
				dbStore.put(block.getBlockHash(), JSON.toJSONString(block));
				dbStore.put("key_last_block",block.getBlockHash());
				logger.info("hash of the last block:" + block.getBlockHash());
	            return block;
	        }else{
	        	logger.info("[共识准备]：lastblock读取成功");
	        	logger.info("[共识准备]：lastblcok is: " + dbStore.get(lastBlockHash));
			}
	        return getBlockByHash(lastBlockHash);
	    }

	    /**
	     * 获取最后一个区块的hash
	     *
	     * @return hash
	     */
	    public String getLastBlockHash() {
	        Block block = getLastBlock();
	        if (block != null) {
	            return block.getBlockHash();
	        }
	        return null;
	    }

	    /**
	     * 获取最后一个block的number
	     * @return number
	     */
	    public long getLastBlockNumber() {
	        Block block = getLastBlock();
	        if (block != null) {
	            return block.getBlockHeader().getBlockNumber();
	        }
	        return 0;
	    }

	    /**
	     * 获取某一个block的下一个Block
	     *
	     * @param block
	     *         block
	     * @return block
	     */
	    public Block getNextBlock(Block block) {
	        if (block == null) {
	            return getFirstBlock();
	        }
	        String nextHash = dbStore.get(Constants.KEY_BLOCK_NEXT_PREFIX + block.getBlockHash());
	        if (nextHash == null) {
	            return null;
	        }
	        return getBlockByHash(nextHash);
	    }

	    public Block getNextBlockByHash(String hash) {
	        if (hash == null) {
	            return getFirstBlock();
	        }
	        String nextHash = dbStore.get(Constants.KEY_BLOCK_NEXT_PREFIX + hash);
	        if (nextHash == null) {
	            return null;
	        }
	        return getBlockByHash(nextHash);
	    }

	    public Block getBlockByHash(String hash) {
	        String blockJson = dbStore.get(hash);
	        return FastJsonUtil.toBean(blockJson, Block.class);
	    }
	    
	    public void put(String key,String value){
	    	dbStore.put(key, value);
	    }

	    public List<Record> getRecordList(String hash){
	    	String listjaso=dbStore.get(hash);
	    	return FastJsonUtil.toList(listjaso, Record.class);
	    }
	 
	//getter和setter供测试方便，可删除
	public DbStore getDbStore() {
		return dbStore;
	}

	public void setDbStore(DbStore dbStore) {
		this.dbStore = dbStore;
	}
}
