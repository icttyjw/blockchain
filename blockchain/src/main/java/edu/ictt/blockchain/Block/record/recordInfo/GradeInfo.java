package edu.ictt.blockchain.Block.record.recordInfo;

import java.util.Map;

/**
 * @Author:zoe
 * @Description:
 * @Date:
 *
 */
public class GradeInfo {

    //课程信息，对学位来说
    private  CourseInfo courseInfo;

    //任课教师信息,每门课程最多三个老师
    private TeacherInfo[] teacherInfo = new TeacherInfo[3];

    //学生信息
    private StudentInfo studentInfo;

    /**
     * 成绩信息
     */
    private double grade;

    public GradeInfo(){}

    public GradeInfo(CourseInfo courseInfo, TeacherInfo[] teacherInfo, StudentInfo studentInfo, double grade){
        this.courseInfo = courseInfo;
        this.teacherInfo = teacherInfo;
        this.studentInfo = studentInfo;
        this.grade = grade;
    }

    public CourseInfo getCourseInfo() {
        return courseInfo;
    }

    public void setCourseInfo(CourseInfo courseInfo) {
        this.courseInfo = courseInfo;
    }

    public TeacherInfo[] getTeacherInfo() {
        return teacherInfo;
    }

    public void setTeacherInfo(TeacherInfo[] teacherInfo) {
        this.teacherInfo = teacherInfo;
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
    public String toString(){
        return "CourseGradeInfo:{" +
                "，courseInfo:" + courseInfo +
                ", teacherInfo:" + teacherInfo +
                ", studentInfo:" + studentInfo +
                ", grader:" + grade +
                "}";

    }

}
