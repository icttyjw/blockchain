package edu.ictt.blockchain.Block.check;

import edu.ictt.blockchain.Block.record.Record;

/**
 * @Author:zoe
 * @Description: 记录校验
 * @Date:
 * @Modified By:
 */
public interface RecordChecker {

    /**
     * 校验记录的所有的关键字段
     */
    int checkRecord(Record record);

    /**
     * 校验记录的时间戳,暂时简单的用本地的系统时间。
     */
    int checkTimeStamp(Record record);
   
    /**
     * 校验签名：成绩记录包括校验教师签名和学院签名;学位记录校验学校签名
     */
     int checkSign(Record record);
}
