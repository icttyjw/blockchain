package edu.ictt.blockchain.Block.block;

import java.util.Date;
import java.util.List;

/**
 * @Author:zoe
 * @Description:
 * @Date:
 *
 */
public class BlockHeader {

    /**
     *   前一区块哈希
     */

    private String hashPreviousBlock;

    /**
     * Merkle树根节点
     */

    private String hashMerkleRoot;

    /**Nounce值
     *
     */
    private long nonce;

    /**难度值
     *
     */
    private long difficultGoal;

    /**区块时间戳
     *
     */
    private long blockTimeStamp;

    /**块号
     *
     */
    private long blockNumber;

    /**区块中的记录数量
     *
     */
    private int recordCount;

    /**
     * 该区块签名的公钥
     */
    private String publicKey;

    /**区块头摘要签名
     *
     */
    private String blockHeaderSign;

    /**
     * 这个不需要放在区块头。该区块里每条交易信息的hash集合，按顺序来的，通过该hash集合能算出根节点hash
     */
    //private List<String> hashList;

    public BlockHeader(){};

    public BlockHeader(String hashPreviousBlock, String hashMerkleRoot, long nounce,
                       long difficultGoal, long blockTimeStamp, long blockNumber, int recordCount,
                       String blockHeaderSign, List<String> hashList) {
        this.hashPreviousBlock = hashPreviousBlock;
        this.hashMerkleRoot = hashMerkleRoot;
        this.nonce = nounce;
        this.difficultGoal = difficultGoal;
        this.blockTimeStamp = blockTimeStamp;
        this.blockNumber = blockNumber;
        this.recordCount = recordCount;
        this.blockHeaderSign = blockHeaderSign;
        //this.hashList = hashList;
    }

    public String getHashPreviousBlock() {
        return hashPreviousBlock;
    }

    public void setHashPreviousBlock(String hashPreviousBlock) {
        this.hashPreviousBlock = hashPreviousBlock;
    }

    public String getHashMerkleRoot() {
        return hashMerkleRoot;
    }

    public void setHashMerkleRoot(String hashMerkleRoot) {
        this.hashMerkleRoot = hashMerkleRoot;
    }

    public long getNonce() {
        return nonce;
    }

    public void setNonce(long nonce) {
        this.nonce = nonce;
    }

    public long getDifficultGoal() {
        return difficultGoal;
    }

    public void setDifficultGoal(long difficultGoal) {
        this.difficultGoal = difficultGoal;
    }

    public long getBlockTimeStamp() {
        return blockTimeStamp;
    }

    public void setBlockTimeStamp(long blockTimeStamp) {
        this.blockTimeStamp = blockTimeStamp;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public String getBlockHeaderSign() {
        return blockHeaderSign;
    }

    public void setBlockHeaderSign(String blockHeaderSign) {
        this.blockHeaderSign = blockHeaderSign;
    }

    public long getBlockNumber() { return blockNumber; }

    public void setBlockNumber(long blockNumber) { this.blockNumber = blockNumber; }

    //public List<String> getHashList() { return hashList; }

   // public void setHashList(List<String> hashList) { this.hashList = hashList; }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    @Override
    public String toString() {
        return "BlockHeader{" +
                "hashPreviousBlock='" + hashPreviousBlock + '\'' +
                ", hashMerkleRoot='" + hashMerkleRoot + '\'' +
                ", nounce=" + nonce +
                ", difficultGoal=" + difficultGoal +
                ", blockTimeStamp=" + blockTimeStamp +
                ", blockNumber=" + blockNumber +
                ", recordCount=" + recordCount +
                ", publicKey='" + publicKey + '\'' +
                ", blockHeaderSign='" + blockHeaderSign + '\'' +
                //", hashList=" + hashList +
                '}';
    }
}
