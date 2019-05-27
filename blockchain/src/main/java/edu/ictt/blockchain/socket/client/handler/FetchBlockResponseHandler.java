package edu.ictt.blockchain.socket.client.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tio.utils.json.Json;

import org.tio.core.ChannelContext;

import edu.ictt.blockchain.ApplicationContextProvider;
import edu.ictt.blockchain.Block.check.CheckerManager;
//import edu.ictt.blockchain.Block.db.query.BlockInfo;
//import edu.ictt.blockchain.Block.db.query.service.BlockInfoService;
import edu.ictt.blockchain.Block.record.DegreeRecord;
import edu.ictt.blockchain.Block.record.GradeRecord;
import edu.ictt.blockchain.Block.record.Record;
import edu.ictt.blockchain.Block.block.Block;
import edu.ictt.blockchain.core.event.AddBlockEvent;
import edu.ictt.blockchain.socket.body.lowerbody.RpcBlockBody;
import edu.ictt.blockchain.socket.body.lowerbody.RpcCheckBlockBody;
import edu.ictt.blockchain.socket.client.PacketSender;
import edu.ictt.blockchain.socket.common.intf.AbstractBlockHandler;
import edu.ictt.blockchain.socket.packet.BlockPacket;
import edu.ictt.blockchain.socket.packet.NextBlockPacketBuilder;
import edu.ictt.blockchain.socket.pbft.queue.NextBlockQueue;


public class FetchBlockResponseHandler extends AbstractBlockHandler<RpcBlockBody>{

	private Logger logger = LoggerFactory.getLogger(TotalBlockInfoResponseHandler.class);
	
	//@Autowired
	//BlockInfoService blockInfoService;

    @Override
    public Class<RpcBlockBody> bodyClass() {
        return RpcBlockBody.class;
    }

    @Override
    public Object handler(BlockPacket packet, RpcBlockBody rpcBlockBody, ChannelContext channelContext) {
        logger.info("[通信]：收到来自于<" + rpcBlockBody.getAppId() + ">的回复，Block为：" + Json.toJson(rpcBlockBody));

        Block block = rpcBlockBody.getBlock();
        //如果为null，说明对方也没有该Block
        if (block == null) {
            logger.info("[通信]：对方也没有该Block");
        } else {
            //此处校验传过来的block的合法性，如果合法，则更新到本地，作为next区块
        	if(ApplicationContextProvider.getBean(NextBlockQueue.class).pop(block.getBlockHash()) == null) return null;
            CheckerManager checkerManager = ApplicationContextProvider.getBean(CheckerManager.class);
            RpcCheckBlockBody rpcCheckBlockBody = checkerManager.check(block);
            //校验通过，则存入本地DB，保存新区块
            if (rpcCheckBlockBody.getCode() == 0) {
                ApplicationContextProvider.publishEvent(new AddBlockEvent(block));
              //区块存入rocks的同时，区块哈希与相关查询关键字的映射关系需要保存到本地的sql数据库中
            	//saveBlockInfoSQL(block);
                //继续请求下一块
                BlockPacket blockPacket = NextBlockPacketBuilder.build();
                ApplicationContextProvider.getBean(PacketSender.class).sendGroup(blockPacket);
            }
        }

        return null;
    }
	
    //区块存入rocks的同时，区块哈希与相关查询关键字的映射关系需要保存到本地的sql数据库中
   /* public void saveBlockInfoSQL(Block block) {
    	//从block中取出相关需要存放的信息，其中具体信息比如学校学院等信息取一条记录中的就ok
    	String blockHash = block.getBlockHash();
    	if(block.getBlockBody().getGrecordsList()!=null) {
    		GradeRecord gRecord = block.getBlockBody().getGrecordsList().get(0);
    		int schoolId = gRecord.getSchoolInfo().getSchoolId();
    		String schoolName = gRecord.getSchoolInfo().getSchoolName();
    		int facultyId = gRecord.getFacultyInfo().getFacultyId();
    		String facultyName = gRecord.getFacultyInfo().getFacultyName();
    		int courseId = gRecord.getGradeInfo().getCourseInfo().getCourseId();
    		String courseName = gRecord.getGradeInfo().getCourseInfo().getCourseName();
    		BlockInfo blockInfo = new BlockInfo(blockHash,schoolId,schoolName,facultyId,facultyName,courseId,courseName);
    		blockInfoService.saveBlockInfo(blockInfo);
    		logger.info("[落地]：区块已成功落地，可以进行查询");
    	}else if(block.getBlockBody().getDrecordsList()!=null) {
    		//学位记录部分存在问题。记录的定义需要有较大的改动。
    		/*DegreeRecord dRecord = block.getBlockBody().getDrecordsList().get(0);
    		int schoolId = dRecord.getSchoolInfo().getSchoolId();
    		String schoolName = dRecord.getSchoolInfo().getSchoolName();
    		int facultyId = 0;
    		String facultyName = null; 
    		int courseId = (int) dRecord.getDegreeId();
    		String courseName = dRecord.getDegree();
    		BlockInfo blockInfo = new BlockInfo(blockHash,schoolId,schoolName,facultyId,facultyName,courseId,courseName);*/
    /*	}else {
    		logger.info("[落地]：区块体为空，没有记录");
    	}
    	
    	
    }*/
}
