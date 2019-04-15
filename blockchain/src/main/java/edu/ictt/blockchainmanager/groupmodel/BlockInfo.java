package edu.ictt.blockchainmanager.groupmodel;

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
	private long blockNumber;
	
	private String blockHash;
	
	private int schoolId;
	
	private String schoolName;
	
	private int facultyId;
	
	private String facultyName;
	
	private int courseId;
	
	private String courseName;

	public BlockInfo() {}
	
	public BlockInfo(long blockNumber,String blockHash, int schoolId, String schoolName, int facultyId, String facultyName, int courseId,
			String courseName) {
		super();
		this.blockNumber = blockNumber;
		this.blockHash = blockHash;
		this.schoolId = schoolId;
		this.schoolName = schoolName;
		this.facultyId = facultyId;
		this.facultyName = facultyName;
		this.courseId = courseId;
		this.courseName = courseName;
	}


	public long getBlockNumber() {
		return blockNumber;
	}

	public void setBlockNumber(long blockNumber) {
		this.blockNumber = blockNumber;
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
		return "BlockInfo [blockNumber=" + blockNumber + ", blockHash=" + blockHash + ", schoolId=" + schoolId
				+ ", schoolName=" + schoolName + ", facultyId=" + facultyId + ", facultyName=" + facultyName
				+ ", courseId=" + courseId + ", courseName=" + courseName + "]";
	}
	
	
}
