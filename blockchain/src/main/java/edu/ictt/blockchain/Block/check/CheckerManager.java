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

		 if (dbBlockChecker.checkNum(block) != 0) {
			 return new RpcCheckBlockBody(-1, "Block number errror", block);
		 }else if (dbBlockChecker.checkHash(block) != 0) {
			 return new RpcCheckBlockBody(-3, "Block hash errror", block);
		 }else if(dbBlockChecker.checkTime(block) != 0){
			 return new RpcCheckBlockBody(-4, "Block time errror", block);
		 }else if(dbBlockChecker.checkNewRecordMR(block)!=0){
			 return new RpcCheckBlockBody(-10, "Illeagl Block ", block); 
		 }else {
			 return new RpcCheckBlockBody(0, "OK", block);
		 }	
		
	 }
	
	public int periodcheck(Block block){
		  if (dbBlockChecker.checkHash(block) != 0) {
			 return -3;
		 }else if(dbBlockChecker.checkTime(block) != 0){
			 return -4;
		 }else if(dbBlockChecker.checkNewRecordMR(block)!=0){
			 return -10; 
		 }else {
			 return 0;
		 }	
	}
}
