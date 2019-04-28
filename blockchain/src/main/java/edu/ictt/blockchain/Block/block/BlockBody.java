package edu.ictt.blockchain.Block.block;

import edu.ictt.blockchain.Block.record.DegreeRecord;
import edu.ictt.blockchain.Block.record.GradeRecord;
import edu.ictt.blockchain.Block.record.NewRecord;
import edu.ictt.blockchain.Block.record.Record;

import java.util.List;

/**
 * @Author:
 * @Description:
 * @Date:
 *
 */
public class BlockBody {

    //成绩记录集合
    private List<GradeRecord> grecordsList;

    //学位记录集合
    private List<DegreeRecord> drecordsList;
    
    private List<NewRecord> recordList;

    public BlockBody(){};

    
    public BlockBody(List<GradeRecord> grecordsList,List<DegreeRecord> drecordsList) {
		super();
		this.grecordsList = grecordsList;
		this.drecordsList = drecordsList;
	}
    public BlockBody(List<NewRecord> recordList){
    	this.recordList=recordList;
    }


	public List<NewRecord> getRecordList() {
		return recordList;
	}


	public void setRecordList(List<NewRecord> recordList) {
		this.recordList = recordList;
	}


	public List<GradeRecord> getGrecordsList() {
		return grecordsList;
	}


	public List<DegreeRecord> getDrecordsList() {
		return drecordsList;
	}


	public void setGrecordsList(List<GradeRecord> grecordsList) {
		this.grecordsList = grecordsList;
	}


	public void setDrecordsList(List<DegreeRecord> drecordsList) {
		this.drecordsList = drecordsList;
	}


	@Override
	public String toString() {
		return "BlockBody [grecordsList=" + grecordsList + ", drecordsList=" + drecordsList + ", recordList="
				+ recordList + "]";
	}

	/*@Override
	public String toString() {
		return "BlockBody [grecordsList=" + grecordsList + ", drecordsList=" + drecordsList + "]";
	}*/
  
}
