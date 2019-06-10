package edu.ictt.blockchain.Block.db;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import edu.ictt.blockchain.ApplicationContextProvider;
import edu.ictt.blockchain.common.CommonUtil;
import edu.ictt.blockchain.common.Constants;
import edu.ictt.blockchain.core.manager.DbBlockManager;
import edu.ictt.blockchain.core.manager.UDbBlockManager;
import edu.ictt.blockchainmanager.groupmodel.NodeState;
import edu.ictt.blockchainmanager.sql.service.NodeService;

/**
 * 程序运行即自检本地数据库是否存有创世块，如果有则继续后续步骤，如果没有则创建
 * @author zoe
 *
 */
//@Component
public class CheckGenesis {
	//@Resource
	//DbStore dbStore;
	
	//@Resource
	//NodeService nodeService;
	NodeService nodeService = ApplicationContextProvider.getBean(NodeService.class);
	DbBlockManager dbBlockManager = ApplicationContextProvider.getBean(DbBlockManager.class);
	
	Logger logger=LoggerFactory.getLogger(getClass());
	
	DbStore dbStore = ApplicationContextProvider.getBean(DbStore.class);

	//获取当前节点主次：校内节点只检测GENESIS_BLOCK，校间节点还要检测U_GENESIS_BLOCK 
	public CheckGenesis() {
		checkGenesis();
		String localIp = CommonUtil.getLocalIp();
		NodeState localNode = nodeService.queryByIp(localIp);
		if(localNode.getMain().equals("1")) checkUGenesis();
		//else if(localNode.getMain().equals("0")) checkGenesis();
	}

	public void checkGenesis() {
		//先检测有没有
		if(dbStore.get(Constants.GENESIS_BLOCK)!=null) {
			logger.info("[启动检查]：本地创世块存在");
			//TODO 有的话校验用周期校验
			
			return;
		}else {
			CreateGenesisBlock cgBlock = new CreateGenesisBlock();
		}
	}

	public void checkUGenesis() {
		//先检测有没有
				if(dbStore.get("U_GENESIS_BLOCK")!=null) {
				//TODO 有的话校验用周期校验
					logger.info("[启动检查]：本地U创世块存在");
					return;
				}else {
					//用创世block创建创世uBlock
					UCreateGenesisBlock uBlock = new UCreateGenesisBlock(dbBlockManager.getBlockByHash(dbStore.get(Constants.GENESIS_BLOCK)));
				}
	}
	
}
