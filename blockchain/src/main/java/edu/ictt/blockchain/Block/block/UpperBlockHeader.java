package edu.ictt.blockchain.Block.block;

public class UpperBlockHeader {

	/**
     *   前一区块哈希
     */
    private String uhashPreviousBlock;
    
    /**
     * 该UpperBlock中的Block的hash：连接uBlockHeader和uBLockBody
     */
    private String bhash;

    /**Nounce值
     *提高区块生成门槛
     */
    private long unonce;

    /**难度值
     *
     */
    private long udifficultGoal;

    /**区块时间戳
     *
     */
    private long ublockTimeStamp;

    /**块号
     *
     */
    private long ublockNumber;

    /**
     * 该区块签名的公钥
     */
    private String upublicKey;

    /**区块头摘要签名：可用来校验该区块是否被篡改
     */
    private String ublockHeaderSign;
    
    public UpperBlockHeader() {}

	public UpperBlockHeader(String uhashPreviousBlock, String bHash, long unonce, long udifficultGoal,
			long ublockTimeStamp, long ublockNumber, String upublicKey, String ublockHeaderSign) {
		super();
		this.uhashPreviousBlock = uhashPreviousBlock;
		this.bhash = bHash;
		this.unonce = unonce;
		this.udifficultGoal = udifficultGoal;
		this.ublockTimeStamp = ublockTimeStamp;
		this.ublockNumber = ublockNumber;
		this.upublicKey = upublicKey;
		this.ublockHeaderSign = ublockHeaderSign;
	}

	public String getUhashPreviousBlock() {
		return uhashPreviousBlock;
	}

	public void setUhashPreviousBlock(String uhashPreviousBlock) {
		this.uhashPreviousBlock = uhashPreviousBlock;
	}

	public String getBhash() {
		return bhash;
	}

	public void setBhash(String bHash) {
		this.bhash = bHash;
	}

	public long getUnonce() {
		return unonce;
	}

	public void setUnonce(long unonce) {
		this.unonce = unonce;
	}

	public long getUdifficultGoal() {
		return udifficultGoal;
	}

	public void setUdifficultGoal(long udifficultGoal) {
		this.udifficultGoal = udifficultGoal;
	}

	public long getUblockTimeStamp() {
		return ublockTimeStamp;
	}

	public void setUblockTimeStamp(long ublockTimeStamp) {
		this.ublockTimeStamp = ublockTimeStamp;
	}

	public long getUblockNumber() {
		return ublockNumber;
	}

	public void setUblockNumber(long ublockNumber) {
		this.ublockNumber = ublockNumber;
	}

	public String getUpublicKey() {
		return upublicKey;
	}

	public void setUpublicKey(String upublicKey) {
		this.upublicKey = upublicKey;
	}

	public String getUblockHeaderSign() {
		return ublockHeaderSign;
	}

	public void setUblockHeaderSign(String ublockHeaderSign) {
		this.ublockHeaderSign = ublockHeaderSign;
	}

	@Override
	public String toString() {
		return "UpperBlockHeader [uhashPreviousBlock=" + uhashPreviousBlock + ", hashOfBlock=" + bhash
				+ ", unonce=" + unonce + ", udifficultGoal=" + udifficultGoal + ", ublockTimeStamp=" + ublockTimeStamp
				+ ", ublockNumber=" + ublockNumber + ", upublicKey=" + upublicKey + ", ublockHeaderSign="
				+ ublockHeaderSign + "]";
	}
   
}
