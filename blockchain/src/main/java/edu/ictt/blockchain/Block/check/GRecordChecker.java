package edu.ictt.blockchain.Block.check;

import edu.ictt.blockchain.Block.record.*;
import edu.ictt.blockchain.common.FastJsonUtil;
import edu.ictt.blockchain.common.algorithm.ECDSAAlgorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author:zoe
 * @Description: 成绩记录校验
 * @Date:
 * @Modified By:
 */

@Component
public class GRecordChecker implements RecordChecker {

	private Logger logger = LoggerFactory.getLogger(getClass());
    
	@Override
    public int checkRecord(Record record){
        GradeRecord gradeRecord = (GradeRecord) record;
       //int cfs=checkFalSign(gradeRecord);
       // int cteas=checkTeacherSign(gradeRecord);
        int cs=checkSign(gradeRecord);
        int ctimes=checkTimeStamp(gradeRecord);
        int checkResult = cs+ctimes;//checkFalSign(gradeRecord) + checkTeacherSign(gradeRecord) + checkSign(gradeRecord) + checkTimeStamp(gradeRecord);
        System.out.println(cs+" "+ctimes);
        if (checkResult == 0){
            return 0;
        }
        return -1;
    }
	
	@Override
	public int checkTimeStamp(Record record) {
		// 暂时做简单判断,之后需要加误差时间
		if(record.getRecordTimeStamp() > System.currentTimeMillis())
			return -1;
		return 0;
	}
	
	@Override
    public int checkSign(Record record) {

        GradeRecord gradeRecord = (GradeRecord) record;
        int cfs=checkFalSign(gradeRecord);
        int cteas=checkTeacherSign(gradeRecord);
        //System.out.println
        logger.info("签名校验"+cfs+" "+cteas);
        if(cfs==0 && cteas==0){//checkTeacherSign(gradeRecord) == 0 && checkFalSign(gradeRecord) == 0
            return 0;
        }
        return -1;
    }
	 
	

    //教师和学校公钥重新对记录签名，与记录当前的签名比较
   
    /**
     * 校验教师的签名
     * @param gradeRecord
     * @return
     */
    public int checkTeacherSign(GradeRecord gradeRecord){
    	String scinfo=FastJsonUtil.toJSONString(gradeRecord.getSchoolInfo());
    	String facuinfo=FastJsonUtil.toJSONString(gradeRecord.getFacultyInfo());
    	String grainfo=FastJsonUtil.toJSONString(gradeRecord.getGradeInfo());
        String graRecord =scinfo+facuinfo+grainfo//scinfo+facuinfo+grainfo//gradeRecord.getSchoolInfo().toString()+gradeRecord.getFacultyInfo().toString()
        		 + gradeRecord.getRecordTimeStamp(); // +gradeRecord.getGradeInfo().toString() + gradeRecord.getRecordTimeStamp();


        TeacherInfo[] teacherInfos = gradeRecord.getGradeInfo().getTeacherInfo();

        int checkFlag = -1;

        //如果有多个任课教师，任课教师中任一一人的签名匹配即可
        for(TeacherInfo teacherInfo:teacherInfos){
            String teaPublicKey = teacherInfo.getTeacherPubKey();
            //logger.info("当前教师签名" + gradeRecord.getTeacherSign());
            try {
                if(ECDSAAlgorithm.verify(graRecord, gradeRecord.getTeacherSign(), teaPublicKey))
                    checkFlag = 0;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return checkFlag;
    }

    /**
     * 校验学院的签名
     * @param gradeRecord
     * @return
     */
    public int checkFalSign(GradeRecord gradeRecord){
    	String scinfo=FastJsonUtil.toJSONString(gradeRecord.getSchoolInfo());
    	String facuinfo=FastJsonUtil.toJSONString(gradeRecord.getFacultyInfo());
    	String grainfo=FastJsonUtil.toJSONString(gradeRecord.getGradeInfo());
        String graRecord = scinfo+facuinfo+grainfo//scinfo+facuinfo+grainfo/*gradeRecord.getSchoolInfo().toString()+gradeRecord.getFacultyInfo().toString()
        		+ gradeRecord.getRecordTimeStamp() + gradeRecord.getTeacherSign();//+gradeRecord.getGradeInfo().toString() + gradeRecord.getRecordTimeStamp() + gradeRecord.getTeacherSign();

        int checkFlag = -1;

        //logger.info("当前学院公钥" + gradeRecord.getFacultyInfo().getFacultyPubKey());
        String facPublicKey = gradeRecord.getFacultyInfo().getFacultyPubKey();

        try {
            //logger.info("当前记录：" + graRecord);
            //logger.info("当前学院签名" + gradeRecord.getFacultySign());
            //logger.info("当前学院公钥：" + facPublicKey);
            if(ECDSAAlgorithm.verify(graRecord, gradeRecord.getFacultySign(), facPublicKey)){
                checkFlag = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return checkFlag;
    }

	



}
