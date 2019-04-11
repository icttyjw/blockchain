package edu.ictt.blockchain.socket.body;

import edu.ictt.blockchain.Block.record.DegreeRecord;
import edu.ictt.blockchain.Block.record.GradeRecord;
import edu.ictt.blockchain.Block.record.Record;

public class RecordBody extends BaseBody{

	private GradeRecord gradeRecord;
	
	private DegreeRecord degreeRecord;
	
	private String indexhash;
	//课程系列第一条记录发送时需要附带后续的记录数量
	private int count;
	
	private String type;
	
	public RecordBody() {
		super();
	}
	public RecordBody(GradeRecord gradeRecord,String indexhash,int count){
		super();
		this.gradeRecord=gradeRecord;
		this.degreeRecord=null;
		this.indexhash=indexhash;
		this.count=count;
	}
	public RecordBody(GradeRecord gradeRecord,String indexhash){
		super();
		this.gradeRecord=gradeRecord;
		this.degreeRecord=null;
		this.indexhash=indexhash;
		this.count=-1;
	}
	public RecordBody(DegreeRecord degreeRecord,String indexhash,int count){
		super();
		this.degreeRecord=degreeRecord;
		this.indexhash=indexhash;
		this.count=count;
	}
	public RecordBody(DegreeRecord degreeRecord,String indexhash){
		super();
		this.degreeRecord=degreeRecord;
		this.indexhash=indexhash;
		this.count=-1;
	}
	

	
	public String getIndexhash() {
		return indexhash;
	}

	public void setIndexhash(String indexhash) {
		this.indexhash = indexhash;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	public GradeRecord getGradeRecord() {
		return gradeRecord;
	}
	public DegreeRecord getDegreeRecord() {
		return degreeRecord;
	}
	public String getType() {
		return type;
	}
	public void setGradeRecord(GradeRecord gradeRecord) {
		this.gradeRecord = gradeRecord;
	}
	public void setDegreeRecord(DegreeRecord degreeRecord) {
		this.degreeRecord = degreeRecord;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "RecordBody [gradeRecord=" + gradeRecord + ", degreeRecord=" + degreeRecord + ", indexhash=" + indexhash
				+ ", count=" + count + ", type=" + type + "]";
	}
	
	



}
