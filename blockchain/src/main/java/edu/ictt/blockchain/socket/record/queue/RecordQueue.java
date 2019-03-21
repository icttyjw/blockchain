package edu.ictt.blockchain.socket.record.queue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.hutool.core.collection.CollectionUtil;
import edu.ictt.blockchain.Block.block.BlockBody;
import edu.ictt.blockchain.Block.record.Record;
import edu.ictt.blockchain.common.FastJsonUtil;
import edu.ictt.blockchain.core.manager.DbBlockManager;
import edu.ictt.blockchain.core.service.BlockService;
import edu.ictt.blockchain.socket.body.RecordBody;
/*
 * 接收记录的队列
 */
@Component
public class RecordQueue {

	@Resource
	DbBlockManager dbBlockManager;
	
	/*
	 * 接收的记录按课程分类  下列三个都需要进行初始化，如果db中存在对应元素，应当填入，如果不存在则为空
	 */
	protected ConcurrentHashMap<String, List<Record>> recordConcurrentHashMap=new ConcurrentHashMap<String, List<Record>>();
	/*
	 * 记录每类记录需要的数量
	 */
	protected ConcurrentHashMap<String, Integer> recordcountConcurrentHashMap =new ConcurrentHashMap<String, Integer>();

	/*
	 * 课程名列表以及课程对应的课程数量
	 * 恢复时通过这个表找到所有需要恢复的课程
	 */
	private List<String> course=new ArrayList< >();
	//private List<Integer> recordCount = new ArrayList<>();
	private  int recordCount;
	
	@Resource
	private BlockService blockService;
	
	private Logger logger=LoggerFactory.getLogger(getClass());
	
	public void receive(RecordBody recordBody){
		String hash=recordBody.getIndexhash();
		//count这块需要做处理，否则每条记录都会有
		int count=recordBody.getCount();
		List<Record> ls=recordConcurrentHashMap.get(hash);
		/*
		 * 第一次收到某课程记录需要新建一个课程列表准备接收后续成绩
		 * 在课程类列表中更新一个新课程，并存入数据库
		 * 
		 */
		if(CollectionUtil.isEmpty(ls))
		{
			ls=new ArrayList<Record>();
			course.add(hash);//对课程名进行记录
			recordCount = count;//课程的记录数量
			String cstring=FastJsonUtil.toJSONString(course);
			dbBlockManager.put("course", cstring);
			System.out.println("新增一门课程：" + cstring);
		}
		ls.add(recordBody.getRecord());
		if(count!=-1){
			recordcountConcurrentHashMap.put(hash, count);
		}
		else
			count=recordcountConcurrentHashMap.get(hash);
		if(ls.size()==count)
		{
			List<String> hashlist=ls.stream().map(Record::getHash).collect(Collectors.toList());
			BlockBody blockbody=new BlockBody(ls, hashlist);
			blockService.addBlock(blockbody);
		}else
		{//备份记录
			String recordlist=FastJsonUtil.toJSONString(ls);
			dbBlockManager.put(hash, recordlist);
			System.out.println("备份的记录对应的课程" + hash + ":" + "记录" + recordlist);

			//备份每个课程的记录数量
			//dbBlockManager.put(FastJsonUtil.toJSONString(course),FastJsonUtil.toJSONNoFeatures(recordCount));
			//System.out.println("备份课程的记录数量" + hash + ":" + "记录数量" + recordCount);
		}
	}

	public DbBlockManager getDbBlockManager() {
		return dbBlockManager;
	}

	public void setDbBlockManager(DbBlockManager dbBlockManager) {
		this.dbBlockManager = dbBlockManager;
	}
}
