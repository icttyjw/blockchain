package edu.ictt.blockchain.socket.pbft.msg;

import edu.ictt.blockchain.Block.block.Block;

public class VotePreMsg extends VoteMsg{

	private Block block;

	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}
	
}
