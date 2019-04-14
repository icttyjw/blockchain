package edu.ictt.blockchain.Block.db.query;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="block")
public class BlockInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2835146221652592168L;

	@Id
	private String blockHash;
	
	private int schoolId;
	
	private String schoolName;
	
	private int facultyId;
	
	private String facultyName;
	
	//成绩记录中，该字段表示课程id；学位记录中，该字段表示证书的编号
	private int courseId;
	//成绩记录中，该字段表示课程名称；学位记录中，该字段表示证书的名称。
	private String courseName;

	public BlockInfo() {}
	
	public BlockInfo(String blockHash, int schoolId, String schoolName, int facultyId, String facultyName, int courseId,
			String courseName) {
		super();
		this.blockHash = blockHash;
		this.schoolId = schoolId;
		this.schoolName = schoolName;
		this.facultyId = facultyId;
		this.facultyName = facultyName;
		this.courseId = courseId;
		this.courseName = courseName;
	}
	
	public String getBlockHash() {
		return blockHash;
	}

	public void setBlockHash(String blockHash) {
		this.blockHash = blockHash;
	}

	public int getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public int getFacultyId() {
		return facultyId;
	}

	public void setFacultyId(int facultyId) {
		this.facultyId = facultyId;
	}

	public String getFacultyName() {
		return facultyName;
	}

	public void setFacultyName(String facultyName) {
		this.facultyName = facultyName;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	@Override
	public String toString() {
		return "BlockInfo [blockHash=" + blockHash + ", schoolId=" + schoolId + ", schoolName=" + schoolName
				+ ", facultyId=" + facultyId + ", facultyName=" + facultyName + ", courseId=" + courseId
				+ ", courseName=" + courseName + "]";
	}
	
	
}
