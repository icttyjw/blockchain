package edu.ictt.blockchain.Block.check;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import edu.ictt.blockchain.Block.block.Block;
import edu.ictt.blockchain.socket.body.RpcCheckBlockBody;

/**
 * 暂时没有加权限校验
 */
@Component
public class CheckerManager {

	@Resource
	private DbBlockChecker dbBlockChecker;
	 public RpcCheckBlockBody check(Block block) {

		 //DbBlockChecker dbBlockChecker = new DbBlockChecker();

		 if (dbBlockChecker.checkAll(block)) {
			 return new RpcCheckBlockBody(0, "OK", block);
		 } else {
			 if (dbBlockChecker.checkNum(block) == 0) {
				 if (dbBlockChecker.checkHash(block) == 0) {
				 	if(dbBlockChecker.checkTime(block) == 0){
						return new RpcCheckBlockBody(-10, "Illegal block", block);

					}
					 return new RpcCheckBlockBody(-3, "Block time errror", block);

				 }
				 return new RpcCheckBlockBody(-2, "Block hash errror", block);
			 }
			 return new RpcCheckBlockBody(-1, "Block number errror", block);
		 }


	 }
}
