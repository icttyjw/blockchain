package edu.ictt.blockchain.Block.db;

import edu.ictt.blockchain.Block.block.Block;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DbInitConfig {

    /**
     * 根据数据类型写入到不同的rocksdb文件夹，讀取時根據類型去相應的文件夾讀取
     * 1:block
     * 2:degreeRecord
     * 3:gradeRecord
     */
    private int dataType;

	@Bean
    public RocksDB rocksDB() throws RocksDBException {
        RocksDB.loadLibrary();

        Options options = new Options().setCreateIfMissing(true);
        try {
        	return RocksDB.open(options, "./rocksDB");
          /*  if (dataType == 1) {
                return RocksDB.open(options, "./Block");
            }
            if (dataType == 2) {
                return RocksDB.open(options, "./DegreeRecord");
            }
            if (dataType == 3) {
                return RocksDB.open(options, "./GradeRecord");
            }
            else {
                return RocksDB.open(options, "./OtherInformation");
            }*/
        } catch (RocksDBException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }
}
