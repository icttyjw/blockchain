package edu.ictt.blockchain.Block.record;

import edu.ictt.blockchain.common.PairKey;

/**
 * 授课老师信息
 */

public class TeacherInfo{

    /**
     * 任课教师工号
     */
    private int teacherId;

    /**
     * 任课教师姓名
     */
    private String teacherName;

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

    public TeacherInfo(int teacherId, String teacherName, String teacherPro, String teacherPubKey){
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.teacherPro = teacherPro;
        this.teacherPubKey = teacherPubKey;
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


    @Override
	public String toString() {
		return "TeacherInfo [teacherId=" + teacherId + ", teacherName=" + teacherName + ", teacherPro=" + teacherPro
				+ ", teacherPubKey=" + teacherPubKey + "]";
	}
}
