package edu.ictt.blockchainmanager.sql.repository;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import edu.ictt.blockchainmanager.groupmodel.BlockInfo;
import edu.ictt.blockchainmanager.groupmodel.NodeState;

@Repository
public interface BlockInfoRepository extends JpaRepository<BlockInfo, String> {
	
	/*
	 * 根据学校姓名查询
	 */
	//@Query(value = "SELECT * FROM block WHERE school_name = ?1 LIMIT 10",nativeQuery=true)
	//(value = "FROM BlockInfo as bi WHERE bi.school_name = ?")
	//public List<BlockInfo> queryBySchoolName(@Param("school_name")String schoolname);
	
	@Query(value = "SELECT * FROM block WHERE school_name = ?1 LIMIT 10",nativeQuery=true)
	//(value = "FROM BlockInfo as bi WHERE bi.school_name = ?")
	public List<BlockInfo> queryFirst10BySchoolName(@Param("school_name")String schoolname);
	/*
	 * 根据学校姓名及学院查询
	 */
	@Query(value = "SELECT * FROM block WHERE school_name = ?1 AND faculty_name = ?2 LIMIT 10",nativeQuery=true)
	public List<BlockInfo> queryFirst10BySchoolNameAndFacultyName(@Param("school_name")String schoolname,@Param("faculty_name")String facultyname);
	/*
	 * 根据学校姓名及学院及课程查询
	 */
	@Query(value = "SELECT * FROM block WHERE school_name = ?1 AND faculty_name = ?2 AND course_name = ?3 LIMIT 1",nativeQuery=true)
	public BlockInfo queryBySchoolNameAndFacultyNameAndCourseName(@Param("school_name")String schoolname,@Param("faculty_name")
																String facultyname,@Param("course_name")String coursename);
}
