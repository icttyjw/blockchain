package edu.ictt.blockchain.Block.record;

import java.io.Serializable;

/**
 * @Author:zoe
 * @Description:
 * @Date:
 *
 */
public class DegreeRecord implements Serializable, Record {

    /**
     * 学校信息
     */
    private SchoolInfo schoolInfo;

    /**
     * 学位信息
     */
    private DegreeInfo degreeInfo;

    /**
     * 记录时间戳
     */
    private long recordTimeStamp;
    private String hash;
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

    public long getRecordTimeStamp() {
        return recordTimeStamp;
    }

    public void setRecordTimeStamp(long recordTimeStamp) {
        this.recordTimeStamp = recordTimeStamp;
    }
    @Override
	public void setHash(String hash) {
		this.hash=hash;
		
	}

	@Override
	public String getHash() {
		return hash;
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
