package edu.ictt.blockchain.Block.record;

import java.io.Serializable;

/**
 * @Author:zoe
 * @Description:
 * @Date:
 *
 */
public class DegreeRecord extends Record implements Serializable {

    /**
     * 学校信息
     */
    private SchoolInfo schoolInfo;

    /**
     * 学位信息
     */
    private DegreeInfo degreeInfo;

    public DegreeRecord(){}

    public DegreeRecord(SchoolInfo schoolInfo, DegreeInfo degreeInfo, long recordTimeStamp){
        this.schoolInfo = schoolInfo;
        this.degreeInfo = degreeInfo;
        this.recordTimeStamp = recordTimeStamp;
    }

    public SchoolInfo getSchoolInfo() {
        return schoolInfo;
    }

    public void setSchoolInfo(SchoolInfo schoolInfo) {
        this.schoolInfo = schoolInfo;
    }

    public DegreeInfo getDegreeInfo() {
        return degreeInfo;
    }

    public void setDegreeInfo(DegreeInfo degreeInfo) {
        this.degreeInfo = degreeInfo;
    }


    @Override
    public String toString() {
        return "DegreeRecord{" +
                "schoolInfo=" + schoolInfo +
                ", degreeInfo=" + degreeInfo +
                ", recordTimeStamp=" + recordTimeStamp +
                '}';
    }

	

}
