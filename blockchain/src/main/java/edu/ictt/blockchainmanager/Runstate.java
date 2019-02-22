package edu.ictt.blockchainmanager;

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
	private String pubKeyX;
	private String pubKeyY;
	/*
	 * 节点主次
	 */
	private String main;
	/**
	 * 节点私钥
	 */
	private String priKey;
	
	public Runstate(String id,String name,String Ip,String state,String connectstate,String main,
					String lastConnect,String pubKeyX,String pubKeyY,String priKey){
		this.id=id;
		this.name=name;
		this.Ip=Ip;
		this.state=state;
		this.connectstate =connectstate;
		this.main=main;
		this.lastConnect=lastConnect;
		this.pubKeyX=pubKeyX;
		this.pubKeyY=pubKeyY;
		this.priKey=priKey;
	}
	
	public Runstate(){
		super();
	}
	
	public String getMain() {
		return main;
	}

	public void setMain(String main) {
		this.main = main;
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
	
	public String getPubKeyX() {
		return pubKeyX;
	}
	public String getPriKey() {
		return priKey;
	}
	public void setPubKeyX(String pubKeyX) {
		this.pubKeyX = pubKeyX;
	}
	public String getPubKeyY() {
		return pubKeyY;
	}

	public void setPubKeyY(String pubKeyY) {
		this.pubKeyY = pubKeyY;
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
				"main："+main+"\n"+
				"connectstate:"+connectstate+"\n"+
				"lastConnect:"+lastConnect+"\n"+
				"pubKeyX:"+pubKeyX+"\n"+
				"pubKeyY:"+pubKeyY;
	}
	
}
