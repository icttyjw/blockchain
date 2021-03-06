package edu.ictt.blockchain.Block.record;

import java.io.Serializable;


/**
 * 记录的基类定义
 */

public class GradeRecord extends Record implements Serializable {


	private static final long serialVersionUID = 466699300984328279L;

	/**
	 * 学校信息
	 */
	private SchoolInfo schoolInfo;

	/**
	 * 学院信息
	 */
	private FacultyInfo facultyInfo;

	/**
	 * 成绩信息
	 */
	private GradeInfo gradeInfo;

	/**
	 * 教师签名
	 */
	private String teacherSign;

	/**
	 * 学院签名
	 */
	private String facultySign;

	public GradeRecord(){}

	public GradeRecord(SchoolInfo schoolInfo, FacultyInfo facultyInfo, GradeInfo gradeInfo, String teacherSign,
					 String facultySign, long recordTimeStamp){
		this.schoolInfo = schoolInfo;
		this.facultyInfo = facultyInfo;
		this.gradeInfo = gradeInfo;
		this.teacherSign = teacherSign;
		this.facultySign = facultySign;
		this.recordTimeStamp = recordTimeStamp;

	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public SchoolInfo getSchoolInfo() {
		return schoolInfo;
	}

	public void setSchoolInfo(SchoolInfo schoolInfo) {
		this.schoolInfo = schoolInfo;
	}

	public FacultyInfo getFacultyInfo() {
		return facultyInfo;
	}

	public void setFacultyInfo(FacultyInfo facultyInfo) {
		this.facultyInfo = facultyInfo;
	}

	public GradeInfo getGradeInfo() {
		return gradeInfo;
	}

	public void setGradeInfo(GradeInfo gradeInfo) {
		this.gradeInfo = gradeInfo;
	}

	public String getTeacherSign() {
		return teacherSign;
	}

	public void setTeacherSign(String teacherSign) {
		this.teacherSign = teacherSign;
	}

	public String getFacultySign() {
		return facultySign;
	}

	public void setFacultySign(String facultySign) {
		this.facultySign = facultySign;
	}

	@Override
	public String getSign() {
		return teacherSign+facultySign;
	}

	@Override
	public String toString() {
		return "GradeRecord [schoolInfo=" + schoolInfo + ", facultyInfo=" + facultyInfo + ", gradeInfo=" + gradeInfo
				+ ", teacherSign=" + teacherSign + ", facultySign=" + facultySign + "]";
	}

}
