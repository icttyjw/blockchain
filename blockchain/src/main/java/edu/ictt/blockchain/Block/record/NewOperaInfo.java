package edu.ictt.blockchain.Block.record;

/**
 * 只存id的操作info
 * @author zoe
 *
 */
public class NewOperaInfo {
	/**
	 * 学校信息
	 */
	private SchoolInfo schoolInfo;
	
	/**
	 * 操作人员id
	 */
	private int operatorId;
	
	/**
	 * 操作人员公钥
	 */
	private String operatorPubkey;
	
	/**
	 * 操作次数
	 */
	private int count;
	/**
	 * 操作是否成功
	 */
	private boolean result;
	/**
	 * 操作涉及到的改动
	 */
	private String changedInfo;
	
	public NewOperaInfo() {}

	public NewOperaInfo(SchoolInfo schoolInfo, int operatorId, String operatorPubkey, int count, boolean result,
			String changedInfo) {
		super();
		this.schoolInfo = schoolInfo;
		this.operatorId = operatorId;
		this.operatorPubkey = operatorPubkey;
		this.count = count;
		this.result = result;
		this.changedInfo = changedInfo;
	}

	public SchoolInfo getSchoolInfo() {
		return schoolInfo;
	}

	public void setSchoolInfo(SchoolInfo schoolInfo) {
		this.schoolInfo = schoolInfo;
	}

	public int getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(int operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorPubkey() {
		return operatorPubkey;
	}

	public void setOperatorPubkey(String operatorPubkey) {
		this.operatorPubkey = operatorPubkey;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getChangedInfo() {
		return changedInfo;
	}

	public void setChangedInfo(String changedInfo) {
		this.changedInfo = changedInfo;
	}

	@Override
	public String toString() {
		return "NewOperaInfo [schoolInfo=" + schoolInfo + ", operatorId=" + operatorId + ", operatorPubkey="
				+ operatorPubkey + ", count=" + count + ", result=" + result + ", changedInfo=" + changedInfo + "]";
	}
	
	
}
