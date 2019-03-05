package edu.ictt.blockchain.socket.record.queue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.hutool.core.collection.CollectionUtil;
import edu.ictt.blockchain.Block.block.BlockBody;
import edu.ictt.blockchain.Block.record.Record;
import edu.ictt.blockchain.core.service.BlockService;
import edu.ictt.blockchain.socket.body.RecordBody;
/*
 * 接收记录的队列
 */
public class RecordQueue {

	/*
	 * 接收的记录按课程分类
	 */
	protected ConcurrentHashMap<String, List<Record>> recordConcurrentHashMap=new ConcurrentHashMap<String, List<Record>>();
	/*
	 * 记录每类记录需要的数量
	 */
	protected ConcurrentHashMap<String, Integer> recordcountConcurrentHashMap=new ConcurrentHashMap<String, Integer>();
	
	@Resource
	private BlockService blockService;
	
	private Logger logger=LoggerFactory.getLogger(getClass());
	
	public void receive(RecordBody recordBody){
		String hash=recordBody.getIndexhash();
		int count=recordBody.getCount();
		List<Record> ls=recordConcurrentHashMap.get(hash);
		if(CollectionUtil.isEmpty(ls))
		{
			ls=new ArrayList<Record>();
		}
		ls.add(recordBody.getRecord());
		if(count!=-1)
			recordcountConcurrentHashMap.put(hash, count);
		else
			count=recordcountConcurrentHashMap.get(hash);
		if(ls.size()==count)
		{
			List<String> hashlist=ls.stream().map(Record::getHash).collect(Collectors.toList());
			BlockBody blockbody=new BlockBody(ls, hashlist);
			blockService.addBlock(blockbody);
		}
	}
}
