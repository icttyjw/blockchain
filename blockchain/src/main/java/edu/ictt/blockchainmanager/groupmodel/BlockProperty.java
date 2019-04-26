package edu.ictt.blockchainmanager.groupmodel;

import org.omg.CORBA.PRIVATE_MEMBER;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * 
 * @author zoe
 * tableview的数据填充类,需要适用property机制实现为后续绑定提供基础类
 *
 */
public class BlockProperty {
	
	private SimpleStringProperty schoolName = new SimpleStringProperty();
	
	private SimpleStringProperty facultyName = new SimpleStringProperty();
	
	private SimpleStringProperty courseName = new SimpleStringProperty();
	
	private SimpleStringProperty teacherName = new SimpleStringProperty();
	
	private SimpleStringProperty student = new SimpleStringProperty();
	
	private SimpleDoubleProperty grade = new SimpleDoubleProperty();
	
	private SimpleBooleanProperty teacherCheck = new SimpleBooleanProperty();
	
	private SimpleBooleanProperty facultyCheck = new SimpleBooleanProperty();

	
	public BlockProperty() {
		super();
	}
	

	public BlockProperty(String schoolName) {
		super();
		this.schoolName = new SimpleStringProperty(schoolName);
		this.facultyName = new SimpleStringProperty("计算机");
		this.courseName = new SimpleStringProperty("数学");
		this.teacherName = new SimpleStringProperty("李小红");
		this.student = new SimpleStringProperty("张三");
		this.grade = new SimpleDoubleProperty(Math.random());
		this.teacherCheck = new SimpleBooleanProperty(true);
		this.facultyCheck = new SimpleBooleanProperty(true);
	}



	public SimpleStringProperty schoolNameProperty() {
		return schoolName;
	}
	
	public SimpleStringProperty facultyNameProperty() {
		return facultyName;
	}
	
	public SimpleStringProperty courseNameProperty() {
		return courseName;
	}
	
	public SimpleStringProperty teacherNameProperty() {
		return teacherName;
	}
	
	public SimpleStringProperty studentProperty() {
		return student;
	}
	
	public SimpleDoubleProperty gradeProperty() {
		return grade;
	}
	
	public SimpleBooleanProperty teacherCheck() {
		return teacherCheck;
	}
	
	public SimpleBooleanProperty facultyCheck() {
		return facultyCheck;
	}
	
	public String getSchoolName() {
		return schoolName.get();
	}

	public void setSchoolName(String schoolName) {
		this.schoolName.set(schoolName);
	}

	public String getFacultyName() {
		return facultyName.get();
	}

	public void setFacultyName(String facultyName) {
		this.facultyName.set(facultyName);
	}

	public String getCourseName() {
		return courseName.get();
	}

	public void setCourseName(String courseName) {
		this.courseName.set(courseName);
	}

	public String getTeacherName() {
		return teacherName.get();
	}

	public void setTeacherName(String teacherName) {
		this.teacherName.set(teacherName);
	}

	public String getStudentName() {
		return student.get();
	}
	
	public void setStudent(String student) {
		this.student.set(student);
	}
	
	public double getGrade() {
		return grade.get();
	}

	public void setGrade(double grade) {
		this.grade.set(grade);
	}

	public boolean getTeacherCheck() {
		return teacherCheck.get();
	}

	public void setTeacherCheck(boolean teacherCheck) {
		this.teacherCheck.set(teacherCheck);
	}

	public boolean getFacultyCheck() {
		return facultyCheck.get();
	}

	public void setFacultyCheck(boolean facultyCheck) {
		this.facultyCheck.set(facultyCheck);
	}


	@Override
	public String toString() {
		return "BlockProperty [schoolName=" + schoolName + ", facultyName=" + facultyName + ", courseName=" + courseName
				+ ", teacherName=" + teacherName + ", student=" + student + ", grade=" + grade + ", teacherCheck="
				+ teacherCheck + ", facultyCheck=" + facultyCheck + "]";
	}
	
	
}
