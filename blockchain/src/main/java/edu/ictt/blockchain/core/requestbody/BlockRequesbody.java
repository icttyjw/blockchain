package edu.ictt.blockchain.core.requestbody;

import edu.ictt.blockchain.Block.block.BlockBody;

public class BlockRequesbody {

	private BlockBody blockBody;

	public BlockRequesbody() {
	}

	public BlockRequesbody(BlockBody blockBody) {
		this.blockBody = blockBody;
	}

	public BlockBody getBlockBody() {
		return blockBody;
	}

	public void setBlockBody(BlockBody blockBody) {
		this.blockBody = blockBody;
	}
	
}
