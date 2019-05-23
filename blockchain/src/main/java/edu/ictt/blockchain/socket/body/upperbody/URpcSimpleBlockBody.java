package edu.ictt.blockchain.socket.body.upperbody;

import edu.ictt.blockchain.socket.body.common.BaseBody;

public class URpcSimpleBlockBody extends BaseBody{
	
	/**
	 * UpperBlock的simpleBody包含uhash和bhash
	 */
	private String uhash;
	
	private String bhash;

	public URpcSimpleBlockBody() {
		super();
	}

	public URpcSimpleBlockBody(String uhash, String bhash) {
		super();
		this.uhash = uhash;
		this.bhash = bhash;
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

	@Override
	public String toString() {
		return "URpcSimpleBlockBody [uhash=" + uhash + ", bhash=" + bhash + "]";
	}
}
