package edu.ictt.blockchain.Block.block;

import com.alibaba.fastjson.annotation.JSONField;

import edu.ictt.blockchain.common.SHA256;


public class UpperBlock {
	
	/**
	 * upperBlock header
	 */
	@JSONField(ordinal=1)
	private UpperBlockHeader uBlockHeader;
	
	/**
	 * upperBlock body
	 */
	@JSONField(ordinal=2)
	private UpperBlockBody uBlockBody;

	@JSONField(ordinal=3)
	private String uBlockHash;
	
	public UpperBlock() {}
	
	public UpperBlock(UpperBlockHeader uBlockHeader, UpperBlockBody uBlockBody) {
		super();
		this.uBlockHeader = uBlockHeader;
		this.uBlockBody = uBlockBody;
		this.uBlockHash = calculateHash();
	}


	public UpperBlockHeader getuBlockHeader() {
		return uBlockHeader;
	}

	public void setuBlockHeader(UpperBlockHeader uBlockHeader) {
		this.uBlockHeader = uBlockHeader;
	}

	public UpperBlockBody getuBlockBody() {
		return uBlockBody;
	}

	public void setuBlockBody(UpperBlockBody uBlockBody) {
		this.uBlockBody = uBlockBody;
	}

	public String getUpperBlockHash() {
		return uBlockHash;
	}

	public void setUpperBlockHash(String uBlockHash) {
		this.uBlockHash = uBlockHash;
	}

	/**
	 * 根据该区块所有属性计算sha256
	 * @return
	 * sha256hex
	 */
	private String calculateHash() {
		return SHA256.sha256(
				uBlockHeader.toString() + uBlockBody.toString()
		);
	}

	@Override
	public String toString() {
		return "UpperBlock [uBlockHeader=" + uBlockHeader + ", uBlockBody=" + uBlockBody + ", uBlockHash="
				+ uBlockHash + "]";
	}
	

}
