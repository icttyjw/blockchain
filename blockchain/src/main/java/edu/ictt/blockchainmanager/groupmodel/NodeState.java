package edu.ictt.blockchainmanager.groupmodel;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
@Entity
@DynamicInsert
@DynamicUpdate
@Table
public class NodeState implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 999611661446387109L;//程序的运行状态

	/**
	 * ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	/**
	 * 自命名节点名
	 */
	private String name;//
	/*
	 * 节点类型
	 * 1.自己  localnode
	 * 2.同校节点 groupnode
	 * 3.其他学校节点 schoolnode
	 */
	private String nodetype;
	/**
	 * 节点IP
	 */
	private String Ip;//
	/**
	 * 运行状态
	 */
	private String state;//
	/**
	 * 通信状态
	 */
	private String connectstate;//
	/**
	 * 最后一次通信时间
	 */
	private String lastConnect;//
	/**
	 * 节点公钥 用新的方法 公钥不用分成两个
	 */
	private String pubKey;
	/*
	 * 节点主次
	 */
	private String main;
	/**
	 * 节点私钥
	 */
	private String priKey;
	
	public NodeState(int id,String name,String Ip,String state,String connectstate,String main,
					String nodeType,String lastConnect,String pubKey,String priKey){
		this.id=id;
		this.name=name;
		this.Ip=Ip;
		this.state=state;
		this.connectstate =connectstate;
		this.main=main;
		this.lastConnect=lastConnect;
		this.nodetype=nodeType;
		this.pubKey=pubKey;
		this.priKey=priKey;
	}
	public NodeState(String name,String Ip,String state,String connectstate,String main,
			String nodeType,String lastConnect,String pubKey,String priKey){
		this.name=name;
		this.Ip=Ip;
		this.state=state;
		this.connectstate =connectstate;
		this.main=main;
		this.lastConnect=lastConnect;
		this.nodetype=nodeType;
		this.pubKey=pubKey;
		this.priKey=priKey;
}
	
	public NodeState(){
		super();
	}
	
	public String getNodetype() {
		return nodetype;
	}

	public void setNodetype(String nodetype) {
		this.nodetype = nodetype;
	}

	public String getMain() {
		return main;
	}

	public void setMain(String main) {
		this.main = main;
	}

	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getIp() {
		return Ip;
	}
	public String getState() {
		return state;
	}
	public String getConnectstate() {
		return connectstate;
	}
	public String getLastConnect() {
		return lastConnect;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setIp(String ip) {
		Ip = ip;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void setConnectstate(String connectstate) {
		this.connectstate = connectstate;
	}
	public void setLastConnect(String lastConnect) {
		this.lastConnect = lastConnect;
	}
	
	public String getPubKey() {
		return pubKey;
	}
	public String getPriKey() {
		return priKey;
	}
	public void setPubKey(String pubKey) {
		this.pubKey = pubKey;
	}
	

	public void setPriKey(String priKey) {
		this.priKey = priKey;
	}
	public void changestate(int i){
		if(i==0)
		{
			this.state="0";
			this.connectstate="0";
		}else
		{
			this.state="1";
			this.connectstate="1";
		}
	}
	@Override
	public String toString() {
		return "NodeState{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", nodetype='" + nodetype + '\'' +
				", Ip='" + Ip + '\'' +
				", state='" + state + '\'' +
				", connectstate='" + connectstate + '\'' +
				", lastConnect='" + lastConnect + '\'' +
				", pubKey='" + pubKey + '\'' +
				", main='" + main + '\'' +
				", priKey='" + priKey + '\'' +
				'}';
	}
}
