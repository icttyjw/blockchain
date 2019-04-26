package edu.ictt.blockchain.Block.record;

public class MajorInfo {
	
	/**
	 * 专业号
	 */
	private int majorId;
	
	/**
	 * 专业名
	 */
	private String majorName;

	public int getMajorId() {
		return majorId;
	}

	public void setMajorId(int majorId) {
		this.majorId = majorId;
	}

	public String getMajorName() {
		return majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

	@Override
	public String toString() {
		return "MajorInfo [majorId=" + majorId + ", majorName=" + majorName + "]";
	}
	
}
