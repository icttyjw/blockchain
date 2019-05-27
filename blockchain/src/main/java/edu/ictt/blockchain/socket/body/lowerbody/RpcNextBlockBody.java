package edu.ictt.blockchain.socket.body.lowerbody;

import edu.ictt.blockchain.socket.body.common.BaseBody;

public class RpcNextBlockBody extends BaseBody{

	/**
     * blockHash
     */
    private String hash;
    /**
     * 上一个hash
     */
    private String prevHash;

    public RpcNextBlockBody() {
        super();
    }

    public RpcNextBlockBody(String hash, String prevHash) {
        super();
        this.hash = hash;
        this.prevHash = prevHash;
    }

    public String getPrevHash() {
        return prevHash;
    }

    public void setPrevHash(String prevHash) {
        this.prevHash = prevHash;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public String toString() {
        return "RpcNextBlockBody{" +
                "hash='" + hash + '\'' +
                ", prevHash='" + prevHash + '\'' +
                '}';
    }
}
