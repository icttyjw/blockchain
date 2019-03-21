package edu.ictt.blockchain.Block.record;

import edu.ictt.blockchain.common.PairKey;

import java.util.ArrayList;
import java.util.List;

/**
 * 记录中学校信息类
 */

public class SchoolInfo{

    //学校编号
    private int schoolId;

    //学校名
    private String schoolName;

    //学校密钥对
    private PairKey schoolPairKey;

    //学校特殊属性:比如一本，二本那些，这个属性可能包含多个内容
    private List<String> schoolPro = new ArrayList<>();

    public SchoolInfo(){}

    public SchoolInfo(int schoolId, String schoolName, PairKey schoolPairKey, List<String> schoolPro){
        this.schoolId = schoolId;
        this.schoolName = schoolName;
        this.schoolPairKey = schoolPairKey;
        this.schoolPro = schoolPro;
    }

    public int getSchoolId() {

        return schoolId;
    }

    public String getSchoolName() {

        return schoolName;
    }

    public PairKey getSchoolPairKey() {
        return schoolPairKey;
    }

    public List<String> getSchoolPro() {

        return schoolPro;
    }

    public void setSchoolPro(ArrayList<String> schoolPro) {

        this.schoolPro = schoolPro;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public void setSchoolPairKey(PairKey schoolPairKey) {
        this.schoolPairKey = schoolPairKey;
    }

    public void setSchoolPro(List<String> schoolPro) {
        this.schoolPro = schoolPro;
    }

    @Override
    public String toString() {
        return "SchoolInfo{" +
                "schoolId=" + schoolId +
                ", schoolName='" + schoolName + '\'' +
                ", schoolPairKey=" + schoolPairKey +
                ", schoolPro=" + schoolPro +
                '}';
    }
}
