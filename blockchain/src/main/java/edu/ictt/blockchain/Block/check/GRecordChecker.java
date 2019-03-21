package edu.ictt.blockchain.Block.check;

import edu.ictt.blockchain.Block.record.*;
import edu.ictt.blockchain.common.ObjectAndByte;
import edu.ictt.blockchain.common.algorithm.ECDSAAlgorithm;
import edu.ictt.blockchain.common.ecc.ECkeyUtil;
import org.springframework.stereotype.Component;
import sun.security.ec.ECPublicKeyImpl;

import java.security.PublicKey;

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
        int checkResult = checkSchoolSign(gradeRecord) + checkTeacherSign(gradeRecord) + checkSign(gradeRecord) + checkTimeStamp(gradeRecord);

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

        if(checkTeacherSign(gradeRecord) == 1 && checkSchoolSign(gradeRecord) == 1){
            return 0;
        }
        return -1;
    }

    //校验教师的签名
    public int checkTeacherSign(GradeRecord gradeRecord){
        String graRecord = gradeRecord.getSchoolInfo().toString()+gradeRecord.getFacultyInfo().toString()
                +gradeRecord.getGradeInfo().toString();

        TeacherInfo[] teacherInfos = gradeRecord.getGradeInfo().getTeacherInfo();

        int checkFlag = -1;

        //如果有多个任课教师，任课教师中任一一人的签名匹配即可

        for(TeacherInfo teacherInfo:teacherInfos){
            String teaPublicKey = teacherInfo.getteacherPairKey().getPublicKey();
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
    public int checkSchoolSign(GradeRecord gradeRecord){
        String graRecord = gradeRecord.getSchoolInfo().toString()+gradeRecord.getFacultyInfo().toString()
                +gradeRecord.getGradeInfo().toString() + gradeRecord.getTeacherSign();

        FacultyInfo facultyInfo = gradeRecord.getFacultyInfo();

        int checkFlag = -1;

        String facPublicKey = facultyInfo.getFaculthPairKey().getPublicKey();

        try {
            if((ECDSAAlgorithm.verify(graRecord, gradeRecord.getSign(), facPublicKey))){
                checkFlag = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return checkFlag;
    }



}
