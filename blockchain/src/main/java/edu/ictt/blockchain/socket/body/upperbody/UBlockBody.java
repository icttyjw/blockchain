package edu.ictt.blockchain.socket.body.upperbody;

import edu.ictt.blockchain.Block.block.Block;
import edu.ictt.blockchain.Block.block.UpperBlock;
import edu.ictt.blockchain.socket.body.common.BaseBody;

/**
 * 上层节点用到的基本body：BlockBody，此时对于上层节点来说，block就相当于校内的record。不过是不需要后续数量的
 * @author zoe
 *
 */
public class UBlockBody extends BaseBody{
	
	
	private Block block;
	
	private String hash;
	
	/**
	 *当前校级节点本次共识的区块数量？？？这个暂时不确定是否需要，因为共识的情况不确定，各学校一块一块交叉还是收某个学校收完了共识 
	 */
	

	public UBlockBody() {super();}
	
	public UBlockBody(Block block, String uhash) {
		super();
		this.block = block;
		this.hash = uhash;
	}

	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String uhash) {
		this.hash = uhash;
	}

	@Override
	public String toString() {
		return "BlockBody [block=" + block + ", hash=" + hash + "]";
	}
	
}
