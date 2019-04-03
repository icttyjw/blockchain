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
     * 学位属性
     */
    private String degree;

    /**
     * 学生信息
     */
    private StudentInfo studentInfo;

    /**
     * 开题情况
     */
    private String gradDesignStartState;

    /**
     * 中期情况
     */
    private String gradDesignMidState;

    /**
     * 最终论文答辩情况
     */
    private String gradDesignFinState;

    /**
     * 学位证书编号
     */
    private long degreeId;

    /**
     * 学校签名
     */
    private String schoolSign;

    public DegreeRecord(){}

    public DegreeRecord(String degree, String gradDesignStartState, String gradDesignMidState,
                        String gradDesignFinState, long degreeId){
        this.degree = degree;
        this.degreeId = degreeId;
        this.gradDesignStartState = gradDesignStartState;
        this.gradDesignMidState = gradDesignMidState;
        this.gradDesignFinState = gradDesignFinState;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public StudentInfo getStudentInfo() {
        return studentInfo;
    }

    public void setStudentInfo(StudentInfo studentInfo) {
        this.studentInfo = studentInfo;
    }

    public String getGradDesignStartState() {
        return gradDesignStartState;
    }

    public void setGradDesignStartState(String gradDesignStartState) { this.gradDesignStartState = gradDesignStartState; }

    public String getGradDesignMidState() {
        return gradDesignMidState;
    }

    public void setGradDesignMidState(String gradDesignMidState) {
        this.gradDesignMidState = gradDesignMidState;
    }

    public String getGradDesignFinState() {
        return gradDesignFinState;
    }

    public void setGradDesignFinState(String gradDesignFinState) {
        this.gradDesignFinState = gradDesignFinState;
    }

    public long getDegreeId() {
        return degreeId;
    }

    public void setDegreeId(long degreeId) {
        this.degreeId = degreeId;
    }

    public String getSchoolSign() {
        return schoolSign;
    }

    public void setSchoolSign(String schoolSign) {
        this.schoolSign = schoolSign;
    }

    public SchoolInfo getSchoolInfo() {
        return schoolInfo;
    }

    public void setSchoolInfo(SchoolInfo schoolInfo) {
        this.schoolInfo = schoolInfo;
    }

    @Override
	public String toString() {
		return "DegreeRecord [schoolInfo=" + schoolInfo + ", degree=" + degree + ", studentInfo=" + studentInfo
				+ ", gradDesignStartState=" + gradDesignStartState + ", gradDesignMidState=" + gradDesignMidState
				+ ", gradDesignFinState=" + gradDesignFinState + ", degreeId=" + degreeId + ", schoolSign=" + schoolSign
				+ "]";
	}
}
