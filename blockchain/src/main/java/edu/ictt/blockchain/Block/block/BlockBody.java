package edu.ictt.blockchain.Block.block;

import edu.ictt.blockchain.Block.record.Record;

import java.util.List;

/**
 * @Author:zoe
 * @Description:
 * @Date:
 *
 */
public class BlockBody {

    //记录集合，其中第一条记录为特殊记录
    private List<Record> recordsList;

    //记录哈希值集合
    private List<String> recordHashList;

    public BlockBody(){};

    public BlockBody(List<Record> recordsList, List<String> recordHashList) {
        this.recordsList = recordsList;
        this.recordHashList = recordHashList;
    }

    public List<Record> getRecordsList() {
        return recordsList;
    }

    public void setRecordsList(List<Record> recordsList) {
        this.recordsList = recordsList;
    }

    public List<String> getRecordHashList() {
        return recordHashList;
    }

    public void setRecordHashList(List<String> recordHashList) {
        this.recordHashList = recordHashList;
    }

    @Override
    public String toString() {
        return "BlockBody{" +
                "records：" + recordsList +
                '}';
    }

}
