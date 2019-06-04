package edu.ictt.blockchain.socket.body.upperbody;

import edu.ictt.blockchain.socket.body.common.BaseBody;
import edu.ictt.blockchain.socket.upperpbft.msg.UVoteMsg;

/**
 * UpperBlock的投票的基本body
 * @author zoe
 *
 */
public class UVoteBody extends BaseBody{
	
	private UVoteMsg uVoteMsg;
	
	public UVoteBody() {
		// TODO Auto-generated constructor stub
	}
	
	public UVoteBody(UVoteMsg uVoteMsg) {
		this.uVoteMsg = uVoteMsg;
	}

	public UVoteMsg getuVoteMsg() {
		return uVoteMsg;
	}

	public void setuVoteMsg(UVoteMsg uVoteMsg) {
		this.uVoteMsg = uVoteMsg;
	}

	@Override
	public String toString() {
		return "UVoteBody [uVoteMsg=" + uVoteMsg + "]";
	}
}
