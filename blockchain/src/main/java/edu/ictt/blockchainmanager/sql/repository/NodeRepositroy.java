package edu.ictt.blockchainmanager.sql.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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
	
}
