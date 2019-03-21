package test;

import org.rocksdb.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author:zoe
 * @Description:
 * @Date:
 * @Modified By:
 */
public class RocksColumnFamilyTest {

    static {
        RocksDB.loadLibrary();
    }

    RocksDB rocksDB;

    public void testDefaultColumnFamily() throws RocksDBException{
        Options options = new Options().setCreateIfMissing(true);

        rocksDB = RocksDB.open(options, "./ColumnFalimyTest");

        byte[] key = "Hello".getBytes();
        byte[] value = "World".getBytes();
        rocksDB.put(key,value);

        //list方式读出文件中的列族
        List<byte[]> cfs = RocksDB.listColumnFamilies(options, "./ColumnFalimyTest");
        for(byte[] cf:cfs){
            System.out.println("当前列族:" + new String(cf));
        }

        //通过key得到value
        byte[] getValue = rocksDB.get(key);
        System.out.println("key对应的value是:" + new String(getValue));

        //put一对新的键值对
        rocksDB.put("SecondKey".getBytes(),"SecondValue".getBytes());

        //存储key的列表
        List<byte[]> keys = new ArrayList<>();
        keys.add(key);
        keys.add(key);
        keys.add(key);
        keys.add("SecondKey".getBytes());

        //根据key列表生成对应的key-value的map
        Map<byte[],byte[]> valueMap = rocksDB.multiGet(keys);
        for(Map.Entry<byte[],byte[]> entry:valueMap.entrySet()){
            System.out.println(new String(entry.getKey() + ":" + new String(entry.getValue())));
        }

        //使用迭代器遍历
        RocksIterator iter = rocksDB.newIterator();
        for(iter.seekToFirst();iter.isValid(); iter.next()) {
            System.out.println("iter key:" + new String(iter.key() + ",iter value:" + new String(iter.value())));
        }
    }

    public  void testCertainColumnFamily() throws RocksDBException{
        String table = "CertainColumnFamilyTest";
        String key = "certainKey";
        String value = "certainValue";

        //创建一个columnFamilyDescriptors列表
        List<ColumnFamilyDescriptor> columnFamilyDescriptors = new ArrayList<>();
        Options options = new Options().setCreateIfMissing(true);

        //读取文件中的列族？，并为每个列族增加一个ColumnFamilyDescriptor
        //
        List<byte[]> cfs = RocksDB.listColumnFamilies(options, "./ColumnFalimyTest");
        if(cfs.size()>0){
            for(byte[] cf : cfs){
                System.out.println("当前列族:" + new String(cf));
                columnFamilyDescriptors.add(new ColumnFamilyDescriptor(cf,new ColumnFamilyOptions()));
            }
        }else {
            columnFamilyDescriptors.add(new ColumnFamilyDescriptor(RocksDB.DEFAULT_COLUMN_FAMILY,new ColumnFamilyOptions()));
        }

        //一个handle指向一个column
        List<ColumnFamilyHandle> columnFamilyHandles = new ArrayList<>();
        DBOptions dbOptions = new DBOptions().setCreateIfMissing(true);
        rocksDB = RocksDB.open(dbOptions, "./ColumnFalimyTest" ,columnFamilyDescriptors,columnFamilyHandles);
        for(int i=0; i<columnFamilyDescriptors.size(); i++){
            if(new String(columnFamilyDescriptors.get(i).columnFamilyName()).equals(table)){
                rocksDB.dropColumnFamily(columnFamilyHandles.get(i));
            }
        }

        //创建一个新的columnFamily并把其加入到数据库中
        ColumnFamilyHandle columnFamilyHandle = rocksDB.createColumnFamily(
                new ColumnFamilyDescriptor((table.getBytes()), new ColumnFamilyOptions()));

        //把key和value放到handle指向的列族中
        rocksDB.put(columnFamilyHandle, key.getBytes(), value.getBytes());

        //从handle指向的列族中取value
        byte[] getValue = rocksDB.get(columnFamilyHandle, key.getBytes());
        System.out.println("get Value：" + new String(getValue));

        rocksDB.put(columnFamilyHandle, "SecondKey".getBytes(), "SecondValue".getBytes());
        //放入相同key值的一对key-value
        rocksDB.put(columnFamilyHandle, "SecondKey".getBytes(), "ChangedSecondValue".getBytes());
        rocksDB.put(columnFamilyHandle, "ThirdKey".getBytes(), "ThirdValue".getBytes());

        //list读取key
        List<byte[]> keys = new ArrayList<>();
        keys.add(key.getBytes());
        keys.add("SecondKey".getBytes());
        keys.add("ThirdKey".getBytes());


        //为什么要添加两遍。因为里面放了两对----對的，批量讀取時每個key都需要有一個handle
        List<ColumnFamilyHandle> handleList = new ArrayList<>();
        for(int i=0; i<keys.size(); i++){
            handleList.add(columnFamilyHandle);
        }

        //批量读取handlelist中的keys
        Map<byte[], byte[]> multiGet = rocksDB.multiGet(handleList,keys);
        for(Map.Entry<byte[], byte[]> entry : multiGet.entrySet()) {
            System.out.println("批量读取" + new String(entry.getKey()) + "--" + new String(entry.getValue()));
        }

        //移除key及其对应的value...
        rocksDB.remove(columnFamilyHandle,key.getBytes());

        //迭代器遍历当前handle指向的列族
        RocksIterator rocksIterator = rocksDB.newIterator(columnFamilyHandle);
        for(rocksIterator.seekToFirst();rocksIterator.isValid();rocksIterator.next()){
            System.out.println(new String(rocksIterator.key()) + ":" + new String(rocksIterator.value()));
        }
    }

    public static void main(String[] args) throws RocksDBException {
        RocksColumnFamilyTest test = new RocksColumnFamilyTest();
        test.testCertainColumnFamily();
        //test.testDefaultColumnFamily();

    }
}
