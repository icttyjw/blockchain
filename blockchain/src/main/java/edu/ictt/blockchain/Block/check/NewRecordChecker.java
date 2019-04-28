package edu.ictt.blockchain.Block.check;

import edu.ictt.blockchain.Block.record.DegreeRecord;
import edu.ictt.blockchain.Block.record.GradeInfo;
import edu.ictt.blockchain.Block.record.NewDegreeInfo;
import edu.ictt.blockchain.Block.record.NewGradeInfo;
import edu.ictt.blockchain.Block.record.NewRecord;
import edu.ictt.blockchain.Block.record.Record;
import edu.ictt.blockchain.common.FastJsonUtil;
import edu.ictt.blockchain.common.algorithm.ECDSAAlgorithm;

import java.io.UnsupportedEncodingException;

import edu.ictt.blockchain.Block.record.DegreeInfo;

public class NewRecordChecker {
	
	/**
	 * 根据判断出的记录类型调用对应的校验方法，返回校验结果
	 * @throws UnsupportedEncodingException 
	 */
	public int checkNewRecord(NewRecord record) throws UnsupportedEncodingException {
		switch (record.getRecord_type()) {
		case 1:
			 return checkGradeInfo(record);
		case 2:
			return checkDegreeInfo(record);
		//case 3:	
		default:
			return -1;
		}	
	}

	/**
	 * 校验成绩信息
	 * @throws UnsupportedEncodingException 
	 */
	public int checkGradeInfo(NewRecord record) throws UnsupportedEncodingException {
		return checkTeacherSign(record) + checkFacultySign(record) + checkTimeStamp(record);
	}
	/**
	 * 校验学位信息
	 */
	public int checkDegreeInfo(NewRecord record) throws UnsupportedEncodingException {
		return checkSign(record) + checkTimeStamp(record);
	}
	/**
	 * 校验时间
	 * @param record
	 * @return
	 */
	public int checkTimeStamp(NewRecord record) {
		// TODO Auto-generated method stub
		// 暂时做简单判断,之后需要加误差时间
		if(record.getTimeStamp() > System.currentTimeMillis())
				return -1;
				return 0;		
	}

	/**
	 * 校验学位记录签名
	 * @param record
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public int checkSign(NewRecord record) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
        String schPublicKey = record.getDegreeInfo().getSchoolInfo().getSchoolPubKey();
        String degree = record.getRecord_type() + FastJsonUtil.toJSONString(record.getDegreeInfo()) + record.getTimeStamp() + record.getComment();

        try {
            if(ECDSAAlgorithm.verify(degree, record.getSign(), schPublicKey)){
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
	}
	
	/**
	 * 重新生成成绩记录的教师签名
	 * @throws UnsupportedEncodingException 
	 */
	public int checkTeacherSign(NewRecord record) throws UnsupportedEncodingException {
		NewGradeInfo gradeInfo = record.getGradeInfo();
		String teacherPubkey = gradeInfo.getTeacherPubkey();
		String grade = FastJsonUtil.toJSONString(gradeInfo.getSchoolInfo()) + gradeInfo.getFaculthId()
		+ gradeInfo.getMajorId() + gradeInfo.getCourseId() + gradeInfo.getTeacherId()+gradeInfo.getStudentId()
		+ gradeInfo.getGrade();
		try {
            if(ECDSAAlgorithm.verify(grade, gradeInfo.getTeacherSign(), teacherPubkey)){
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		return -1;
	}
	
	/**
	 * 重新生成成绩记录的学院签名
	 * @throws UnsupportedEncodingException 
	 */
	public int checkFacultySign(NewRecord record) throws UnsupportedEncodingException {
		NewGradeInfo gradeInfo = record.getGradeInfo();
		String facultyPubkey = gradeInfo.getFacultyPubkey();
		String faculty = FastJsonUtil.toJSONString(gradeInfo);
		String signSring = record.getRecord_type() + faculty + record.getTimeStamp() + record.getComment();
		
		try {
            if(ECDSAAlgorithm.verify(signSring, record.getSign(), facultyPubkey)){
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		return -1;
	}
}
