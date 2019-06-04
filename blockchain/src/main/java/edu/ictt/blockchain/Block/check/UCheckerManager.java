package edu.ictt.blockchain.Block.check;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import edu.ictt.blockchain.Block.block.UpperBlock;
import edu.ictt.blockchain.socket.body.lowerbody.RpcCheckBlockBody;
import edu.ictt.blockchain.socket.body.upperbody.URpcBlockBody;
import edu.ictt.blockchain.socket.body.upperbody.URpcCheckBlockBody;

@Component
public class UCheckerManager {

	@Resource
	UBlockChecker uBlockChecker;
	
	public URpcCheckBlockBody check(UpperBlock uBlock) {
		 if (uBlockChecker.checkNum(uBlock) != 0) {
			 return new URpcCheckBlockBody(-1, "uBlock number error", uBlock);
		 }else if (uBlockChecker.checkPreHash(uBlock) != 0) {
			 return new URpcCheckBlockBody(-3, "uBlock hash error", uBlock);
		 }else if(uBlockChecker.checkTime(uBlock) != 0){
			 return new URpcCheckBlockBody(-4, "uBlock time error", uBlock);	 
		 }//else if(uBlockChecker.checkBlockHash(uBlock) != 0){
			 //return new URpcCheckBlockBody(-5, "uBlock bhash error", uBlock);
		 //}else if(uBlockChecker.checkBlockSign(uBlock)!=0){
			// return new URpcCheckBlockBody(-6, "uBlock Sign error ", uBlock); 
		 //}
		 else {
			 return new URpcCheckBlockBody(0, "OK", uBlock);
		 }	
		
	}
}
