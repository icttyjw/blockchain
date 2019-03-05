package test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import edu.ictt.blockchainmanager.NodeState;
import org.junit.Test;
import org.tio.utils.json.Json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import cn.hutool.core.collection.CollectionUtil;
import edu.ictt.blockchain.socket.pbft.msg.VoteMsg;

import edu.ictt.blockchain.Block.block.Block;
import edu.ictt.blockchain.Block.block.BlockHeader;
import edu.ictt.blockchain.Block.record.Record;
import edu.ictt.blockchainmanager.NodeState;
import edu.ictt.blockchain.common.CommonUtil;
import edu.ictt.blockchain.common.Const;
import edu.ictt.blockchain.common.FastJsonUtil;
import edu.ictt.blockchain.common.PairKey;
import edu.ictt.blockchain.common.SHA256;
import edu.ictt.blockchain.common.algorithm.ECDSAAlgorithm;
import edu.ictt.blockchain.common.util.DerbyDBUtil;
import edu.ictt.blockchain.core.manager.ManageMessage;
import edu.ictt.blockchain.socket.body.BaseBody;
import edu.ictt.blockchain.socket.body.RpcBlockBody;
import edu.ictt.blockchain.socket.body.StateBody;
import edu.ictt.blockchain.socket.packet.BlockPacket;
import edu.ictt.blockchain.socket.packet.PacketBuilder;
import edu.ictt.blockchain.socket.packet.PacketType;
import edu.ictt.blockchain.socket.pbft.VoteType;
import edu.ictt.blockchain.socket.pbft.msg.VoteMsg;
import edu.ictt.blockchain.socket.pbft.msg.VotePreMsg;
import edu.ictt.blockchain.socket.pbft.queue.BaseMsgQueue;
import edu.ictt.blockchain.socket.pbft.queue.CommitMsgQueue;
import edu.ictt.blockchain.socket.pbft.queue.PreMsgQueue;
import edu.ictt.blockchain.socket.pbft.queue.PrepareMsgQueue;

import static edu.ictt.blockchain.socket.pbft.Message.blockConcurrentHashMap;
import static edu.ictt.blockchain.socket.pbft.Message.findByHash;



public class PartTest {

	@Test
	public void hash(){
		ConcurrentHashMap<String,  Integer> csi=new ConcurrentHashMap<String, Integer>();
		if(csi.contains("111"))
			System.out.println(1);
	}
	
	@Test
	public void msgtest() throws UnsupportedEncodingException{
		BlockHeader blockHeader=new BlockHeader();
		blockHeader.setBlockTimeStamp(CommonUtil.getNow());
		Block block=new Block();
		block.setBlockHeader(blockHeader);
		block.setBlockHash(SHA256.sha256(blockHeader.toString()));
		System.out.println("1+"+block);
		RpcBlockBody rpcBlockBody=new RpcBlockBody(block);
		System.out.println("2+"+rpcBlockBody);
		String jsonStr;
			jsonStr = JSON.toJSONString(rpcBlockBody);
			System.out.println(jsonStr);
		BaseBody bsBody = null;
		bsBody = FastJsonUtil.toBean(jsonStr, RpcBlockBody.class);
		System.out.println(bsBody);
		bsBody=JSON.parseObject(jsonStr,new TypeReference<RpcBlockBody>(){});
		System.out.println(bsBody);
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
		NodeState rs=new NodeState("1","ss","123","1","1","main","12:00:00","fdf","fdf");
		String text=FastJsonUtil.toJSONString(rs);
		text.getBytes("utf-8");
		System.out.println(text);
		NodeState rw=FastJsonUtil.toBean(text, NodeState.class);
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
		NodeState rw=mm.queryByIp("ss");
		System.out.println(rw);
	}
	/*
	 * 数据库写入测试
	 */
	@Test
	public void inserttest(){
		NodeState rs=new NodeState("1","ss","123","1","1","main","12:00:00","fdf","fdf");
		//ManageMessage mm=new ManageMessage();
		System.out.println(rs.toString());
		//mm.Regist(rs);
	}
	@Test
	public void updatetest(){
		NodeState rs=new NodeState("1","ss","123","1","1","main","12:00:00","fdf","fdf");
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
