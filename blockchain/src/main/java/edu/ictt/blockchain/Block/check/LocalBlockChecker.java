package edu.ictt.blockchain.Block.check;

import edu.ictt.blockchain.Block.block.Block;

/**
 * @Author:zoe
 * @Description: 重新启动时对本地数据库区块校验
 * @Date:
 * @Modified By:
 */
public class LocalBlockChecker implements BlockChecker{

    /**
     * 本地从头恢复整条链,恢复完成后需要从网络获取最新区块信息并请求同步。
     */
    public void recoverFromDisk(){


    }

    @Override
    public int checkNum(Block block) {
        return 0;
    }

    @Override
    public int checkHash(Block block) {
        return 0;
    }

    @Override
    public int checkTime(Block block) {
        return 0;
    }

    @Override
    public int checkSign(Block block) {
        return 0;
    }

    @Override
    public int checkBlock(Block block) {
        return 0;
    }



}
