package edu.ictt.blockchain.socket.upperpbft.msg;
/**
 * pbft算法传输prepare和commit消息的载体
 */
public class UVoteMsg {

	
	/**
     * 当前投票状态（Prepare，commit）
     */
    private byte voteType;
    
    /**
     * 区块的hash
     */
    private String uhash;
    
    /**
     * 该区块内封装的区块的hash
     */
    private String bhash;
    
    /**
     * 区块的number
     */
    private long number;
    
    /**
     * 是哪个节点传来的
     */
    private String appId;
    
    /**
     * 是否同意
     */
    private boolean agree;

	public byte getVoteType() {
		return voteType;
	}

	public void setVoteType(byte voteType) {
		this.voteType = voteType;
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

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public boolean isAgree() {
		return agree;
	}

	public void setAgree(boolean agree) {
		this.agree = agree;
	}

	@Override
	public String toString() {
		return "UVoteMsg [voteType=" + voteType + ", uhash=" + uhash + ", bhash=" + bhash + ", number=" + number
				+ ", appId=" + appId + ", agree=" + agree + "]";
	}
   
}
