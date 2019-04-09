package edu.ictt.blockchain.socket.body;

import edu.ictt.blockchain.Block.record.GradeRecord;
import edu.ictt.blockchain.Block.record.Record;

public class RecordBody extends BaseBody{

	private Record record;
	
	private String indexhash;
	//课程系列第一条记录发送时需要附带后续的记录数量
	private int count;
	
	public RecordBody() {
		super();
	}
	public RecordBody(Record record,String indexhash,int count){
		super();
		this.record=record;
		this.indexhash=indexhash;
		this.count=count;
	}
	public RecordBody(Record record,String indexhash){
		super();
		this.record=record;
		this.indexhash=indexhash;
		this.count=-1;
	}
	

	public Record getRecord() {
		return record;
	}

	
	public void setRecord(Record record) {
		this.record = record;
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
	@Override
	public String toString() {
		return "RecordBody [record=" + record + ", indexhash=" + indexhash + ", count=" + count + "]";
	}



}
