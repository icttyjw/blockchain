package edu.ictt.blockchain.socket.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlockSessionContext {

	private static Logger log = LoggerFactory.getLogger(BlockSessionContext.class);

	/**
	 * @param args
	 * @author tanyaowu
	 */
	public static void main(String[] args) {

	}

	private String token = null;
	private String group;
	private String userid = null;

	/**
	 *
	 * @author tanyaowu
	 */
	public BlockSessionContext() {
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @return the userid
	 */
	public String getUserid() {
		return userid;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @param userid the userid to set
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}
}
