package edu.ictt.blockchain.socket.body.upperbody;

import edu.ictt.blockchain.Block.block.Block;
import edu.ictt.blockchain.Block.block.UpperBlock;

/**
 * UpperBlock的响应校验的基本body
 * @author zoe
 *
 */
public class URpcCheckBlockBody extends URpcBlockBody{
	 /**
     * 0是正常同意，-1区块number错误，-2没有权限，-3hash错误，-4时间错误，-10不合法的next block
     */
    private int code;
    /**
     * 附带的message
     */
    private String message;
	
	public URpcCheckBlockBody(int code, String message) {
        this(code, message, null);
    }

    public URpcCheckBlockBody(int code, String message, UpperBlock uBlock) {
        super(uBlock);
        this.code = code;
        this.message = message;
    }

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "URpcCheckBlockBody [code=" + code + ", message=" + message + "]";
	}
    
}
