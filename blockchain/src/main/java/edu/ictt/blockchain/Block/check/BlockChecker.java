package edu.ictt.blockchain.Block.check;

import edu.ictt.blockchain.Block.block.Block;

/**
 * @Author:
 * @Description: 区块校验，暂时未考虑权限
 * @Date:
 */
public interface BlockChecker {
    /**
    * 比较目标区块和自己本地的区块num大小
    * @param block
    * 被比较的区块
    * @return
    * 本地与目标区块的差值
    */
    int checkNum(Block block);

    /**
     * 校验prevHash
     * @param block
     * block
     * @return
     * 大于0合法
     */
    int checkHash(Block block);

    /**
     * 校验生成时间
     * @param block  block
     * @return block
     */
    int checkTime(Block block);

    /**
     * 校验摘要头签名
     * @param block  block
     * @return block
     */
    int checkSign(Block block);

    /**
     * 校验block，包括签名、hash、关联关系
     * @param  block
     * @return
     */
    int checkMerkleRoot(Block block);


    /**
     * 校验区块内操作的权限是否合法
     * @param block
     * block
     * @return
     * 大于0合法
     */
    //int checkPermission(Block block);

}
