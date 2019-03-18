package edu.ictt.blockchain.Block.check;

import edu.ictt.blockchain.Block.record.DegreeRecord;
import edu.ictt.blockchain.Block.record.Record;
import edu.ictt.blockchain.common.algorithm.ECDSAAlgorithm;
import org.springframework.stereotype.Component;

/**
 * @Author:zoe
 * @Description: 学位记录校验
 * @Date:
 * @Modified By:
 */

@Component
public class DRecordChecker extends RecordChecker {

    /**
     * 学校公钥重新对记录签名，与记录当前的签名比较
     * @param record
     * @return
     */
    @Override
    public int checkSign(Record record) {

        DegreeRecord degreeRecord = (DegreeRecord) record;

        String schPublicKey = degreeRecord.getSchoolInfo().getSchoolPairKey().getPublicKey();

        try {
            if(ECDSAAlgorithm.verify(degreeRecord.toString(), degreeRecord.getSchoolSign(), schPublicKey)){
                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }
}
