package edu.ictt.blockchain.Block.block;

import cn.hutool.crypto.digest.DigestUtil;

import java.io.Serializable;

public class Block implements Serializable{

	private static final long serialVersionUID = 6457204987118872125L;

	//区块头
	private BlockHeader blockHeader;

	//区块体
	private  BlockBody blockBody;

	//区块哈希
	private String blockHash;

	public Block(){}

	public Block(BlockHeader blockHeader, BlockBody blockBody){
		this.blockHeader = blockHeader;
		this.blockBody = blockBody;
	}

	public BlockHeader getBlockHeader() {
		return blockHeader;
	}

	public void setBlockHeader(BlockHeader blockHeader) {
		this.blockHeader = blockHeader;
	}

	public BlockBody getBlockBody() {
		return blockBody;
	}

	public void setBlockBody(BlockBody blockBody) {
		this.blockBody = blockBody;
	}

	public String getBlockHash() {

		return blockHash;
	}

	public void setblockHash(String blockHash) {

		this.blockHash = blockHash;
	}

	/**
	 * 根据该区块所有属性计算sha256
	 * @return
	 * sha256hex
	 */
	private String calculateHash() {
		return DigestUtil.sha256Hex(
				blockHeader.toString() + blockBody.toString()
		);
	}

}
