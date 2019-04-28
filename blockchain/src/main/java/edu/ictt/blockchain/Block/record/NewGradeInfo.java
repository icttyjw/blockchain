package edu.ictt.blockchain.Block.record;

/**
 * 只存id的成绩info
 * @author zoe
 *
 */
public class NewGradeInfo {

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
	 * 课程id
	 */
	private int courseId;
	
	/**
	 * 教师id
	 */
	private int teacherId;
	
	/**
	 * 学生id
	 */
	private long studentId;
	
	/**
	 * 成绩信息
	 */
	private double grade;
	
	/**
	 * 学院公钥
	 */
	private String facultyPubkey;
	
	/**
	 * 教师公钥
	 */
	private String teacherPubkey;
	
	private String teacherSign;
	
	public NewGradeInfo() {}

	public NewGradeInfo(SchoolInfo schoolInfo, int faculthId, int majorId, int courseId, int teacherId, long studentId,
			double grade, String facultyPubkey, String teacherPubkey) {
		super();
		this.schoolInfo = schoolInfo;
		this.faculthId = faculthId;
		this.majorId = majorId;
		this.courseId = courseId;
		this.teacherId = teacherId;
		this.studentId = studentId;
		this.grade = grade;
		this.facultyPubkey = facultyPubkey;
		this.teacherPubkey = teacherPubkey;
	}

	public String getTeacherSign() {
		return teacherSign;
	}

	public void setTeacherSign(String teacherSign) {
		this.teacherSign = teacherSign;
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

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}

	public long getStudentId() {
		return studentId;
	}

	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}

	public double getGrade() {
		return grade;
	}

	public void setGrade(double grade) {
		this.grade = grade;
	}

	public String getFacultyPubkey() {
		return facultyPubkey;
	}

	public void setFacultyPubkey(String facultyPubkey) {
		this.facultyPubkey = facultyPubkey;
	}

	public String getTeacherPubkey() {
		return teacherPubkey;
	}

	public void setTeacherPubkey(String teacherPubkey) {
		this.teacherPubkey = teacherPubkey;
	}

	@Override
	public String toString() {
		return "NewGradeInfo [schoolInfo=" + schoolInfo + ", faculthId=" + faculthId + ", majorId=" + majorId
				+ ", courseId=" + courseId + ", teacherId=" + teacherId + ", studentId=" + studentId + ", grade="
				+ grade + ", facultyPubkey=" + facultyPubkey + ", teacherPubkey=" + teacherPubkey + ", teacherSign="
				+ teacherSign + "]";
	}
	
}
