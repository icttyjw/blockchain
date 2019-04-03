package edu.ictt.blockchain.Block.record;

import edu.ictt.blockchain.common.PairKey;

import java.util.ArrayList;
import java.util.List;

/**
 * 记录中学校信息类
 */

public class SchoolInfo{

    /**
     * 学校编号
     */
    private int schoolId;

    /**
     * 学校名
     */
    private String schoolName;

    /**
     * 学校公钥
     */
    private String schoolPubKey;

    /**
     * 学校特殊属性:比如一本，二本,981,211等
     */
    private String schoolPro;

    public SchoolInfo(){}

    public SchoolInfo(int schoolId, String schoolName, String schoolPubKey, String schoolPro){
        this.schoolId = schoolId;
        this.schoolName = schoolName;
        this.schoolPubKey = schoolPubKey;
        this.schoolPro = schoolPro;
    }

    public int getSchoolId() {

        return schoolId;
    }

    public String getSchoolName() {

        return schoolName;
    }

    
   /* public PairKey getSchoolPairKey() {
        return schoolPubKey;
    }
    
    public void setSchoolPairKey(PairKey schoolPairKey) {
        this.schoolPubKey = schoolPairKey;
    }*/


    public String getSchoolPubKey() {
		return schoolPubKey;
	}

	public void setSchoolPubKey(String schoolPubKey) {
		this.schoolPubKey = schoolPubKey;
	}

	public String getSchoolPro() {
		return schoolPro;
	}

	public void setSchoolPro(String schoolPro) {
		this.schoolPro = schoolPro;
	}

	public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

	@Override
	public String toString() {
		return "SchoolInfo [schoolId=" + schoolId + ", schoolName=" + schoolName + ", schoolPubKey=" + schoolPubKey
				+ ", schoolPro=" + schoolPro + "]";
	}

}
