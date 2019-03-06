package edu.ictt.blockchain.Block.db;

import java.util.List;

/**
 * key-value型DB数据库操作接口
 */
public interface DbStore {
	 /**
     * 数据库key value
     *
     * @param key
     *         key
     * @param value
     *         value
     */
    void put(String key, String value);

    /**
     * get By Key
     *
     * @param key
     *         key
     * @return value
     */
    String get(String key);

    /**
     * remove by key
     *
     * @param key
     *         key
     */
    void remove(String key);


    /**
     * 放入key list类型
     * @param key
     * @param list
     */
    //void putList(String key, List list);

    /**
     *取出list by key
     */
   // void getList(String key);

    /**
     *取出key-list类型中list的某个value by key
     */
   // void getEleOfList(String key, Object object);
}
