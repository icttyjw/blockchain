package edu.ictt.blockchain.sql.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.ictt.blockchainmanager.NodeState;
import edu.ictt.blockchainmanager.groupmodel.Node;

@Mapper
public interface NodeRepositroy extends JpaRepository<NodeState, String>{
	/*
	 * 查询自己的信息
	 */
	@Query("SELECT * FROM node WHERE nodetype=?1")
	public NodeState queryLocalNode(@Param("nodetype") String nodetype);
	/*
	 * 用于查询其余节点
	 */
	@Query("SELECT * FROM node WHERE nodetype=?1")
	public List<NodeState> queryNodebyType(@Param("nodetype") String nodetype);
	
}
