package edu.ictt.blockchain.Block.record;

import edu.ictt.blockchain.common.PairKey;

/**
 * 授课老师信息
 */

public class TeacherInfo{

    //任课教师工号
    private int teacherId;

    //任课教师姓名
    private String teacherName;

    //任课教师职称
    private String teacherTitle;

    //任课教师密钥对
    private PairKey teacherPairKey;

    public TeacherInfo(){}

    public TeacherInfo(int teacherId, String teacherName, String teacherTitle, PairKey teacherPairKey){
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.teacherTitle = teacherTitle;
        this.teacherPairKey = teacherPairKey;
    }


    public int getTeacherId() {

        return teacherId;
    }

    public String getTeacherName()
    {

        return teacherName;
    }

    public String getTeacherTitle() {

        return teacherTitle;
    }

    public PairKey getteacherPairKey() {
        return teacherPairKey;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public void setTeacherTitle(String teacherTitle) {
        this.teacherTitle = teacherTitle;
    }

    public void setteacherPairKey(PairKey teacherPairKey) {
        this.teacherPairKey = teacherPairKey;
    }


    @Override
    public String toString() {
        return "TeacherInfo{" +
                "teacherId=" + teacherId +
                ", teacherName='" + teacherName + '\'' +
                ", teacherTitle='" + teacherTitle + '\'' +
                ", teacherPairKey=" + teacherPairKey +
                '}';
    }
}