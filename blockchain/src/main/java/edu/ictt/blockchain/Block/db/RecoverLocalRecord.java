package edu.ictt.blockchain.Block.db;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import edu.ictt.blockchain.Block.block.Block;
import edu.ictt.blockchain.Block.check.GRecordChecker;
import edu.ictt.blockchain.Block.db.ConnectRocksDB;
import edu.ictt.blockchain.Block.record.DegreeRecord;
import edu.ictt.blockchain.Block.record.GradeRecord;
import edu.ictt.blockchain.Block.record.Record;
import edu.ictt.blockchain.common.FastJsonUtil;
import org.rocksdb.RocksDBException;
import org.rocksdb.RocksIterator;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author:zoe
 * @Description: 恢复本地未上链的记录
 * @Date:
 * @Modified By:
 */
public class RecoverLocalRecord {

    ConnectRocksDB rocksDB;

    public void recoverRecord() throws RocksDBException {
        rocksDB = new ConnectRocksDB(3);

        /*读出课程的各自的记录队列
         */
        List<String> courseList;
        List<Integer> recordCountList;

        /*key:课程; value: 课程对应的记录列表
         *
         */
        ConcurrentHashMap<String, List<GradeRecord>> recordConcurrentHashMap=new ConcurrentHashMap<String, List<GradeRecord>>();
        /*
         * key:课程hash; value: 课程记录的数量
         */
        ConcurrentHashMap<String, Integer> recordcountConcurrentHashMap=new ConcurrentHashMap<String, Integer>();

        //获取课程列表
        String courses = rocksDB.getRocksDbStore().get("course");
        courseList = FastJsonUtil.toList(courses,String.class);

        //获取课程对应的记录数量
        String recordCount = rocksDB.getRocksDbStore().get(courses);
        recordCountList = FastJsonUtil.toList(recordCount, Integer.class);

        //记录校验器
        GRecordChecker gRecordChecker = new GRecordChecker();

        //先恢复
        for(String course : courseList) {
            String records = rocksDB.getRocksDbStore().get(course);
            System.out.println(records);
            List<GradeRecord> recordList = FastJsonUtil.toList(records, GradeRecord.class);
            /*for(GradeRecord gradeRecord:recordList){
                if(gRecordChecker.checkRecord(gradeRecord))
                    recordConcurrentHashMap.put(course, recordList);
                else{
                    System.out.println("此记录出错:" + gradeRecord.toString());
                }
            }*/

            System.out.println("记录恢复完毕");
        }

        //再判断记录的课程数量和课程的记录数量是否匹配,对于缺失的暂时未作处理
        /**
        if(courseList.size() == recordCountList.size()){
            Iterator<String>  courseIte = courseList.iterator();
            Iterator<Integer> recordCountIte = recordCountList.iterator();
            while(courseIte.hasNext()){
                String curCourse = courseIte.next();
                //该课程当前恢复的记录数量
                int curRecordsCount = recordConcurrentHashMap.get(curCourse).size();

                //该课程之前保存的记录数量
                int recordsCount = recordCountIte.next();
                if(curRecordsCount == recordsCount){
                    System.out.println(curCourse + "课程记录恢复成功:");
                }else{
                    int losCount = recordsCount-curRecordsCount;
                    System.out.println(curCourse + "课程成绩记录缺失" + losCount + "条");
                }
                //输出当前课程记录
                System.out.println(recordConcurrentHashMap.get(curCourse));
            }
        }else{
            System.out.println("部分课程成绩记录缺失");
        }*/
    }
}
