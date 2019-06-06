package edu.ictt.blockchain.core.manager;

import static org.assertj.core.api.Assertions.offset;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import cn.hutool.core.util.StrUtil;
import edu.ictt.blockchain.Block.block.Block;
import edu.ictt.blockchain.Block.block.UpperBlock;
import edu.ictt.blockchain.Block.db.DbStore;
import edu.ictt.blockchain.common.Constants;
import edu.ictt.blockchain.common.FastJsonUtil;

/**
 * 查找第一个最后一个区块等
 *
 */
@Service
public class UDbBlockManager {

	private Logger logger=LoggerFactory.getLogger(getClass());
	
	@Resource
	DbStore dbStore;
	
	/**
	 * 获取本地第一个uBlock
	 */
	public UpperBlock getFirstBlock() {
		 String firstBlockHash = dbStore.get(Constants.U_KEY_FIRST_BLOCK);
	     if (StrUtil.isEmpty(firstBlockHash)) {
	            return null;
	        }
	     return getBlockByHash(firstBlockHash);
		
	}
	/**
	 * 获取本地最后一个uBlock
	 * @return
	 */
	public UpperBlock getLastBlock() {
		String lastBlockHash = dbStore.get(Constants.U_KEY_LAST_BLOCK);
        if (StrUtil.isEmpty(lastBlockHash)) {
            return null;
        }
        return getBlockByHash(lastBlockHash);
	}
	
	/**
	 * 获取最后一个uBlock的hash
	 * @return
	 */
	public String getLastBlockHash() {
		UpperBlock uBlock = getLastBlock();
		if(uBlock!=null) {
			return uBlock.getUpperBlockHash();
		}
		return null;
	}
	
	/**
	 * 获取最后一个uBlock的number
	 */
	public long getLastBlockNumber() {
		UpperBlock uBlock = getLastBlock();
		if(uBlock!=null) {
			return uBlock.getuBlockHeader().getUblockNumber();
		}
		return 0;
	}
	
	/**
	 * 获取某一uBlock的下一uBlock
	 */
	public UpperBlock getNextBlock(UpperBlock uBlock) {
		if(uBlock == null) {
			return getFirstBlock();
		}
		String nextHash = dbStore.get(Constants.U_KEY_BLOCK_NEXT_PREFIX + uBlock.getUpperBlockHash());
		if(nextHash == null) {return null;}
		
		return getBlockByHash(nextHash);
		
	}
	/**
	 * 根据uhash找区块
	 * @param uhash
	 * @return
	 */
	public UpperBlock getBlockByHash(String uhash) {
		// TODO Auto-generated method stub
		String blockJson = dbStore.get(Constants.U_KEY_BLOCK_HASH_PREFIX+uhash);
        return FastJsonUtil.toBean(blockJson, UpperBlock.class);
	}
	
	/**
	 * 根据bhash找uhash及其代表的uBlock
	 */
	public UpperBlock getUBlockByBhash(String bhash) {
		String uhash = dbStore.get(Constants.U_KEY_IN_BLOCK_PREFIX + bhash);
		return getBlockByHash(uhash);
	}

	/**
	 * 根据当前hash找到其下一区块
	 * @param uhash
	 * @return
	 */
	public UpperBlock getNextBlockByhash(String uhash) {
		if (uhash == null) {
            return getFirstBlock();
        }
		String nextHash = dbStore.get(Constants.U_KEY_BLOCK_NEXT_PREFIX + uhash);
		if(nextHash == null) {return null;}
		
		return getBlockByHash(nextHash);
	}
	
	public void put(String key,String value){
    	dbStore.put(key, value);
    }
}
