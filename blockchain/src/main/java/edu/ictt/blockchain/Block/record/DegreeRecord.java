package edu.ictt.blockchain.Block.record;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * @Author:zoe
 * @Description:
 * @Date:
 */
public class DegreeRecord extends Record implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1700734513656530453L;

	/**
     * 学校信息
     */
    private SchoolInfo schoolInfo;
    
    /**
	 * 学院信息
	 */
	private FacultyInfo facultyInfo;
    
    /**
     * 学位信息
     */
    private DegreeInfo degreeInfo;

    /**
     * 学校签名
     */
    private String schoolSign;
   
    public DegreeRecord(){}

	public DegreeRecord(SchoolInfo schoolInfo, FacultyInfo facultyInfo, DegreeInfo degreeInfo, String schoolSign) {
		super();
		this.schoolInfo = schoolInfo;
		this.facultyInfo = facultyInfo;
		this.degreeInfo = degreeInfo;
		this.schoolSign = schoolSign;
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

	public DegreeInfo getDegreeInfo() {
		return degreeInfo;
	}

	public void setDegreeInfo(DegreeInfo degreeInfo) {
		this.degreeInfo = degreeInfo;
	}

	public String getSchoolSign() {
		return schoolSign;
	}

	public void setSchoolSign(String schoolSign) {
		this.schoolSign = schoolSign;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "DegreeRecord [schoolInfo=" + schoolInfo + ", facultyInfo=" + facultyInfo + ", degreeInfo=" + degreeInfo
				+ ", schoolSign=" + schoolSign + "]";
	}

}
