package edu.ictt.blockchain.Block.record;

import java.io.Serializable;

/**
 * @Author:zoe
 * @Description:
 * @Date:
 *
 */
public abstract class Record {

	protected String hash;
	protected long recordTimeStamp;
	protected String Sign;


	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public long getRecordTimeStamp() {
		return recordTimeStamp;
	}

	public void setRecordTimeStamp(long recordTimeStamp) {
		this.recordTimeStamp = recordTimeStamp;
	}

	public String getSign() {
		return Sign;
	}

	public void setSign(String sign) {
		Sign = sign;
	}
}
