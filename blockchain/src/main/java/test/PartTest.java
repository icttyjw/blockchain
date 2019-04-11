package test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import edu.ictt.blockchain.Block.block.Block;
import edu.ictt.blockchain.Block.block.BlockBody;
import edu.ictt.blockchain.Block.block.BlockHeader;
import edu.ictt.blockchain.Block.check.DbBlockChecker;
import edu.ictt.blockchain.Block.check.GRecordChecker;
import edu.ictt.blockchain.Block.db.ConnectRocksDB;
import edu.ictt.blockchain.Block.db.DbInitConfig;
import edu.ictt.blockchain.Block.db.RecoverLocalRecord;
import edu.ictt.blockchain.Block.db.RocksDbStoreImpl;
import edu.ictt.blockchain.Block.generatorUtil.GenerateBlock;
import edu.ictt.blockchain.Block.generatorUtil.GenerateRecord;
import edu.ictt.blockchain.Block.me.MerkleTree;
import edu.ictt.blockchain.Block.merkle.MerkleHash;
import edu.ictt.blockchain.Block.merkle.MerkleNode;
import edu.ictt.blockchain.Block.record.*;
import edu.ictt.blockchain.common.CommonUtil;
import edu.ictt.blockchain.common.Const;
import edu.ictt.blockchain.common.FastJsonUtil;
import edu.ictt.blockchain.common.PairKey;
import edu.ictt.blockchain.common.SHA256;
import edu.ictt.blockchain.common.algorithm.ECDSAAlgorithm;
import edu.ictt.blockchain.common.util.DerbyDBUtil;
import edu.ictt.blockchain.core.manager.DbBlockManager;
import edu.ictt.blockchain.core.manager.ManageMessage;
import edu.ictt.blockchain.socket.body.BaseBody;
import edu.ictt.blockchain.socket.body.RecordBody;
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
import edu.ictt.blockchain.socket.record.queue.RecordQueue;
import edu.ictt.blockchainmanager.groupmodel.NodeState;

import org.junit.Test;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.tio.utils.json.Json;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;



public class PartTest {

	@Test
	public void newmerkle(){
		Block block=GenerateBlock.generateBlock(1);
		DbBlockChecker blockChecker=new DbBlockChecker();
		System.out.println(blockChecker.checkBlock(block));
	}
	
	@Test
	public void merkle(){
		GradeRecord r1=GenerateRecord.geneGRecord();
		GradeRecord r2=GenerateRecord.geneGRecord();
		List<String> lgr=new ArrayList<String>();
		lgr.add(r1.toString());
		lgr.add(r2.toString());
		String root1=new MerkleTree(lgr).build().getRoot();
		List<MerkleNode> merkleNodes = new ArrayList<>();
		for(String str:lgr){
			MerkleNode merkleNode = new MerkleNode(MerkleHash.create(str));
			merkleNodes.add(merkleNode);
		}
		 edu.ictt.blockchain.Block.merkle.MerkleTree merkleTree = new edu.ictt.blockchain.Block.merkle.MerkleTree();
		merkleTree.buildTree(merkleNodes);
        System.out.println(root1);
        System.out.println("校验时重新生成的MerkleRoot: " + merkleTree.getRoot().getHash().toString());
	}
	
	
	@Test
	public void rjson(){
		String s="";
	}
	
	@Test
	public void json() throws UnsupportedEncodingException{
		Block block=GenerateBlock.generateBlock(2);
		String b=FastJsonUtil.toJSONString(block);
		byte[] bb=b.getBytes(Const.CHARSET);
		String bbb=new String(bb,Const.CHARSET);
		Block nb=FastJsonUtil.toBean(bbb, new TypeReference<Block>(){});
		System.out.println(bbb);
		System.out.println(nb);
	}
	
	@Test
	public void stringtest() throws UnsupportedEncodingException{
		GradeRecord r=GenerateRecord.geneGRecord();
		RecordBody  rb=new RecordBody(r, null);
		String rjson=FastJsonUtil.toJSONString(rb);
		
		BlockPacket blockPacket = new PacketBuilder<>().setType(PacketType.HEART_BEAT).setBody(rb).build();
		String rbjs=new String(blockPacket.getBody(),Const.CHARSET);
		RecordBody nrb=FastJsonUtil.toBean(rbjs, RecordBody.class);
		System.out.println(rbjs);
		System.out.println(nrb);
	}
	
