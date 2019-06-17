package edu.ictt.blockchain.socket.block.queue;

import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.apache.derby.impl.sql.execute.CountAggregator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import edu.ictt.blockchain.Block.block.Block;
import edu.ictt.blockchain.Block.block.UpperBlockBody;
import edu.ictt.blockchain.Block.check.DbBlockChecker;
import edu.ictt.blockchain.Block.check.UBlockChecker;
import edu.ictt.blockchain.core.event.BlockInformEvent;
import edu.ictt.blockchain.core.manager.DbBlockManager;
import edu.ictt.blockchain.core.manager.UDbBlockManager;
import edu.ictt.blockchain.core.requestbody.UpperBlockRequestBody;
import edu.ictt.blockchain.core.service.UpperBlockService;
import edu.ictt.blockchain.socket.body.upperbody.UBlockBody;

/**
 * 校级节点的区块队列：存储各学院发来的区块并封装这些块进行pbft
 * @author zoe
 *
 */
@Component
public class BlockQueue {

	@Resource
	UDbBlockManager uDbBlockManager;
	
	@Resource
	DbBlockChecker dbBlockChecker;
	
	@Resource
	UBlockChecker uBlockChecker; 
	
	@Resource
	DbBlockManager dbBlockManager;
	
	@Resource
	private UpperBlockService uBlockService;
	
	private Logger logger=LoggerFactory.getLogger(getClass());
	/**
	 * 存储各学院发来的区块
	 * @param bsBody
	 */
	private ConcurrentHashMap<String, Block> blockConcurrentHashMap = new ConcurrentHashMap<>();
	
	
	
	public void receive(UBlockBody bsBody) {
		// TODO Auto-generated method stub
		//查重
		String bhash = bsBody.getHash();
		if(blockConcurrentHashMap.get(bhash)!=null) {
			logger.info("[校内-校级]：当前区块已收到过");
			return;
		}
		/**
		 * 需要校验每个收到的区块的完整性
		 * @param bsBody
		 */
		Block block = bsBody.getBlock();
		logger.info("[校内-校级]:校级节点已收到校内上传的区块，区块信息是[" + block + "]");
		if(block == null) {
			logger.info("[校内-校级]:收到的区块为空，请重新接收");
			return;
		}
		/**
		 * 校验成功，则将区块放入队列，并在本地备份
		 */
		//TODO ublock中block的校验
		if(uBlockChecker.checkBlock(block)==0) {
			blockConcurrentHashMap.put(bhash, block);
			dbBlockManager.put(bhash, toString());
			logger.info("[校内-校级]:该区块校验成功,已放入本地区块队列和本地缓存");
			
			//调用uBlockService生成并发布区块
			UpperBlockBody uBlockBody = new UpperBlockBody(block);
			UpperBlockRequestBody uBlockRequestBody = new UpperBlockRequestBody(uBlockBody);
			uBlockService.addBlock(uBlockRequestBody);		
		}else {
			logger.info("[校内-校级]:收到的区块有误，请重新接收");
		}
		
		/**
		 * 是否需要删除区块
		 */
	}
	@EventListener(BlockInformEvent.class)
	public void add(BlockInformEvent blockInformEvent) {
		String hash=(String)blockInformEvent.getSource();
		Block block=dbBlockManager.getBlockByHash(hash);
		if(uBlockChecker.checkBlock(block)==0) {
			blockConcurrentHashMap.put(hash, block);
			dbBlockManager.put(hash, toString());
			logger.info("[校内-校级]:该区块校验成功,已放入本地区块队列和本地缓存");
			
			//调用uBlockService生成并发布区块
			UpperBlockBody uBlockBody = new UpperBlockBody(block);
			UpperBlockRequestBody uBlockRequestBody = new UpperBlockRequestBody(uBlockBody);
			uBlockService.addBlock(uBlockRequestBody);		
		}else {
			logger.info("[校内-校级]:收到的区块有误，请重新接收");
		}
	}

}
