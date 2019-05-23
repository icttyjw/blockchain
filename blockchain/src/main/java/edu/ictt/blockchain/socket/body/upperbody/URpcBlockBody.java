package edu.ictt.blockchain.socket.body.upperbody;

import edu.ictt.blockchain.Block.block.UpperBlock;
import edu.ictt.blockchain.socket.body.common.BaseBody;

/**
 * UpperBlock的响应的基本body
 * @author zoe
 *
 */
public class URpcBlockBody extends BaseBody {
	
	private UpperBlock ubBlock;

	
	public URpcBlockBody() {
		super();
	}

	public URpcBlockBody(UpperBlock ubBlock) {
		super();
		this.ubBlock = ubBlock;
	}

	public UpperBlock getUbBlock() {
		return ubBlock;
	}

	public void setUbBlock(UpperBlock ubBlock) {
		this.ubBlock = ubBlock;
	}

	@Override
	public String toString() {
		return "URpcBlockBody [ubBlock=" + ubBlock + "]";
	}
	
}
