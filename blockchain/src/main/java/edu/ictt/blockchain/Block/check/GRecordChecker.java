package edu.ictt.blockchain.Block.check;

import edu.ictt.blockchain.Block.record.*;
import edu.ictt.blockchain.common.algorithm.ECDSAAlgorithm;
import org.springframework.stereotype.Component;

/**
 * @Author:zoe
 * @Description: 成绩记录校验
 * @Date:
 * @Modified By:
 */

@Component
public class GRecordChecker extends RecordChecker {


    @Override
    public boolean checkRecord(Record record){
        GradeRecord gradeRecord = (GradeRecord) record;
        int checkResult = checkFalSign(gradeRecord) + checkTeacherSign(gradeRecord) + checkSign(gradeRecord) + checkTimeStamp(gradeRecord);

        if (checkResult == 0){
            return true;
        }
        return false;
    }

    /**
     * 教师和学校公钥重新对记录签名，与记录当前的签名比较
     * @param record
     * @return
     */
    @Override
    public int checkSign(Record record) {

        GradeRecord gradeRecord = (GradeRecord) record;

        if(checkTeacherSign(gradeRecord) == 1 && checkFalSign(gradeRecord) == 1){
            return 0;
        }
        return -1;
    }

    //校验教师的签名
    public int checkTeacherSign(GradeRecord gradeRecord){
        String graRecord = gradeRecord.getSchoolInfo().toString()+gradeRecord.getFacultyInfo().toString()
                +gradeRecord.getGradeInfo().toString() + gradeRecord.getRecordTimeStamp();


        TeacherInfo[] teacherInfos = gradeRecord.getGradeInfo().getTeacherInfo();

        int checkFlag = -1;

        //如果有多个任课教师，任课教师中任一一人的签名匹配即可

        for(TeacherInfo teacherInfo:teacherInfos){
            String teaPublicKey = teacherInfo.getTeacherPairKey().getPublicKey();
            System.out.println("当前教师签名" + gradeRecord.getTeacherSign());
            try {
                if(ECDSAAlgorithm.verify(graRecord, gradeRecord.getTeacherSign(), teaPublicKey))
                    checkFlag = 1;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return checkFlag;
    }

    //校验学院的签名
    public int checkFalSign(GradeRecord gradeRecord){
        String graRecord = gradeRecord.getSchoolInfo().toString()+gradeRecord.getFacultyInfo().toString()
                +gradeRecord.getGradeInfo().toString() + gradeRecord.getRecordTimeStamp() + gradeRecord.getTeacherSign();

        int checkFlag = -1;

        System.out.println("当前学院公钥" + gradeRecord.getFacultyInfo().getFacultyPairKey().getPublicKey());
        String facPublicKey = gradeRecord.getFacultyInfo().getFacultyPairKey().getPublicKey();

        try {
            //System.out.println("当前记录：" + graRecord);
            System.out.println("当前学院签名" + gradeRecord.getFalSign());
            //System.out.println("当前学院公钥：" + facPublicKey);
            if(ECDSAAlgorithm.verify(graRecord, gradeRecord.getFalSign(), facPublicKey)){
                checkFlag = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return checkFlag;
    }



}
