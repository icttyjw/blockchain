package edu.ictt.blockchain.socket.upperpbft.queue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.tio.utils.hutool.StrUtil;

import com.google.common.collect.Lists;

import edu.ictt.blockchain.core.manager.UDbBlockManager;
import edu.ictt.blockchain.socket.body.lowerbody.BlockHash;
import edu.ictt.blockchain.socket.body.upperbody.UBlockHash;
import edu.ictt.blockchain.socket.body.upperbody.URpcSimpleBlockBody;
import edu.ictt.blockchain.socket.client.ClientStarter;
import edu.ictt.blockchain.socket.client.PacketSender;
import edu.ictt.blockchain.socket.packet.BlockPacket;
import edu.ictt.blockchain.socket.packet.PacketBuilder;
import edu.ictt.blockchain.socket.packet.UPacketType;

/**
 * 收到区块之后对收到区块的处理，最终确保收到的区块是最长链上的块或者说以便在某一区块之后有可能出现的多个分叉区块中确定最终的下一区块
 * @author zoe
 *
 */
@Component
public class UNextBlockQueue {

	@Resource
	private UDbBlockManager uDbBlockManager;
	
	@Resource
	private ClientStarter clientStarter;
	
	@Resource
	private PacketSender packetSender;
	
	private Logger logger= LoggerFactory.getLogger(getClass());
	
	//requestMap存储前一区块hash和各节点针对该prevHash所连接的下一区块hash的集合。可以理解为对有可能出现的分叉情况的一种处理。
	private ConcurrentHashMap<String,List<UBlockHash>> requestMap = new ConcurrentHashMap<>();
	
	//保存已确定的区块hash
	private List<String>  wantHashs = Lists.newCopyOnWriteArrayList();
	
	public Object pop(String uBlockHash) {
		if(wantHashs.remove(uBlockHash)) {
			return uBlockHash;
		}
		return null;
	}
	
	/**
	 *请求unextblock时，在此处对收到的区块进行处理 
	 * @param uBlockHash
	 */
	public void push(UBlockHash uBlockHash) {
		String prevHash = uBlockHash.getPreUHash();
		String wantHash = uBlockHash.getUhash();
		String wantBhash = uBlockHash.getBhash();
		//当前区块的前续区块hash为空，说明前一区块为创世块？？？不应该是这样，创世块的前一区块哈希也是有的，应该与有的这个hash比较。而且不可能为空
		
		//如果本地已经有了当前区块的hash，说明不需要在进行收集各个节点的连接情况了。则移除prevHash。
		//为什么从reqMap中移除了prevHash。因为本地已经有了prevHash的下一块。
		if(uDbBlockManager.getBlockByHash(wantHash) != null) {
			requestMap.remove(wantHash);
			return;
		}
		
		//如果本地没有，加进去
		add(prevHash,uBlockHash);
		
		//寻找集合中哪个hash数最多
		int agreeCount = clientStarter.pbftAgreeCount();
		int maxCount = findMaxHash(prevHash).size();
		
		//判断当前数量最多的这个hash的数量是否满足agreecount。满足则请求获取该hash的block。
		if(maxCount>=agreeCount-1) {
			logger.info("[通信]：共有<" + maxCount + ">个节点返回next block hash为" + wantHash);
			wantHashs.add(wantHash);
			BlockPacket blockPacket = new PacketBuilder<URpcSimpleBlockBody>().setType(UPacketType.FETCH_BLOCK_INFO_REQUEST).
					setBody(new URpcSimpleBlockBody(wantHash,wantBhash)).build();
			packetSender.sendUGroup(blockPacket);
			requestMap.remove(wantHash);
		}
	}
	
	/**
	 * 将收到的区块hash添加到对应的prevHash对应的集合中
	 * @param prevHash
	 * @param uBlockHash
	 */
	private void add(String prevHash, UBlockHash uBlockHash) {
		// TODO Auto-generated method stub
		List<UBlockHash> baseResponses = requestMap.get(prevHash);
		
		if(baseResponses == null) {
			baseResponses = new ArrayList<>();
		}
		
		//避免有某个节点重复的信息
		for(UBlockHash oldResponses:baseResponses) {
			if(StrUtil.equals(oldResponses.getAppId(),uBlockHash.getAppId())) {
				return;
			}
		}
		baseResponses.add(uBlockHash);
		requestMap.put(prevHash, baseResponses);
	}

	/**
	 * 查询prevHash对应的blockHash中，哪个连接prevhash的hash数量最多
	 */
	public List<UBlockHash> findMaxHash(String key){
		List<UBlockHash> uBlockHashs = requestMap.get(key);
		
		//统计相同hash的数量
		Map<String,Integer> map = new HashMap<>();
		for(UBlockHash uBlockHash: uBlockHashs) {
			String hash = uBlockHash.getUhash();
			map.merge(hash, 1, (a,b) -> a+b);
		}
		
		String uhash = getMaxKey(map);
		return uBlockHashs.stream().filter(uBlockHash -> uhash.equals(uBlockHash.getUhash())).collect(Collectors.toList());
		
	}
	
	/**
	 * 找到List集合中数量最多的hash
	 * @param map
	 * @return
	 */
	private String getMaxKey(Map<String, Integer> map) {
		//保存最大值
		int value,flagValue=0;
		//保存最大值对应的key
		String key,flagKey = null;
		Set<Map.Entry<String,Integer>> entrySet = map.entrySet();
		// TODO Auto-generated method stub
		for(Map.Entry<String, Integer> entry:entrySet) {
			key = entry.getKey();
			value = entry.getValue();
			
			if(flagValue < value) {
				flagKey = key;
				flagValue = value;
			}
		}
		return flagKey;
	}
}
