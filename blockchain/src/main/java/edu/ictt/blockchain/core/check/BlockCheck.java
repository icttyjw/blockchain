package edu.ictt.blockchain.core.check;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import edu.ictt.blockchain.Block.block.Block;
import edu.ictt.blockchain.Block.check.CheckerManager;
import edu.ictt.blockchain.core.manager.DbBlockManager;
import edu.ictt.blockchain.socket.body.lowerbody.RecBlockReqBody;
import edu.ictt.blockchain.socket.client.PacketSender;
import edu.ictt.blockchain.socket.packet.BlockPacket;
import edu.ictt.blockchain.socket.packet.PacketBuilder;
import edu.ictt.blockchain.socket.packet.PacketType;

@Component
public class BlockCheck {

	//当前总区块数
	public  long blockcount=1;
	//当前需要校验的区块号
	public  long blocknum=1;
	@Resource
	private CheckerManager checkerManager;
	@Resource 
	private DbBlockManager dbBlockManager;
	@Resource
	private PacketSender packetSender;
	
	private ConcurrentHashMap<Long, String> hashlist=new ConcurrentHashMap<Long, String>();
	
	
	//@Scheduled(cron="0 0 2 * * ? *")
	public void LongtermBlocCheck(){
		Block block;
		String blockhash;
		if(hashlist.isEmpty()){
			block=dbBlockManager.getLastBlock();
			blockhash=block.getBlockHash();
			blockcount=block.getBlockHeader().getBlockNumber();
			blocknum=blockcount;
		}else
		{
			blockhash=hashlist.get(blocknum);
			block=dbBlockManager.getBlockByHash(blockhash);
		}
		for(int i=0;i<1000;i++){
			int res=checkerManager.periodcheck(block);
			if(res==0){
				hashlist.put(block.getBlockHeader().getBlockNumber(), blockhash);
				blockhash=block.getBlockHeader().getHashPreviousBlock();
				if(blockhash.equals(null))
				{
					block=dbBlockManager.getLastBlock();
					blockhash=block.getBlockHash();
					blockcount=block.getBlockHeader().getBlockNumber();
					blocknum=blockcount;
				}else{
				block=dbBlockManager.getBlockByHash(blockhash);
				blockhash=block.getBlockHash();
				blocknum=block.getBlockHeader().getBlockNumber();
				}
				if(!hashlist.contains(blockhash))
				hashlist.put(blocknum, blockhash);
			}
			//通讯恢复区块
			else{
				RecBlockReqBody body=new RecBlockReqBody(blockhash);
				BlockPacket blockPacket=new PacketBuilder<>().setType(PacketType.BLOCK_RECOVER_REQUEST).setBody(body).build();
				packetSender.sendGroup(blockPacket);
			}
			
			
		}
	}
	//@Scheduled(cron="0 0,28 * * * ? *")
	public void shorttermBlockCheck(){
		 long randomblock=randomlong(blockcount);
		 Block block;
			String blockhash;
			int count=10;
		 if(hashlist.isEmpty()){
				block=dbBlockManager.getLastBlock();
				blockhash=block.getBlockHash();
				blockcount=block.getBlockHeader().getBlockNumber();
				blocknum=blockcount;
			}else
			{
				blockhash=hashlist.get(blocknum);
				block=dbBlockManager.getBlockByHash(blockhash);
			}
		 while(count!=0){
			 while(!hashlist.containsKey(randomblock))
			 {
			 randomblock=randomlong(blockcount);
			 count--;
			 }
			 blockhash=hashlist.get(randomblock);
			 block=dbBlockManager.getBlockByHash(blockhash);
			 int res=checkerManager.periodcheck(block);
				if(res==0){
					count--;
				}else
				{
					RecBlockReqBody body=new RecBlockReqBody(blockhash);
					BlockPacket blockPacket=new PacketBuilder<>().setType(PacketType.BLOCK_RECOVER_REQUEST).setBody(body).build();
					packetSender.sendGroup(blockPacket);
					count--;
				}
		 }
		 
	}
	private long randomlong(long max){
		long min=0;
		
		return RandomUtils.nextLong(min, max);
	}
}
