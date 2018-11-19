package edu.ictt.blockchain.bean;

import java.io.Serializable;

public class Runstate implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 999611661446387109L;//程序的运行状态

	/**
	 * 自命名ID
	 */
	private String id;
	/**
	 * 自命名节点名
	 */
	private String name;//
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
	 * 节点公钥
	 */
	private String pubKey;
	/**
	 * 节点私钥
	 */
	private String priKey;
	
	public Runstate(String id,String name,String Ip,String state,String connectstate,
					String lastConnect,String pubKey,String priKey){
		this.id=id;
		this.name=name;
		this.Ip=Ip;
		this.state=state;
		this.connectstate =connectstate;
		this.lastConnect=lastConnect;
		this.pubKey=pubKey;
		this.priKey=priKey;
	}
	
	public String getId() {
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
	public void setId(String id) {
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
	@Override
	public String toString(){
		return "id:"+id+"\n"+
				"name:"+name+"\n"+
				"Ip:"+Ip+"\n"+
				"state："+state+"\n"+
				"connectstate:"+connectstate+"\n"+
				"lastConnect:"+lastConnect+"\n"+
				"pubKey:"+pubKey;
	}
	
}
