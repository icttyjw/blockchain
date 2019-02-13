package edu.ictt.blockchain.socket.pbft.queue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.hutool.core.collection.CollectionUtil;
import edu.ictt.blockchain.socket.pbft.msg.VoteMsg;

/**
 * 
 * 
 *
 */
public abstract class AbstractVoteMsgQueue extends BaseMsgQueue {

	
	 /**
     * 存储所有的hash的投票集合
     */
	
	public static ConcurrentHashMap<String , List<VoteMsg>> voteMsgConcurrentHashMap=new ConcurrentHashMap<>();
	 /**
     * 存储本节点已确认状态的hash的集合，即本节点已对外广播过允许commit或拒绝commit的消息了
     */
	public static ConcurrentHashMap<String, Boolean> voteStateConcurrentHashMap=new ConcurrentHashMap<>();
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	abstract void deal(VoteMsg voteMsg,List<VoteMsg> voteMsgs);
	
	@Override
	public void push(VoteMsg voteMsg) {
		// TODO Auto-generated method stub
		System.out.println(10);
		String hash=voteMsg.getHash();
		VoteMsg msg=new VoteMsg();
		msg.setAgree(true);
		msg.setAppId("3");
		msg.setHash("111");
		msg.setNumber(1);
		List<VoteMsg> vo=new ArrayList<VoteMsg>();
		vo.add(msg);
		voteMsgConcurrentHashMap.put(hash, vo);
		List<VoteMsg> voteMsgs=voteMsgConcurrentHashMap.get(hash);
		if(CollectionUtil.isEmpty(voteMsgs)){
			System.out.println("投票消息的集合为空");
			System.out.println(10);
			voteMsgs=new ArrayList<VoteMsg>();
			voteMsgConcurrentHashMap.put(hash, voteMsgs);
		}else{
			//如果不空的情况下，判断本地集合是否已经存在完全相同的voteMsg了
			System.out.println("开始判断本地是否存在完全相同的voteMsg");
			System.out.println(10);
            for (VoteMsg temp : voteMsgs) {
            	System.out.println("当前voteMsgs中的Number" + temp.getNumber());
                if (temp.getAppId().equals(voteMsg.getAppId())) {
                	System.out.println(101);
                    return;
                }
                System.out.println(10);
            }
		}
		System.out.println(10);
		//添加进去
        voteMsgs.add(voteMsg);
        //如果已经对该hash投过票了，就不再继续
        if (voteStateConcurrentHashMap.get(hash) != null) {

            System.out.println("not null 已经对该hash投过票 ");
        	//return;

        }

        deal(voteMsg, voteMsgs);
		
	}

	
	/**
     * 该方法用来确认待push阶段的下一阶段是否已存在已达成共识的Block <p>
     * 譬如收到了区块5的Prepare的投票信息，那么我是否接受该投票，需要先校验Commit阶段是否已经存在number>=5的投票成功信息
     *
     * @param hash
     *         hash
     * @return 是否超过
     */
	public boolean hasOtherConfirm(String hash,int number){
		System.out.println(5);
		//遍历该阶段的所有投票信息
		for(String key:voteMsgConcurrentHashMap.keySet()){
			//如果下一阶段存在同一个hash的投票，则不理会
            if (hash.equals(key)) {
                continue;
            }
          //如果下一阶段的number比当前投票的小，则不理会
            if (voteMsgConcurrentHashMap.get(key).get(0).getNumber() < number) {
            	System.out.println("small");
            	continue;
            }
            else
            System.out.println("big");
            //只有别的>=number的Block已经达成共识了，则返回true，那么将会拒绝该hash进入下一阶段
            if (voteStateConcurrentHashMap.get(key) != null && voteStateConcurrentHashMap.get(key)) {
            	System.out.println("true");
                return true;
            }
            
		}
		return false;
	}
	
	
	/**
     * 清理旧的block的hash
     */
}
