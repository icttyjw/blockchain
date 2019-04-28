package edu.ictt.blockchain.Block.record;
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
	private GradeInfo gradeInfo;
	
	/**
	 * 学历信息
	 */
	private DegreeInfo degreeInfo;
	/**
	 * 说明性内容，包括管理员操作等
	 */
	private OperationInfo operationInfo;
	
	/**
	 * 记录产生的时间
	 */
	private long timeStamp;
	
	/**
	 * 其余备注
	 */
	private String comment;
	
	public NewRecord(){}
	
	/*
	 * 成绩记录构建
	 */
	public NewRecord(int record_type, String sign, GradeInfo gradeInfo, DegreeInfo degreeInfo,
			OperationInfo operationInfo, long timeStamp, String comment) {
		super();
		this.record_type = record_type;
		this.sign = sign;
		this.gradeInfo = gradeInfo;
		this.degreeInfo = degreeInfo;
		this.operationInfo = operationInfo;
		this.timeStamp = timeStamp;
		this.comment = comment;
	}
	
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
	
	public GradeInfo getGradeInfo() {
		return gradeInfo;
	}

	public String getTeacherSign() {
		return sign;
	}

	public void setGradeInfo(GradeInfo gradeInfo) {
		this.gradeInfo = gradeInfo;
	}

	public void setTeacherSign(String teacherSign) {
		this.sign = teacherSign;
	}

	public DegreeInfo getDegreeInfo() {
		return degreeInfo;
	}

	public void setDegreeInfo(DegreeInfo degreeInfo) {
		this.degreeInfo = degreeInfo;
	}

	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}

	public OperationInfo getOperationInfo() {
		return operationInfo;
	}

	public void setOperationInfo(OperationInfo operationInfo) {
		this.operationInfo = operationInfo;
	}

	@Override
	public String toString() {
		return "NewRecord [record_type=" + record_type + ", sign=" + sign + ", gradeInfo=" + gradeInfo + ", degreeInfo="
				+ degreeInfo + ", operationInfo=" + operationInfo + ", timeStamp=" + timeStamp + ", comment=" + comment
				+ "]";
	}
	
}
