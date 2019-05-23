package edu.ictt.blockchain.core.requestbody;

import edu.ictt.blockchain.Block.block.Block;
import edu.ictt.blockchain.Block.block.UpperBlockBody;

/**
 * 封装uBlockBody
 * @author zoe
 *
 */
public class UpperBlockRequestBody {
	
	private UpperBlockBody uBlockBody;

	public UpperBlockRequestBody() {
		super();
	}

	public UpperBlockRequestBody(UpperBlockBody uBlockBody) {
		super();
		this.uBlockBody = uBlockBody;
	}

	public UpperBlockBody getuBlockBody() {
		return uBlockBody;
	}

	public void setuBlockBody(UpperBlockBody uBlockBody) {
		this.uBlockBody = uBlockBody;
	}

	@Override
	public String toString() {
		return "UpperBlockRequestBody [uBlockBody=" + uBlockBody + "]";
	}
	
}
