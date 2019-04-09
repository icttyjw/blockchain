package edu.ictt.blockchain.Block.db.query.simple;

import java.sql.ResultSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ictt.blockchain.Block.db.query.BlockInfo;
import edu.ictt.blockchain.common.util.DerbyDBUtil;
import edu.ictt.blockchain.common.util.SqlDBUtil;

public class ManageBlockMessage {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	/*{
	boolean doesTableExist=false;
	 doesTableExist=SqlDBUtil.doesTableExist("block");
	 if(doesTableExist==false)
	 {
		 String sql="create table block (block_hash MEDIUMTEXT,school_id INT(11),school_name varchar(45),faculty_id INT(11),"
		 		+ "faculty_name varchar(45),course_id INT(11),course_name varchar(45))";
		 SqlDBUtil.executeInit(sql);
	 }
	}*/
	
	/**
	 * 将区块hash和查询的关键字保存到sql
	 */
	public void saveBlockInfo(BlockInfo blockInfo) {
		String sql="insert into block(block_hash,school_id,school_name,faculty_id,faculty_name,course_id,course_name)values('"
				+blockInfo.getBlockHash()
				+"','"
				+blockInfo.getSchoolId()
				+"','"
				+blockInfo.getSchoolName()
				+"','"
				+blockInfo.getFacultyId()
				+"','"
				+blockInfo.getFacultyName()
				+"','"
				+blockInfo.getCourseId()
				+"','"
				+blockInfo.getCourseName()
				+ "')";
			SqlDBUtil.executeUpdate(sql);
	}
	
	/**
	 * 根据输入的关键字查询
	 */
	public BlockInfo queryBySchoolName(String sName) {
		String sql = "select * from block where school_name = '"+sName+"'";
		ResultSet rs = SqlDBUtil.query(sql);
		logger.info("查询结果：" + rs);
		BlockInfo blockInfo = new BlockInfo();
		try {
			if(rs.next()) 
			{
				String blockhash = rs.getString("block_hash");
				blockInfo.setBlockHash(blockhash);
				int schoolIn = rs.getInt("school_id");
				blockInfo.setSchoolId(schoolIn);
				String schoolName = rs.getString("school_name");
				blockInfo.setSchoolName(schoolName);
				int facultyId = rs.getInt("faculty_id");
				blockInfo.setFacultyId(facultyId);
				String facultyName = rs.getString("faculty_name");
				blockInfo.setFacultyName(facultyName);
				int courseId = rs.getInt("course_id");
				blockInfo.setCourseId(courseId);
				String courseName = rs.getString("course_name");
				blockInfo.setCourseName(courseName);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return blockInfo;
		
	}
	
	
}