	/*
	 RecordQueue写记录测试
	 */
	@Test
	public void recordQueueTest(){

		RecordQueue recordQueue = new RecordQueue();
		DbBlockManager dbBlockManager = new DbBlockManager();
		List<RecordBody> recordBodyList = new ArrayList<>();

		for(int i=0; i<2; i++){
			GradeRecord record = GenerateRecord.geneGRecord();
			System.out.println("记录" + i + record);
			RecordBody recordBody = new RecordBody(record,
					SHA256.sha256(String.valueOf(record.getGradeInfo().getCourseInfo().getCourseId())));
			recordBody.setCount(2);
			recordBodyList.add(recordBody);
		}

		//连到数据库
		try {
			ConnectRocksDB connectRocksDB = new ConnectRocksDB(3);
			dbBlockManager.setDbStore(connectRocksDB.getRocksDbStore());
			recordQueue.setDbBlockManager(dbBlockManager);
		} catch (RocksDBException e) {
			e.printStackTrace();
		}

		for(RecordBody recordBody:recordBodyList){
			recordQueue.receive(recordBody);
		}

	}
	
	@Test
	public void checktest(){
		//GenerateRecord generateRecord=new GenerateRecord();
		GRecordChecker checker=new GRecordChecker();
		System.out.println(checker.checkRecord(GenerateRecord.geneGRecord()));//(GenerateRecord.geneGRecord());//(checker.checkRecord(GenerateRecord.geneGRecord()));
	}

	/**
	 * 读记录(读的时候会校验记录
	 */
	@Test
	public void readrecordtest(){
		try {
			RecoverLocalRecord recoverLocalRecord = new RecoverLocalRecord();
			recoverLocalRecord.recoverRecord();
			
		} catch (RocksDBException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void deleterecord() throws Exception{
		ConnectRocksDB rocksDB= new ConnectRocksDB(3);
		  List<String> courseList;
		  ConcurrentHashMap<String, List<GradeRecord>> recordConcurrentHashMap=new ConcurrentHashMap<String, List<GradeRecord>>();
		  
	}

	/**
	 * 将区块写入rocksDB测试
	 */
	@Test
	public void saveblocktorockstest() throws RocksDBException {

		//创建一个数据库操作对象，并与数据库建立连接

        ConnectRocksDB rocksDB = new ConnectRocksDB(1);

		//写入10个区块
		for(int i=0; i<100; i++){
			BlockHeader blockHeader = new BlockHeader();
			blockHeader.setBlockTimeStamp(CommonUtil.getNow());
			blockHeader.setBlockNumber(i);

			Block block = new Block();
			block.setBlockHeader(blockHeader);
			block.setBlockHash(SHA256.sha256(blockHeader.toString()));

			//将区块转换为json格式存到rocksdb
			String jsonStr = JSON.toJSONString(block);
			rocksDB.getRocksDbStore().put("i", jsonStr);
			System.out.println(jsonStr);
			System.out.println("save this block success");
		}
	}

	/**
	 * 从本地读区块 + 校验区块
	 */
	@Test
	public void readblocktest() throws RocksDBException {
		ConnectRocksDB rocksDB = new ConnectRocksDB(1);

		for(int i=0; i<10; i++){
			System.out.println("read block" + i + " : " +
					JSON.parseObject(rocksDB.getRocksDbStore().get("i"), new TypeReference<Block>(){}));
		}
	}

	/**
	 * 校验测试
	 * 使用本地区块对新区块校验
	 */
	@Test
	public void blockcheckertest() throws RocksDBException {

		//新区块
		BlockHeader blockHeader = new BlockHeader();
		blockHeader.setBlockTimeStamp(CommonUtil.getNow());
		blockHeader.setBlockNumber(2);
		Block newBlock = new Block();
		newBlock.setBlockHeader(blockHeader);


		ConnectRocksDB rocksDB = new ConnectRocksDB('1');


		DbBlockChecker dbBlockChecker = new DbBlockChecker();
		System.out.println("当前区块校验结果"+ dbBlockChecker.checkAll(JSON.parseObject(rocksDB.getRocksDbStore().get("i"), new TypeReference<Block>(){})));
	}


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
		NodeState rs=new NodeState("1","ss","123","1","1","main","localnode","12:00:00","fdf","fdf");
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
		NodeState rs=new NodeState("1","ss","123","1","1","main","localnode","12:00:00","fdf","fdf");
		ManageMessage mm=new ManageMessage();
		System.out.println(rs.toString());
		mm.Regist(rs);
	}
	@Test
	public void updatetest(){
		NodeState rs=new NodeState("1","ss","123","1","1","main","localnode","12:00:00","fdf","fdf");
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
