package edu.ictt.blockchain.Block.db;

import org.rocksdb.RocksDBException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author:zoe
 * @Description:
 * @Date:
 * @Modified By:
 */


public class ConnectRocksDB {

    ///创建一个数据库操作对象，并与数据库建立连接


    private DbInitConfig dbInitConfig = new DbInitConfig();

    private RocksDbStoreImpl rocksDbStore = new RocksDbStoreImpl();

    public ConnectRocksDB(int datatype) throws RocksDBException {
        dbInitConfig.setDataType(datatype);
        rocksDbStore.setRocksDB(dbInitConfig.rocksDB());
    }

    public DbInitConfig getDbInitConfig() {
        return dbInitConfig;
    }

    public void setDbInitConfig(DbInitConfig dbInitConfig) {
        this.dbInitConfig = dbInitConfig;
    }

    public RocksDbStoreImpl getRocksDbStore() {
        return rocksDbStore;
    }

    public void setRocksDbStore(RocksDbStoreImpl rocksDbStore) {
        this.rocksDbStore = rocksDbStore;
    }
}
