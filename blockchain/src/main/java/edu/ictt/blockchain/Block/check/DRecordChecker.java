package edu.ictt.blockchain.Block.check;

import edu.ictt.blockchain.Block.record.Record;

/**
 * @Author:zoe
 * @Description: 学位记录校验
 * @Date:
 * @Modified By:
 */
public class DRecordChecker extends RecordChecker {

    /**
     * 学校公钥重新对记录签名，与记录当前的签名比较
     * @param record
     * @return
     */
    @Override
    public int checkSign(Record record) {
        return 0;
    }
}
