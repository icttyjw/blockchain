package edu.ictt.blockchain.socket.body;

import edu.ictt.blockchain.socket.pbft.msg.VoteMsg;

public class VoteBody extends BaseBody{

	 private VoteMsg voteMsg;

	    public VoteBody() {
	        super();
	    }

	    public VoteBody(VoteMsg voteMsg) {
	        super();
	        this.voteMsg = voteMsg;
	    }

	    public VoteMsg getVoteMsg() {
	        return voteMsg;
	    }

	    public void setVoteMsg(VoteMsg voteMsg) {
	        this.voteMsg = voteMsg;
	    }
}
