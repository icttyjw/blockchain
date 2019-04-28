package edu.ictt.blockchain.Block.record;

/**
 * @Author:zoe
 * @Description:
 * @Date:
 *
 */
public class StudentInfo {

	/**
     * 专业
     */
    private MajorInfo majorInfo;
	/**
     * 学生学号
     */
    private int studentId;

    /**
     * 学生姓名
     */
    private String studentName;
    
    /**
     * 性别
     */
    private char studentSex;
    
    /**
     * 民族
     */
    //private String nation;
    
    /**
     * 籍贯
     */
    //private String nativePalce;
    

    /**
     * 学制
     */
    private String schoolSystem;
    
    /**
     * 学历类别
     */
    private String degreeLevel;
    
    /**
     * 学习形式（普通全日制）
     */
    private String learnType;
    
    /**
     * 入学日期
     */
    private String joinDate;
    
    /**
     * 毕业日期
     */
    private String gradDate;

    public StudentInfo(){}

	/*public StudentInfo(int studentId, String studentName, char studentSex, String nation, String nativePalce,
			String schoolSystem, String degreeLevel, String learnType, String joinDate, String gradDate) {
		super();
		this.studentId = studentId;
		this.studentName = studentName;
		this.studentSex = studentSex;
		this.nation = nation;
		this.nativePalce = nativePalce;
		this.schoolSystem = schoolSystem;
		this.degreeLevel = degreeLevel;
		this.learnType = learnType;
		this.joinDate = joinDate;
		this.gradDate = gradDate;
	}*/
   
    
	public int getStudentId() {
		return studentId;
	}

	public MajorInfo getMajorInfo() {
		return majorInfo;
	}

	public void setMajorInfo(MajorInfo majorInfo) {
		this.majorInfo = majorInfo;
	}

	public StudentInfo(int studentId, String studentName, char studentSex, String schoolSystem, String degreeLevel,
			String learnType, String joinDate, String gradDate) {
		super();
		this.studentId = studentId;
		this.studentName = studentName;
		this.studentSex = studentSex;
		this.schoolSystem = schoolSystem;
		this.degreeLevel = degreeLevel;
		this.learnType = learnType;
		this.joinDate = joinDate;
		this.gradDate = gradDate;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public char getStudentSex() {
		return studentSex;
	}

	public void setStudentSex(char studentSex) {
		this.studentSex = studentSex;
	}

	/*public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getNativePalce() {
		return nativePalce;
	}

	public void setNativePalce(String nativePalce) {
		this.nativePalce = nativePalce;
	}*/

	public String getSchoolSystem() {
		return schoolSystem;
	}

	public void setSchoolSystem(String schoolSystem) {
		this.schoolSystem = schoolSystem;
	}

	public String getDegreeLevel() {
		return degreeLevel;
	}

	public void setDegreeLevel(String degreeLevel) {
		this.degreeLevel = degreeLevel;
	}

	public String getLearnType() {
		return learnType;
	}

	public void setLearnType(String learnType) {
		this.learnType = learnType;
	}

	public String getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}

	public String getGradDate() {
		return gradDate;
	}

	public void setGradDate(String gradDate) {
		this.gradDate = gradDate;
	}

	@Override
	public String toString() {
		return "StudentInfo [studentId=" + studentId + ", studentName=" + studentName + ", studentSex=" + studentSex
				+ ", majorInfo=" + majorInfo + ", schoolSystem=" + schoolSystem + ", degreeLevel=" + degreeLevel
				+ ", learnType=" + learnType + ", joinDate=" + joinDate + ", gradDate=" + gradDate + "]";
	}

	/*@Override
	public String toString() {
		return "StudentInfo [studentId=" + studentId + ", studentName=" + studentName + ", studentSex=" + studentSex
				+ ", nation=" + nation + ", nativePalce=" + nativePalce + ", schoolSystem=" + schoolSystem
				+ ", degreeLevel=" + degreeLevel + ", learnType=" + learnType + ", joinDate=" + joinDate + ", gradDate="
				+ gradDate + "]";
	};*/
      
}
