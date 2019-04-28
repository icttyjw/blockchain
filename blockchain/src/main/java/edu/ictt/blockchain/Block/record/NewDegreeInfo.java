package edu.ictt.blockchain.Block.record;


/**
 * 只存id的学位info
 * @author zoe
 *
 */
public class NewDegreeInfo {
	
	/**
	 * 学校信息
	 */
	private SchoolInfo schoolInfo;
	
	/**
	 * 学院id
	 */
	private int faculthId;
	
	/**
	 * 专业id
	 */
	private int majorId;
	
	/**
	 * 学生id
	 */
	private long studentId;
	
	/**
	 * 学位编号
	 */
	private long degreeId;

	public NewDegreeInfo() {}
	
	public NewDegreeInfo(SchoolInfo schoolInfo, int faculthId, int majorId, long studentId, long degreeId) {
		super();
		this.schoolInfo = schoolInfo;
		this.faculthId = faculthId;
		this.majorId = majorId;
		this.studentId = studentId;
		this.degreeId = degreeId;
	}

	public SchoolInfo getSchoolInfo() {
		return schoolInfo;
	}

	public void setSchoolInfo(SchoolInfo schoolInfo) {
		this.schoolInfo = schoolInfo;
	}

	public int getFaculthId() {
		return faculthId;
	}

	public void setFaculthId(int faculthId) {
		this.faculthId = faculthId;
	}

	public int getMajorId() {
		return majorId;
	}

	public void setMajorId(int majorId) {
		this.majorId = majorId;
	}

	public long getStudentId() {
		return studentId;
	}

	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}

	public long getDegreeId() {
		return degreeId;
	}

	public void setDegreeId(long degreeId) {
		this.degreeId = degreeId;
	}

	@Override
	public String toString() {
		return "NewDegreeInfo [schoolInfo=" + schoolInfo + ", faculthId=" + faculthId + ", majorId=" + majorId
				+ ", studentId=" + studentId + ", degreeId=" + degreeId + "]";
	}
	
}
