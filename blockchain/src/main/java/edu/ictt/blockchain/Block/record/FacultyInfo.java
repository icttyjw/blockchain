package edu.ictt.blockchain.Block.record;

import edu.ictt.blockchain.common.PairKey;

/**
 * @Author:zoe
 * @Description:
 * @Date:
 *
 */

public class FacultyInfo {

    //学院id
    private  int facultyId;

    //学院名称
    private  String facultyName;

    //学院特殊属性
    private String facultyPro;

    //学院密钥对
    private PairKey faculthPairKey;

    public FacultyInfo(){}

    public FacultyInfo(int facultyId, String facultyName, String facultyPro,PairKey faculthPairKey){
        this.facultyId = facultyId;
        this.facultyName = facultyName;
        this.facultyPro = facultyPro;
        this.faculthPairKey = faculthPairKey;
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

    public String getFacultyPro() {

        return facultyPro;
    }

    public void setFacultyPro(String facultyPro) {

        this.facultyPro = facultyPro;
    }

    public PairKey getFaculthPairKey() {
        return faculthPairKey;
    }

    public void setFaculthPairKey(PairKey faculthPairKey) {
        this.faculthPairKey = faculthPairKey;
    }

    @Override
    public String toString() {
        return "FacultyInfo{" +
                "facultyId=" + facultyId +
                ", facultyName='" + facultyName + '\'' +
                ", facultyPro='" + facultyPro + '\'' +
                ", faculthPairKey=" + faculthPairKey +
                '}';
    }
}
