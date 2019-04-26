package edu.ictt.blockchain.Block.record;
/*
 * 包含所有信息
 */
public class NewRecord {
	
	/*
	 * 记录类型
	 */
	private int record_type;
	
	/**
	 * 学校信息
	 */
	private SchoolInfo schoolInfo;

	/**
	 * 学院信息
	 */
	private FacultyInfo facultyInfo;

	
	/**
	 * 教师签名
	 */
	private String teacherSign;

	/**
	 * 学院签名
	 */
	private String facultySign;
	/**
	 * 成绩信息
	 */
	private GradeInfo gradeInfo;
	/*
	 * 学历信息
	 */
	private DegreeInfo degreeInfo;
	/*
	 * 说明性内容，包括管理员操作等
	 */
	private String comment;
	
	public NewRecord(){}
	/*
	 * 成绩记录构建
	 */
	public NewRecord(int type,SchoolInfo schoolInfo,FacultyInfo facultyInfo,GradeInfo gradeInfo,
				String teacherSign,String facultySign){
		this.record_type=type;
		this.schoolInfo=schoolInfo;
		this.facultyInfo=facultyInfo;
		this.gradeInfo=gradeInfo;
		this.teacherSign=teacherSign;
		this.facultySign=facultySign;
		
	}
	

	public int getRecord_type() {
		return record_type;
	}



	public void setRecord_type(int record_type) {
		this.record_type = record_type;
	}



	public SchoolInfo getSchoolInfo() {
		return schoolInfo;
	}

	public FacultyInfo getFacultyInfo() {
		return facultyInfo;
	}

	public GradeInfo getGradeInfo() {
		return gradeInfo;
	}

	public String getTeacherSign() {
		return teacherSign;
	}

	public String getFacultySign() {
		return facultySign;
	}

	public void setSchoolInfo(SchoolInfo schoolInfo) {
		this.schoolInfo = schoolInfo;
	}

	public void setFacultyInfo(FacultyInfo facultyInfo) {
		this.facultyInfo = facultyInfo;
	}

	public void setGradeInfo(GradeInfo gradeInfo) {
		this.gradeInfo = gradeInfo;
	}

	public void setTeacherSign(String teacherSign) {
		this.teacherSign = teacherSign;
	}

	public void setFacultySign(String facultySign) {
		this.facultySign = facultySign;
	}



	public DegreeInfo getDegreeInfo() {
		return degreeInfo;
	}



	public void setDegreeInfo(DegreeInfo degreeInfo) {
		this.degreeInfo = degreeInfo;
	}



	public String getComment() {
		return comment;
	}



	public void setComment(String comment) {
		this.comment = comment;
	}

	
	
}
