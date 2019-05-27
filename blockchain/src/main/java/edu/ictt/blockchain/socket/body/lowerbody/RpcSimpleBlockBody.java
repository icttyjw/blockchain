package edu.ictt.blockchain.socket.body.lowerbody;

import edu.ictt.blockchain.socket.body.common.BaseBody;

public class RpcSimpleBlockBody extends BaseBody{

	/**
     * blockHash
     */
    private String hash;

    public RpcSimpleBlockBody() {
        super();
    }

    public RpcSimpleBlockBody(String hash) {
        super();
        this.hash = hash;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public String toString() {
        return "RpcSimpleBlockBody{" +
                "hash='" + hash + '\'' +
                '}';
    }
}
