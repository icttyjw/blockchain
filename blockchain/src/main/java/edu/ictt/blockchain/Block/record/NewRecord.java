package edu.ictt.blockchain.Block.record;

public class NewRecord {

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
	
	public NewRecord(){}
	
	

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

	
	
}
