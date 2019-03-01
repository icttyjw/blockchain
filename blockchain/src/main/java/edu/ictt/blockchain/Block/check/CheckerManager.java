package edu.ictt.blockchain.Block.check;

import org.springframework.stereotype.Component;

import edu.ictt.blockchain.Block.block.Block;
import edu.ictt.blockchain.socket.body.RpcCheckBlockBody;
@Component
public class CheckerManager {

	 public RpcCheckBlockBody check(Block block) {
		 return new RpcCheckBlockBody(0, "OK", block);
	 }
}
