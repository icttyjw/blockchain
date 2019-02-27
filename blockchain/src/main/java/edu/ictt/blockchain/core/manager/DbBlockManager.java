package edu.ictt.blockchain.core.manager;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.hutool.core.util.StrUtil;
import edu.ictt.blockchain.Block.db.DbStore;
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
	    	logger.info("get lastblock");
	        String lastBlockHash = dbStore.get(Constants.KEY_LAST_BLOCK);
	        if (StrUtil.isEmpty(lastBlockHash)) {
	            return null;
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
	            return block.getblockHash();
	        }
	        return null;
	    }

	    /**
	     * 获取最后一个block的number
	     * @return number
	     */
	    public int getLastBlockNumber() {
	        Block block = getLastBlock();
	        if (block != null) {
	            return 1;//block.getBlockHeader().getNumber();
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
	        String nextHash = dbStore.get(Constants.KEY_BLOCK_NEXT_PREFIX + block.getblockHash());
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
}
