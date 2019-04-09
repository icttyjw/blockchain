package edu.ictt.blockchain.Block.db.query.repository;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.ictt.blockchain.Block.db.query.BlockInfo;
import edu.ictt.blockchainmanager.groupmodel.NodeState;

@Repository
public interface BlockInfoRepository extends JpaRepository<BlockInfo, String> {
	
	/*
	 * 根据学校姓名查询
	 */
	@Query(value = "SELECT top 10 * FROM block WHERE school_name = ?1",nativeQuery=true)
	public List<BlockInfo> queryFirst10BySchoolName(@Param("school_name")String schoolname);
	/*
	 * 根据学校姓名及学院查询
	 */
	@Query(value = "SELECT top 10 * FROM block WHERE school_name = ?1 AND faculty_name = ?1")
	public List<BlockInfo> queryFirst10BySchoolNameAndFacultyName(@Param("school_name")String schoolname,@Param("faculty_name")String facultyname);
	/*
	 * 根据学校姓名及学院及课程查询
	 */
	@Query(value = "SELECT * FROM block WHERE school_name = ?1 AND faculty_name = ?1 AND course_name = ?1")
	public BlockInfo queryBySchoolNameAndFacultyNameAndCourseName(@Param("school_name")String schoolname,@Param("faculty_name")String facultyname,@Param("course_name")String coursename);
}
