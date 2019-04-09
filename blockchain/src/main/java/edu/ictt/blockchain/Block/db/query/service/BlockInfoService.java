package edu.ictt.blockchain.Block.db.query.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ictt.blockchain.Block.db.query.BlockInfo;
import edu.ictt.blockchain.Block.db.query.repository.BlockInfoRepository;

@Service
public class BlockInfoService {
	@Autowired
	private BlockInfoRepository blockInfoRepository;
	
	/*
	 * 保存区块哈希以及对应的相关信息
	 */
	public void saveBlockInfo(BlockInfo blockInfo) {
		blockInfoRepository.save(blockInfo);
	}
	/*
	 * 根据学校姓名查询
	 */
	public List<BlockInfo> queryFirst10BySchoolName(String schoolname) {
		
		return blockInfoRepository.queryFirst10BySchoolName(schoolname);
		
	}
	/*
	 * 根据学校姓名及学院查询
	 */
	public List<BlockInfo> queryFirst10BySchoolNameAndFacultyName(String schoolname,String facultyname) {
		return blockInfoRepository.queryFirst10BySchoolNameAndFacultyName(schoolname, facultyname);
		
	}
	/*
	 * 根据学校姓名及学院及课程查询
	 */
	public BlockInfo queryBySchoolNameAndFacultyNameAndCourseName(String schoolname,String facultyname,String coursename) {
		return blockInfoRepository.queryBySchoolNameAndFacultyNameAndCourseName(schoolname, facultyname, coursename);
		
	}

}
