package edu.ictt.blockchain.socket.body.upperbody;

import com.fasterxml.jackson.databind.deser.Deserializers.Base;

import edu.ictt.blockchain.socket.body.common.BaseBody;



public class URpcNextBlockBody extends BaseBody{
	
	/**
	 * 区块的uhash
	 */
	private String uhash;
	
	/**
	 * 区块的bhash
	 */
	private String bhash;
	
	/**
	 * 前一区块的uhash
	 */
	private String preUHash;
	
	/**
	 * 前一区块的bhash
	 */
	private String preBHash;

	public URpcNextBlockBody() {}
	
	public URpcNextBlockBody(String uhash, String bhash, String preUHash, String preBHash) {
		super();
		this.uhash = uhash;
		this.bhash = bhash;
		this.preUHash = preUHash;
		this.preBHash = preBHash;
	}

	public String getUhash() {
		return uhash;
	}

	public void setUhash(String uhash) {
		this.uhash = uhash;
	}

	public String getBhash() {
		return bhash;
	}

	public void setBhash(String bhash) {
		this.bhash = bhash;
	}

	public String getPreUHash() {
		return preUHash;
	}

	public void setPreUHash(String preUHash) {
		this.preUHash = preUHash;
	}

	public String getPreBHash() {
		return preBHash;
	}

	public void setPreBHash(String preBHash) {
		this.preBHash = preBHash;
	}

	@Override
	public String toString() {
		return "URpcNextBlockBody [uhash=" + uhash + ", bhash=" + bhash + ", preUHash=" + preUHash + ", preBHash="
				+ preBHash + "]";
	}
}
