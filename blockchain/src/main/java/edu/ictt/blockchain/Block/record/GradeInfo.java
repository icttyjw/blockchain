package edu.ictt.blockchain.Block.record;

import java.util.Arrays;

/**
 * @Author:zoe
 * @Description:
 * @Date:
 *
 */
public class GradeInfo {

    /**
     * 课程信息
     */
    private CourseInfo courseInfo;

    /**
     * 学生信息
     */
    private StudentInfo studentInfo;

    /**
     * 成绩信息
     */
    private double grade;

    public GradeInfo(){}

    public GradeInfo(CourseInfo courseInfo, StudentInfo studentInfo, double grade) {
		super();
		this.courseInfo = courseInfo;
		this.studentInfo = studentInfo;
		this.grade = grade;
	}

	public CourseInfo getCourseInfo() {
        return courseInfo;
    }

    public void setCourseInfo(CourseInfo courseInfo) {
        this.courseInfo = courseInfo;
    }    

    public StudentInfo getStudentInfo() {
        return studentInfo;
    }

    public void setStudentInfo(StudentInfo studentInfo) {
        this.studentInfo = studentInfo;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    @Override
	public String toString() {
		return "GradeInfo [courseInfo=" + courseInfo + ", studentInfo=" + studentInfo + ", grade=" + grade + "]";
	}

}
