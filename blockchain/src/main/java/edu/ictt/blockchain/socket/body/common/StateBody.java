package edu.ictt.blockchain.socket.body.common;

public class StateBody extends BaseBody {


	private String id;
	/**
	 * 自命名节点名
	 */
	private String name;//
	/**
	 * 节点IP
	 */
	private String Ip;//
	
	public StateBody(){
		
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
	public void setId(String id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setIp(String ip) {
		Ip = ip;
	}
}
