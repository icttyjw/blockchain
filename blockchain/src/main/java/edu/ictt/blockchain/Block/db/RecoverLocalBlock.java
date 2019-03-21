package edu.ictt.blockchain.Block.db;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import edu.ictt.blockchain.Block.block.Block;
import edu.ictt.blockchain.Block.check.DbBlockChecker;
import edu.ictt.blockchain.Block.db.ConnectRocksDB;
import org.rocksdb.RocksDBException;
import org.rocksdb.RocksIterator;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @Author:zoe
 * @Description: 重新启动时对本地数据库区块校验
 * @Date:
 * @Modified By:
 */

@Component
public class RecoverLocalBlock extends DbBlockChecker {

    /**
     * 本地从头恢复整条链,恢复时可同时向网络请求获取最新区块信息
     */
    public void recoverFromDisk() throws RocksDBException {

        ConnectRocksDB rocksDB = new ConnectRocksDB('1');

        //如果本地区块文件为空，远程请求或者创建创世块


        //从本地区块文件中恢复,边恢复边校验

        List<Block> blockList = new ArrayList<>();
        DbBlockChecker blockChecker = new DbBlockChecker();

        RocksIterator iter = rocksDB.getRocksDbStore().getRocksDB().newIterator();
        for(iter.seekToFirst();iter.isValid(); iter.next()) {
            Block block = JSON.parseObject(rocksDB.getRocksDbStore().get("i"), new TypeReference<Block>(){});
            System.out.println("read block: " + block);

            if(blockChecker.checkAll(block)){
                blockList.add(block);
            }
        }
        System.out.println("本地区块恢复完毕");
    }

    /**
     * 向网络请求自己没有的区块最新的区块信息
     */
    public void  synchBlock(){}




}
