package edu.ictt.blockchain.socket.body.upperbody;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

public class UBlockHash {
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
	
	/**
	 * 节点id
	 */
	private String appId;

	public UBlockHash() {
		super();
	}

	public UBlockHash(String uhash, String bhash, String preUHash, String preBHash, String appId) {
		super();
		this.uhash = uhash;
		this.bhash = bhash;
		this.preUHash = preUHash;
		this.preBHash = preBHash;
		this.appId = appId;
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

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	@Override
	public String toString() {
		return "UBlockHash [uhash=" + uhash + ", bhash=" + bhash + ", preUHash=" + preUHash + ", preBHash=" + preBHash
				+ ", appId=" + appId + "]";
	}
}
