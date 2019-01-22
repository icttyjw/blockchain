package edu.ictt.blockchain.bean;

import java.io.Serializable;

public class Block implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6457204987118872125L;

	
	private String hash;


	public String getHash() {
		return hash;
	}


	public void setHash(String hash) {
		this.hash = hash;
	}
	
	
}
