package edu.ictt.blockchain.Block.record;

import java.util.Arrays;

/**
 * 记录中课程信息类
 */

public class CourseInfo{

	/**
	 * 所属学院
	 */
	private FacultyInfo facultyInfo;
    /**
     * 课程id
     */
    private int courseId;

    /**
     * 课程名
     */
    private String courseName;

    /**
     * 课程学分
     */
    private int courseCredit;

    /**
     * 开课学期
     */
    private String courseDate;
    
    /**
     * 授课教师
     */
    private TeacherInfo[] teacherInfo = new TeacherInfo[3];

    /**
     * 课程属性:必修学位，必修非学位等等
     */
    private String coursePro;

    public CourseInfo(){}
    
    public CourseInfo(FacultyInfo facultyInfo, int courseId, String courseName, int courseCredit, String courseDate,
			TeacherInfo[] teacherInfo, String coursePro) {
		super();
		this.facultyInfo = facultyInfo;
		this.courseId = courseId;
		this.courseName = courseName;
		this.courseCredit = courseCredit;
		this.courseDate = courseDate;
		this.teacherInfo = teacherInfo;
		this.coursePro = coursePro;
	}




	public FacultyInfo getFacultyInfo() {
		return facultyInfo;
	}

	public void setFacultyInfo(FacultyInfo facultyInfo) {
		this.facultyInfo = facultyInfo;
	}

	public int getCourseId() {

        return courseId;
    }

    public String getCourseName() {

        return courseName;
    }

    public int getCourseCredit() {
        return courseCredit;
    }

    public String getCourseDate() {

        return courseDate;
    }

    public String getCoursePro() {

        return coursePro;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCourseCredit(int courseCredit) {
        this.courseCredit = courseCredit;
    }

    public void setCourseDate(String courseDate) {
        this.courseDate = courseDate;
    }

    public void setCoursePro(String coursePro) {
        this.coursePro = coursePro;
    }
    
    public TeacherInfo[] getTeacherInfo() {
        return teacherInfo;
    }

    public void setTeacherInfo(TeacherInfo[] teacherInfo) {
        this.teacherInfo = teacherInfo;
    }
    
	@Override
	public String toString() {
		return "CourseInfo [facultyInfo=" + facultyInfo + ", courseId=" + courseId + ", courseName=" + courseName
				+ ", courseCredit=" + courseCredit + ", courseDate=" + courseDate + ", teacherInfo="
				+ Arrays.toString(teacherInfo) + ", coursePro=" + coursePro + "]";
	}
}
