package edu.ictt.blockchain.socket.upperpbft.msg;

import edu.ictt.blockchain.Block.block.UpperBlock;

//共识投票前的预处理消息，需要将upperblock包含在内进行一些查重等预处理
public class UVotePreMsg extends UVoteMsg{
	
	private UpperBlock uBlock;

	public UpperBlock getuBlock() {
		return uBlock;
	}

	public void setuBlock(UpperBlock uBlock) {
		this.uBlock = uBlock;
	}

	@Override
	public String toString() {
		return "UVotePreMsg [uBlock=" + uBlock + "]";
	}
	
}
