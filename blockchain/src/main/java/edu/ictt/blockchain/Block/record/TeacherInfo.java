package edu.ictt.blockchain.Block.record;

import edu.ictt.blockchain.common.PairKey;

/**
 * 授课老师信息
 */

public class TeacherInfo{

    /**
     * 所属学院
     */
	private FacultyInfo facultyInfo;
	/**
     * 任课教师工号
     */
    private int teacherId;

    /**
     * 任课教师姓名
     */
    private String teacherName;
    
    /**
     * 任课教师性别
     */
    private char teacherSex;

    /**
     * 任课教师属性
     */
    private String teacherPro;
    
    /**
     *任课教师公钥
     */

    private String teacherPubKey;
    
    /*
     * 任课教师密钥对
     */
    //private PairKey teacherPairKey;
    

    public TeacherInfo(){}

    public TeacherInfo(FacultyInfo facultyInfo, int teacherId, String teacherName, char teacherSex, String teacherPro,
			String teacherPubKey) {
		super();
		this.facultyInfo = facultyInfo;
		this.teacherId = teacherId;
		this.teacherName = teacherName;
		this.teacherSex = teacherSex;
		this.teacherPro = teacherPro;
		this.teacherPubKey = teacherPubKey;
	}


	public FacultyInfo getFacultyInfo() {
		return facultyInfo;
	}

	public void setFacultyInfo(FacultyInfo facultyInfo) {
		this.facultyInfo = facultyInfo;
	}

	public String getTeacherPro() {
		return teacherPro;
	}

	public void setTeacherPro(String teacherPro) {
		this.teacherPro = teacherPro;
	}

	public int getTeacherId() {

        return teacherId;
    }

    public String getTeacherName()
    {

        return teacherName;
    }

    public String getTeacherTitle() {

        return teacherPro;
    }

    /*public PairKey getTeacherPairKey() {
        return teacherPubKey;
    }*/
    
    public String getTeacherPubKey() {
		return teacherPubKey;
	}

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

	public void setTeacherPubKey(String teacherPubKey) {
		this.teacherPubKey = teacherPubKey;
	}

	public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public void setTeacherTitle(String teacherTitle) {
        this.teacherPro = teacherTitle;
    }
    

    /*public void setTeacherPairKey(PairKey teacherPairKey) {
        this.teacherPubKey = teacherPairKey;
    }*/


    public char getTeacherSex() {
		return teacherSex;
	}

	public void setTeacherSex(char teacherSex) {
		this.teacherSex = teacherSex;
	}

	@Override
	public String toString() {
		return "TeacherInfo [facultyInfo=" + facultyInfo + ", teacherId=" + teacherId + ", teacherName=" + teacherName
				+ ", teacherSex=" + teacherSex + ", teacherPro=" + teacherPro + ", teacherPubKey=" + teacherPubKey
				+ "]";
	}
}
