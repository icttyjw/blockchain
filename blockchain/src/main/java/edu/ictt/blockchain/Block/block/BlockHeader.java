package edu.ictt.blockchain.Block.block;

import java.util.Date;

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
    private long nounce;

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

    /**区块头摘要签名
     *
     */
    private String blockHeaderSign;

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

    public long getNounce() {
        return nounce;
    }

    public void setNounce(long nounce) {
        this.nounce = nounce;
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

    public long getBlockMumber() {
        return blockNumber;
    }

    public void setBlockMumber(long blockNumber) {
        this.blockNumber = blockNumber;
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

    @Override
    public String toString() {
        return "BlockHeader：{" +
                ", hashPreviousBlock='" + hashPreviousBlock +
                ", hashMerkleRoot='" + hashMerkleRoot +
                ", nounce='" + nounce +
                ", difficultGoal=" + difficultGoal +
                ", blockTimeStamp=" + blockTimeStamp +
                ", blockNumber=" + blockNumber +
                ", recordCount=" + recordCount +
                ", blockHeaderSign" + blockHeaderSign +
                '}';
    }


}
