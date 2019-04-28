package edu.ictt.blockchain.Block.generatorUtil;

import static edu.ictt.blockchain.common.algorithm.ECDSAAlgorithm.generatePrivateKey;
import static edu.ictt.blockchain.common.algorithm.ECDSAAlgorithm.generatePublicKey;

import java.io.UnsupportedEncodingException;

import edu.ictt.blockchain.Block.record.NewGradeInfo;
import edu.ictt.blockchain.Block.record.NewRecord;
import edu.ictt.blockchain.Block.record.SchoolInfo;
import edu.ictt.blockchain.common.FastJsonUtil;
import edu.ictt.blockchain.common.algorithm.ECDSAAlgorithm;

public class GenerateNewRecord {
		
	//学校公私钥
	String sPriKey = generatePrivateKey();
	String sPubKey = generatePublicKey(sPriKey, true);
			
	//学院公私钥
	String fPriKey = generatePrivateKey();
	String fPubKey = generatePublicKey(fPriKey,true);
			
	//教师公私钥
	String tPriKey = generatePrivateKey();
	String tPubKey = generatePublicKey(tPriKey,true);
			
	public NewRecord GenerateNewRecord() throws UnsupportedEncodingException {
		
		SchoolInfo schoolInfo = new SchoolInfo(10001,"西电",sPubKey,"211");
		NewGradeInfo gradeInfo = new NewGradeInfo(schoolInfo,1000103,1000103001,19040001,000001,1703121,85,fPubKey,tPubKey);
		String teacherPubkey = gradeInfo.getTeacherPubkey();
		String grade = FastJsonUtil.toJSONString(gradeInfo.getSchoolInfo()) + gradeInfo.getFaculthId()
		+ gradeInfo.getMajorId() + gradeInfo.getCourseId() + gradeInfo.getTeacherId()+gradeInfo.getStudentId()
		+ gradeInfo.getGrade();
		String teacherSign = ECDSAAlgorithm.sign(tPriKey, grade);
		gradeInfo.setTeacherSign(teacherSign);
		
		NewRecord record = new NewRecord(1,gradeInfo,null,null,System.currentTimeMillis(),"无");
		
		String facultyPubkey = gradeInfo.getFacultyPubkey();
		String faculty = FastJsonUtil.toJSONString(gradeInfo);
		String signSring = record.getRecord_type() + faculty + record.getTimeStamp() + record.getComment();
		String sign = ECDSAAlgorithm.sign(fPriKey, signSring);
		record.setSign(sign);
		
		return record;
	}

	public String getsPriKey() {
		return sPriKey;
	}

	public void setsPriKey(String sPriKey) {
		this.sPriKey = sPriKey;
	}

	public String getsPubKey() {
		return sPubKey;
	}

	public void setsPubKey(String sPubKey) {
		this.sPubKey = sPubKey;
	}

	public String getfPriKey() {
		return fPriKey;
	}

	public void setfPriKey(String fPriKey) {
		this.fPriKey = fPriKey;
	}

	public String getfPubKey() {
		return fPubKey;
	}

	public void setfPubKey(String fPubKey) {
		this.fPubKey = fPubKey;
	}

	public String gettPriKey() {
		return tPriKey;
	}

	public void settPriKey(String tPriKey) {
		this.tPriKey = tPriKey;
	}

	public String gettPubKey() {
		return tPubKey;
	}

	public void settPubKey(String tPubKey) {
		this.tPubKey = tPubKey;
	}
	
	
}
