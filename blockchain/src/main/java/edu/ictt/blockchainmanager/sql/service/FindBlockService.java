package edu.ictt.blockchainmanager.sql.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import edu.ictt.blockchain.Block.block.Block;
import edu.ictt.blockchain.Block.db.RocksDbStoreImpl;
import edu.ictt.blockchain.Block.db.query.BlockInfo;

@Service
public class FindBlockService {
	@Autowired
	BlockInfoService blockInfoService;
	@Autowired
	RocksDbStoreImpl rocksDbStoreImpl;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public Block findBlockByBI(BlockInfo blockInfo) {
		Block block;
		block = JSON.parseObject(rocksDbStoreImpl.get(blockInfo.getBlockHash()), new TypeReference<Block>(){});
		logger.info("根据输入的学校找到的块" + block.toString());
		return block;
	}
	/*
	 * 根据学校姓名查询
	 */
	public List<Block> queryFirst10BySchoolName(String schoolname) {
		
		List<BlockInfo> blockInfos= blockInfoService.queryFirst10BySchoolName(schoolname);
		List<Block> blocks = new ArrayList<>();
		
		for(BlockInfo blockInfo:blockInfos) {
			blocks.add(findBlockByBI(blockInfo));
		}
		return blocks;
		
	}
	/*
	 * 根据学校姓名及学院查询
	 */
	public List<Block> queryFirst10BySchoolNameAndFacultyName(String schoolname,String facultyname) {
		List<BlockInfo> blockInfos = blockInfoService.queryFirst10BySchoolNameAndFacultyName(schoolname, facultyname);
		List<Block> blocks = new ArrayList<>();
		for(BlockInfo blockInfo:blockInfos) {
			blocks.add(findBlockByBI(blockInfo));
		}
		return blocks;
		
	}
	/*
	 * 根据学校姓名及学院及课程查询
	 */
	public Block queryBySchoolNameAndFacultyNameAndCourseName(String schoolname,String facultyname,String coursename) {
		BlockInfo blockInfo = blockInfoService.queryBySchoolNameAndFacultyNameAndCourseName(schoolname, facultyname, coursename);
		return findBlockByBI(blockInfo);
	}
}
