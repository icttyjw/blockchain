package edu.ictt.blockchainmanager.sql.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ictt.blockchainmanager.groupmodel.Node;
import edu.ictt.blockchainmanager.groupmodel.NodeState;
import edu.ictt.blockchainmanager.sql.repository.NodeRepositroy;

@Service
public class NodeService {

	@Autowired
	private NodeRepositroy nodeRepositroy;
	/*
	 * 保存节点信息
	 */
	public void saveLocalNode(NodeState node){ 
		nodeRepositroy.save(node);
	}
	/*
	 * 查询自己的信息
	 */
	public NodeState queryLocalNode(String nodetype){
		return nodeRepositroy.queryLocalNode(nodetype);
	}
	/*
	 * 用于查询其他节点
	 */
	public List<NodeState> queryByNodeType(String nodetype){
		return nodeRepositroy.queryNodebyType(nodetype);
	}
	/*
	 * 查询所有节点信息
	 */
	public List<NodeState> queryAllNodes(){
		return nodeRepositroy.findAll();
	}
	
	/*
	 * 根据姓名查询某个节点
	 */
	public NodeState queryByName(String name) {
		return nodeRepositroy.queryByName(name);
	}
	
	public void deleteByName(String name){
		nodeRepositroy.deleteByName(name);
	}
}
