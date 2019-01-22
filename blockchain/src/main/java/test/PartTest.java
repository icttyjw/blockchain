package test;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;

import edu.ictt.blockchain.bean.Block;
import edu.ictt.blockchain.bean.Runstate;
import edu.ictt.blockchain.common.FastJsonUtil;
import edu.ictt.blockchain.common.PairKey;
import edu.ictt.blockchain.common.algorithm.ECDSAAlgorithm;
import edu.ictt.blockchain.common.util.DerbyDBUtil;
import edu.ictt.blockchain.manager.ManageMessage;
import edu.ictt.blockchain.socket.body.StateBody;
import edu.ictt.blockchain.socket.pbft.VoteType;
import edu.ictt.blockchain.socket.pbft.msg.VoteMsg;
import edu.ictt.blockchain.socket.pbft.msg.VotePreMsg;
import edu.ictt.blockchain.socket.pbft.queue.BaseMsgQueue;
import edu.ictt.blockchain.socket.pbft.queue.CommitMsgQueue;
import edu.ictt.blockchain.socket.pbft.queue.PreMsgQueue;

import static edu.ictt.blockchain.socket.pbft.Message.blockConcurrentHashMap;
import static edu.ictt.blockchain.socket.pbft.Message.findByHash;

public class PartTest {

	@Test
	public void msgtest(){
		Block block=new Block();
		block.setHash("3");
		VotePreMsg voteMsg=new VotePreMsg();
		voteMsg.setAgree(true);
		voteMsg.setAppId("1");
		voteMsg.setBlock(block);
		blockConcurrentHashMap.put("s", voteMsg);
		Block b=findByHash("s");
		System.out.println(b.getHash());
	}
	
	@Test
	public void basetest(){
		BaseMsgQueue baseMsgQueue=new CommitMsgQueue();//new PreMsgQueue();//=
		System.out.println(baseMsgQueue.getClass());
	}
	
	@Test
	public void votetest(){
		VoteMsg voteMsg=new VoteMsg();
		VoteMsg voMsg=new VotePreMsg();
		voteMsg.setAgree(true);
		voteMsg.setHash("111");
		voteMsg.setVoteType(VoteType.pre);
		//voMsg=(VotePreMsg)voteMsg;
		VotePreMsg votePreMsg=JSONObject.parseObject(JSONObject.toJSONString(voteMsg),VotePreMsg.class);
		System.out.println(votePreMsg);
	}
	
	@Test
	public void ecdsatest() throws UnsupportedEncodingException{
		PairKey pk=new PairKey();
		pk.setPrivateKey(ECDSAAlgorithm.generatePrivateKey());
		pk.setPublicKey(ECDSAAlgorithm.generatePublicKey(pk.getPrivateKey()));
		String sign=ECDSAAlgorithm.sign(pk.getPrivateKey(), "1234");
		try {
			boolean t=ECDSAAlgorithm.verify("1234", sign, pk.getPublicKey());
			System.out.println(t);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void jsontest() throws Exception{
		Runstate rs=new Runstate("1","ss","123","1","1","main","12:00:00","fdf","fdf","dfdf");
		String text=FastJsonUtil.toJSONString(rs);
		text.getBytes("utf-8");
		System.out.println(text);
		Runstate rw=FastJsonUtil.toBean(text, Runstate.class);
		System.out.println(rw);
	}
	@Test
	public void fjtest(){
		StateBody bsBody=new StateBody();
		bsBody.setId("1");
		bsBody.setIp("123");
		bsBody.setName("fff");
		System.out.println(FastJsonUtil.toJSONString(bsBody));
	}
	/*
	 * 数据库查询测试
	 */
	@Test
	public void querytest(){
		//Runstate rs=new Runstate("1","ss","123","1","1","12:00:00","fdf","fdf","dfdf");
		ManageMessage mm=new ManageMessage();
		//mm.Regist(rs);
		Runstate rw=mm.queryByIp("ss");
		System.out.println(rw);
	}
	/*
	 * 数据库写入测试
	 */
	@Test
	public void inserttest(){
		Runstate rs=new Runstate("1","ss","123","1","1","main","12:00:00","fdf","fdf","dfdf");
		//ManageMessage mm=new ManageMessage();
		System.out.println(rs.toString());
		//mm.Regist(rs);
	}
	@Test
	public void updatetest(){
		Runstate rs=new Runstate("1","ss","123","1","1","main","12:00:00","fdf","fdf","dfdf");
		ManageMessage mm=new ManageMessage();
		System.out.println(rs.toString());
		mm.Update(rs);
	}
	@Test
	public void destroytest(){
		String sql="drop table node";
		DerbyDBUtil.executeInit(sql);
	}
	
}
