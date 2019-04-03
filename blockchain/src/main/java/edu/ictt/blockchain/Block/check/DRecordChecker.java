package edu.ictt.blockchain.Block.check;

import edu.ictt.blockchain.Block.record.DegreeRecord;
import edu.ictt.blockchain.Block.record.GradeRecord;
import edu.ictt.blockchain.Block.record.Record;
import edu.ictt.blockchain.common.algorithm.ECDSAAlgorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author:
 * @Description: 学位记录校验
 * @Date:
 * @Modified By:
 */

@Component
public class DRecordChecker implements RecordChecker {

	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
    public int checkRecord(Record record){
		int cs = checkSign(record);
		int ctimes = checkTimeStamp(record);
        int checkResult = cs + ctimes ;
        if (checkResult == 0){
            return 0;
        }
        return -1;
    }
	
	@Override
	public int checkTimeStamp(Record record) {
	// 暂时做简单判断,之后优化记录时间获取时候把这块在具体一些,比如加误差时间
		if(record.getRecordTimeStamp() > System.currentTimeMillis())
			//拒绝
			return -1;
		return 0;
	}
	
    /**
     * 学校公钥重新对记录签名，与记录当前的签名比较
     * @param record
     * @return
     */
    @Override
    public int checkSign(Record record) {

        DegreeRecord degreeRecord = (DegreeRecord) record;
        String schPublicKey = degreeRecord.getSchoolInfo().getSchoolPubKey();

        try {
            if(ECDSAAlgorithm.verify(degreeRecord.toString(), degreeRecord.getSchoolSign(), schPublicKey)){
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

	
}
