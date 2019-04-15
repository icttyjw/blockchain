package edu.ictt.blockchain.core.manager;


import edu.ictt.blockchain.Block.block.Block;
import edu.ictt.blockchain.Block.check.CheckerManager;
import edu.ictt.blockchain.Block.db.DbStore;
import edu.ictt.blockchain.Block.record.DegreeRecord;
import edu.ictt.blockchain.Block.record.GradeRecord;
import edu.ictt.blockchain.common.Constants;
import edu.ictt.blockchain.core.event.AddBlockEvent;
import edu.ictt.blockchainmanager.groupmodel.BlockInfo;
import edu.ictt.blockchainmanager.sql.service.BlockInfoService;
import edu.ictt.blockchain.ApplicationContextProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.tio.utils.json.Json;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

/**
 * block的本地存储
 * @author wuweifeng wrote on 2018/4/25.
 */
@Service
public class DbBlockGenerator {
    @Resource
    private DbStore dbStore;
    @Resource
    private CheckerManager checkerManager;
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private BlockInfoService blockInfoService;

    /**
     * 数据库里添加一个新的区块
     *
     * @param addBlockEvent
     *         addBlockEvent
     */
    @Order(1)
    @EventListener(AddBlockEvent.class)
    public synchronized void addBlock(AddBlockEvent addBlockEvent) {
        logger.info("开始生成本地block");
        Block block = (Block) addBlockEvent.getSource();
        String hash = block.getBlockHash();
        //如果已经存在了，说明已经更新过该Block了
        if (dbStore.get(hash) != null) {
            System.out.println("this block has been saved");
            return;
        }
        //校验区块
        if (checkerManager.check(block).getCode() != 0) {
            return;
        }

        //如果没有上一区块，说明该块就是创世块
        if (block.getBlockHeader().getHashPreviousBlock() == null) {
            dbStore.put(Constants.KEY_FIRST_BLOCK, hash);
        } else {
            //保存上一区块对该区块的key value映射
            dbStore.put(Constants.KEY_BLOCK_NEXT_PREFIX + block.getBlockHeader().getHashPreviousBlock(), hash);
        }
        //存入rocksDB
        dbStore.put(hash, Json.toJson(block));
        //设置最后一个block的key value
        dbStore.put(Constants.KEY_LAST_BLOCK, hash);

        logger.info("本地已生成新的Block");
        List<GradeRecord> lg=new ArrayList<GradeRecord>();
        List<DegreeRecord> ld=new ArrayList<DegreeRecord>();
        lg=block.getBlockBody().getGrecordsList();
        ld=block.getBlockBody().getDrecordsList();
        if(lg!=null){
        	GradeRecord gradeRecord=lg.get(0);
        BlockInfo blockInfo=new BlockInfo(hash, gradeRecord.getSchoolInfo().getSchoolId(), gradeRecord.getSchoolInfo().getSchoolName()
        		, gradeRecord.getFacultyInfo().getFacultyId(), gradeRecord.getFacultyInfo().getFacultyName(), gradeRecord.getGradeInfo().getCourseInfo().getCourseId(), gradeRecord.getGradeInfo().getCourseInfo().getCourseName());
        blockInfoService.saveBlockInfo(blockInfo);
        }
       /* if(ld!=null){
        	DegreeRecord degreeRecord=ld.get(0);
        BlockInfo blockInfo=new BlockInfo(hash, degreeRecord.getSchoolInfo().getSchoolId(), degreeRecord.getSchoolInfo().getSchoolName()
        		, 0,null,degreeRecord.getDegreeId(),degreeRecord.getDegree())//gradeRecord.getFacultyInfo().getFacultyId(), gradeRecord.getFacultyInfo().getFacultyName(), gradeRecord.getGradeInfo().getCourseInfo().getCourseId(), gradeRecord.getGradeInfo().getCourseInfo().getCourseName());
        blockInfoService.saveBlockInfo(blockInfo);
        }*/
        //同步到sqlite
       // sqliteSync();
    }

    public DbStore getDbStore() {
        return dbStore;
    }

    public void setDbStore(DbStore dbStore) {
        this.dbStore = dbStore;
    }

    public CheckerManager getCheckerManager() {
        return checkerManager;
    }

    public void setCheckerManager(CheckerManager checkerManager) {
        this.checkerManager = checkerManager;
    }


    /**
     * sqlite根据block信息，执行sql
     */
    //@Async
    //public void sqliteSync() {
        //开始同步到sqlite
    //    ApplicationContextProvider.publishEvent(new DbSyncEvent(""));
   // }
}
