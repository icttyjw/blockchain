package edu.ictt.blockchain.Block.block;

import java.util.ArrayList;
import java.util.List;

import edu.ictt.blockchain.Block.block.Block;

public class UpperBlockBody {
	/**
	 * fill with block
	 */
	private Block block;
	
	public UpperBlockBody() {}

	public UpperBlockBody(Block block) {
		super();
		this.block = block;
	}

	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

	@Override
	public String toString() {
		return "UpperBlockBody [block=" + block + "]";
	}

}
