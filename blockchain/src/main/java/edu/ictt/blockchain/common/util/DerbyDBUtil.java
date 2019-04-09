 package edu.ictt.blockchain.common.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Properties;

import sun.jdbc.rowset.CachedRowSet;


/**
 * Derby的接口实现，用作未上链数据的持久化以及本地信息（密钥，关联节点等）的保存
 * executeUpdate、executeInit、query这三个方法的参数是完整的sql语句
 */
public class DerbyDBUtil {

	static {
		try {
			DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());//new com.mysql.jdbc.Driver());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/*
	 * 嵌入模式的derby只能进行单例操作
	 * 每次操作都需要完成链接和关闭链接
	 */
	private static Connection getConnection(){
		try {
			return DriverManager.getConnection("jdbc:derby:BlockDB;create=true","user","password");
			//return DriverManager.getConnection("jdbc:mysql://localhost:3306/block?useUnicode=true&characterEncoding=utf8","root","1234");
		}
		catch(Exception e) {
			
			System.out.println(e);
		}
		return null;
	}
	
	private static Connection closeConnection(){
		try{
			return DriverManager.getConnection("jdbc:derby:BlockDB;shutDown=true");
		}
		catch(Exception e){
			System.out.println(e);
		}
		return null;
	}
	
	public static boolean JDBCexit()//检测数据库能否顺利连接
	{
		try {
			DriverManager.getConnection("jdbc:derby:BlockDB;create=true","user","password");//DriverManager.getConnection("jdbc:mysql://localhost:3305/block?useUnicode=true&characterEncoding=utf8","root","1234");
			 return true;
		}
		catch(Exception e) {
			return false;
		}
	}
	 /**  
     * 写入修改Table时使用这个方法
     * @param sql 完整的sql语句
     */  
	public static void executeUpdate(String sql) {
    	Connection conn = getConnection();
    	try {
    		Statement stmt = conn.createStatement();
    		stmt.executeUpdate(sql);
    		stmt.close();
    		conn.close();
    		 closeConnection();
    	}
    	catch(SQLException e) {
    		System.out.println(e);
    	}finally{
    		try {
				DriverManager.getConnection("jdbc:derby:BlockDB;shutDown=true");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    }
	 /**  
     * 查询时使用这个方法
     * @param sql 完整的sql语句
     */  
    public static ResultSet query(String sql) {
    	Connection conn = getConnection();
    	ResultSet rs = null;
    	CachedRowSet crs = null;
    	try {
    		crs = new CachedRowSet();
    		Statement stmt = conn.createStatement();
    		rs = stmt.executeQuery(sql);
    		crs.populate(rs);
    		stmt.close();
    		conn.close();
    		closeConnection();
    	}
    	catch(SQLException e) {
    		System.out.println(e);
    	}
    	return crs;
    }
    
    public static Boolean doesTableExist(String tablename) {  
	    Connection conn = null;  
	    HashSet<String> set = new HashSet<String>();  
	    Properties props =new Properties();
		   props.setProperty("user", "user");
		   props.setProperty("password", "password");
	    try {  
	    	conn =DriverManager.getConnection("jdbc:derby:BlockDB;create=true","user","password");//, props);
	        //Class.forName(DatabaseConnection.getDatabaseConnection());  
	        DatabaseMetaData meta = conn.getMetaData();  
	        ResultSet res = meta.getTables(null, null, null,  
	                new String[]{"TABLE"});  
	        while (res.next()) {  
	            set.add(res.getString("TABLE_NAME"));  
	        }  
	        res.close();  
	        conn.close();  
	    } catch (Exception e) {  
	        System.err.println("Exception: " + e.getMessage());  
	    }  
	    //System.out.println(set);  
	    return set.contains(tablename.toUpperCase());  
	}
    
    /**  
     * 暂时是签到的block，需要修改
     */  
    public static void init(){
    	Connection conn = getConnection();
    	try {
    		 conn.setAutoCommit(false);
			Statement statement =conn.createStatement();
			statement.executeUpdate("create table block (block_index int generated always as identity ,lastHash varchar(60),Merkle varchar(60),time int,difficulty int,"
					+ "nonce int,cumulativeDifficulty int ,blocknum int ,recordCount int,data varchar(2048))");
			statement.executeUpdate("create table record (orderStamp int,mac varchar(10),time int,lockScript varchar(60),unlockScript varchar(120),blocknum int)");
			 conn.setAutoCommit(true);
			statement.close();
    		conn.close();
    		closeConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /**  
     * 创建、删除Tabel时使用这个方法
     * @param sql 完整的sql语句
     */  
    public static void executeInit(String sql){
    	Connection conn = getConnection();
    	try {
    		 conn.setAutoCommit(false);
			Statement statement =conn.createStatement();
			statement.executeUpdate(sql);
			statement.executeUpdate("create table block (block_index int generated always as identity ,lastHash varchar(60),Merkle varchar(60),time int,difficulty int,"
				+ "nonce int,cumulativeDifficulty int ,blocknum int ,recordCount int,data varchar(2048))");
			statement.executeUpdate("create table record (orderStamp int,mac varchar(10),time int,lockScript varchar(60),unlockScript varchar(120),blocknum int)");
			 conn.setAutoCommit(true);
			statement.close();
    		conn.close();
    		closeConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**  
     * 针对的是签到中的数据库，需要修改
     */  
    public static void drop(){
    	Connection conn = getConnection();
    	try {
    		 conn.setAutoCommit(false);
			Statement statement =conn.createStatement();
			statement.executeUpdate("drop table block");
			statement.executeUpdate("drop table record");
			conn.setAutoCommit(true);
			statement.close();
			conn.close();
			closeConnection();
    	}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
