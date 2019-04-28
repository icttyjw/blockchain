package edu.ictt.blockchain.Block.record;

import java.io.UnsupportedEncodingException;

import edu.ictt.blockchain.common.FastJsonUtil;
import edu.ictt.blockchain.common.algorithm.ECDSAAlgorithm;

/*
 * 包含所有信息
 */
public class NewRecord {
	
	/**
	 * 记录类型
	 * 成绩记录 1
	 * 学位记录 2
	 * 操作记录 3
	 */
	private int record_type;

	/**
	 * 签名
	 * 每个记录一个单独的签名
	 * 如果有多个签名，多个签名拼接
	 */
	private String sign;

	/**
	 * 成绩信息
	 */
	private NewGradeInfo gradeInfo;
	
	/**
	 * 学历信息
	 */
	private NewDegreeInfo degreeInfo;
	/**
	 * 包括管理员操作等
	 */
	private NewOperaInfo operationInfo;
	
	/**
	 * 记录产生的时间
	 */
	private long timeStamp;
	
	/**
	 * 说明性内容，其余备注
	 */
	private String comment;
	
	public NewRecord(){}
	
	/*
	 * 成绩记录构建
	 */
	public NewRecord(int record_type, NewGradeInfo gradeInfo, NewDegreeInfo degreeInfo,
			NewOperaInfo operationInfo, long timeStamp, String comment) {
		super();
		this.record_type = record_type;
		//this.sign = sign;
		this.gradeInfo = gradeInfo;
		this.degreeInfo = degreeInfo;
		this.operationInfo = operationInfo;
		this.timeStamp = timeStamp;
		this.comment = comment;
	}
	
	
	/*public String buildSign() throws UnsupportedEncodingException {
		switch (record_type) {
		case 1:
			sign = getTeacherSign() + getFacultySign();
			break;
		case 2:
			sign = getDegreeSign();
			break;
		case 3:
			sign = getOperaSign();
			break;
		default:
			break;
		}
		return sign;
		
	}*/
	
	/*私钥签名，不在此处签名
	private String getOperaSign() throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		String operaInfo = record_type + FastJsonUtil.toJSONString(operationInfo) + timeStamp + comment;
		String operaSign = ECDSAAlgorithm.sign(operationInfo.getOperatorPubkey(),operaInfo);
		return operaSign;
	}

	private String getDegreeSign() throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		String degree = record_type + FastJsonUtil.toJSONString(degreeInfo) + timeStamp + comment;
		String degreeSign = ECDSAAlgorithm.sign(degreeInfo.getSchoolInfo().getSchoolPubKey(), degree);
		return degreeSign;
	}
	
	private String getTeacherSign() throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		String grade = FastJsonUtil.toJSONString(gradeInfo.getSchoolInfo()) + gradeInfo.getFaculthId()
		+ gradeInfo.getMajorId() + gradeInfo.getCourseId() + gradeInfo.getTeacherId()+gradeInfo.getStudentId()
		+ gradeInfo.getGrade();
		String teacherSign = ECDSAAlgorithm.sign(gradeInfo.getTeacherPubkey(), grade);
		return teacherSign;
	}

	//教师签名的基础上再签
	private String getFacultySign() {
		// TODO Auto-generated method stub
		String faculty = getTeacherSign() + gradeInfo.getTeacherPubkey() + gradeInfo.getFacultyPubkey();
		String facultySign = ECDSAAlgorithm.sign(gradeInfo.getFacultyPubkey(), faculty);
		String signSring = record_type + facultySign + timeStamp + comment;
		String sign = ECDSAAlgorithm.sign(gradeInfo.getFacultyPubkey(), signSring);
		return sign;
	}*/

	public int getRecord_type() {
		return record_type;
	}

	public String getSign() {
		return sign;
	}

	
	public void setSign(String sign) {
		this.sign = sign;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public void setRecord_type(int record_type) {
		this.record_type = record_type;
	}
	
	public NewGradeInfo getGradeInfo() {
		return gradeInfo;
	}

	public void setGradeInfo(NewGradeInfo gradeInfo) {
		this.gradeInfo = gradeInfo;
	}

	public NewDegreeInfo getDegreeInfo() {
		return degreeInfo;
	}

	public void setDegreeInfo(NewDegreeInfo degreeInfo) {
		this.degreeInfo = degreeInfo;
	}

	public NewOperaInfo getOperationInfo() {
		return operationInfo;
	}

	public void setOperationInfo(NewOperaInfo operationInfo) {
		this.operationInfo = operationInfo;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "NewRecord [record_type=" + record_type + ", sign=" + sign + ", gradeInfo=" + gradeInfo + ", degreeInfo="
				+ degreeInfo + ", operationInfo=" + operationInfo + ", timeStamp=" + timeStamp + ", comment=" + comment
				+ "]";
	}
	
}
