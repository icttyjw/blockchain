package edu.ictt.blockchainmanager.sql.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.ictt.blockchainmanager.groupmodel.NodeState;
@Repository
public interface NodeRepositroy extends JpaRepository<NodeState, String>{
	/*
	 * 查询自己的信息
	 */
	@Query(value="SELECT * FROM node_state WHERE nodetype=?1",nativeQuery=true)
	public NodeState queryLocalNode(@Param("nodetype") String nodetype);
	/*
	 * 用于查询其余节点
	 */
	@Query(value="SELECT * FROM node_state WHERE nodetype=?1",nativeQuery=true)
	public List<NodeState> queryNodebyType(@Param("nodetype") String nodetype);
	
	/*
	 *根据名字查询节点所有信息 
	 */
	@Query(value="SELECT * FROM node_state WHERE name=?1",nativeQuery=true)
	public NodeState queryByName(@Param("name") String name);
	
	@Transactional
	@Modifying
	@Query(value="DELETE FROM node_state WHERE name=?1",nativeQuery=true)
	public void deleteByName(@Param("name") String name);
	
}
