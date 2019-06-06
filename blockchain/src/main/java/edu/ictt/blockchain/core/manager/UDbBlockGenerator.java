package edu.ictt.blockchain.core.manager;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.tio.utils.json.Json;

import edu.ictt.blockchain.Block.block.UpperBlock;
import edu.ictt.blockchain.Block.check.UCheckerManager;
import edu.ictt.blockchain.Block.db.DbStore;
import edu.ictt.blockchain.common.Constants;
import edu.ictt.blockchain.core.event.UAddBlockEvent;
import edu.ictt.blockchainmanager.sql.service.BlockInfoService;
import edu.ictt.blockchainmanager.sql.service.UBlockInfoService;

/**
 * uBlock的本地存储
 * @author zoe
 *
 */
@Service
public class UDbBlockGenerator {
	@Resource
	private DbStore dbStore;
	@Resource
	private UCheckerManager uCheckerManager;
	//@Resource
    //private UBlockInfoService uBlockInfoService;
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * add a new uBlock to DB
	 */
	@Order(1)
	@EventListener(UAddBlockEvent.class)
	public synchronized void addBlock(UAddBlockEvent uAddBlockEvent) {
		logger.info("[校间]：开始生成本地block");
		UpperBlock uBlock = (UpperBlock) uAddBlockEvent.getSource();
		String uhash = uBlock.getUpperBlockHash();
		String bhash = uBlock.getuBlockBody().getBlock().getBlockHash();
		
		//如果已经存在了，说明已经更新过该Block了
		//如果能够get到bhash，说明ublock封装的block已经存在；或者当前块已经存在
		if(dbStore.get(bhash)!=null || dbStore.get(uhash)!=null) {
			logger.info("[校间]：该区块封装的区块已经存在，或者该区块已经存在");
			return;
		}
		//
		if(uCheckerManager.check(uBlock).getCode()!=0) {
			logger.info("[校间]：该区块校验失败");
			return;
		}
		
		//save the mapping relationship
		dbStore.put(Constants.U_KEY_FIRST_BLOCK, uhash);
		//save hash of previous block with this block 
		dbStore.put(Constants.U_KEY_BLOCK_NEXT_PREFIX + uBlock.getuBlockHeader().getUhashPreviousBlock(), uhash);
		
		//save bhash of this block with its uhash
		dbStore.put(Constants.U_KEY_IN_BLOCK_PREFIX + bhash,uhash);
		
		//save its hash with its ublock
		dbStore.put(Constants.U_KEY_BLOCK_HASH_PREFIX + uhash, Json.toJson(uBlock));
		//set the last block
		dbStore.put(Constants.U_KEY_LAST_BLOCK, uhash);
		
		logger.info("[校间]：uBlock已存入本地RocksDB");
		
		//TODO 需要再映射到sql中
		
	} 
}
