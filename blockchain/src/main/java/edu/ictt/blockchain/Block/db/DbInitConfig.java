package edu.ictt.blockchain.Block.db;

import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DbInitConfig {

	@Bean
    @ConditionalOnProperty("db.rocksDB")
    public RocksDB rocksDB() {
        RocksDB.loadLibrary();

        Options options = new Options().setCreateIfMissing(true);
        try {
            return RocksDB.open(options, "./rocksDB");
        } catch (RocksDBException e) {
            e.printStackTrace();
            return null;
        }
    }
}