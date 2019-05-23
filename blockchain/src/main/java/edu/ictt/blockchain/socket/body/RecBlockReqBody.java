package edu.ictt.blockchain.socket.body;

public class RecBlockReqBody extends BaseBody{

	private String blockhash;
	
	public RecBlockReqBody(){
		super();
	}
	
	public RecBlockReqBody(String hash){
		super();
		this.blockhash=hash;
	}

	public String getBlockhash() {
		return blockhash;
	}

	public void setBlockhash(String blockhash) {
		this.blockhash = blockhash;
	}
	
}
