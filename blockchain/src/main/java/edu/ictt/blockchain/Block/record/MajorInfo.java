package edu.ictt.blockchain.Block.record;

public class MajorInfo {
	
	/**
	 * 所属学院
	 */
	private FacultyInfo facultyInfo;
	/**
	 * 专业号
	 */
	private int majorId;
	
	/**
	 * 专业名
	 */
	private String majorName;
	
	/**
	 * 专业的特殊属性（双一流重点学科等）
	 * @return
	 */
	private String majorString;
	
	/**
	 * 不确定是否需要公钥，此处的公钥可由各专业负责人持有
	 * @return
	 */
	
	
	public int getMajorId() {
		return majorId;
	}

	public FacultyInfo getFacultyInfo() {
		return facultyInfo;
	}

	public void setFacultyInfo(FacultyInfo facultyInfo) {
		this.facultyInfo = facultyInfo;
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
	
	public String getMajorString() {
		return majorString;
	}

	public void setMajorString(String majorString) {
		this.majorString = majorString;
	}

	@Override
	public String toString() {
		return "MajorInfo [facultyInfo=" + facultyInfo + ", majorId=" + majorId + ", majorName=" + majorName
				+ ", majorString=" + majorString + "]";
	}
	
}
