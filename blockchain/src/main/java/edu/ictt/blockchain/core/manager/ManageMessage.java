package edu.ictt.blockchain.core.manager;

import java.sql.ResultSet;
import java.sql.SQLException;

import edu.ictt.blockchainmanager.Runstate;
import edu.ictt.blockchain.common.util.DerbyDBUtil;

/*
 * 管理节点信息
 * 管理初始化时自动检测是否有数据库
 */
public class ManageMessage {

	
	{
		boolean doesTableExist=false;
		 doesTableExist=DerbyDBUtil.doesTableExist("Node");
		 if(doesTableExist==false)
		 {
			 String sql="create table node (Id varchar(10),name varchar(10),Ip varchar(20),state varchar(10),"
			 		+ "connectstate varchar(10),lastConnect varchar(10),main varchar(10),priKey varchar(80),pubKeyX varchar(80),pubKeyY varchar(80))";
			 DerbyDBUtil.executeInit(sql);
		 }
	}
	
	/*
	 * 将注册信息写入数据库
	 */
	public void Regist(Runstate rs){
		String sql="insert into node(Id,name,Ip,state,connectstate,main,lastConnect,priKey,pubKeyX,pubKeyY)values('"
				+rs.getId()
				+"','"
				+rs.getName()
				+"','"
				+rs.getIp()
				+"','"
				+rs.getState()
				+"','"
				+rs.getConnectstate()
				+"','"
				+rs.getMain()
				+"','"
				+rs.getLastConnect()
				+"','"
				+rs.getPriKey()
				+"','"
				+rs.getPubKeyX()
				+"','"
				+rs.getPubKeyY()
				+"')";
		DerbyDBUtil.executeUpdate(sql);
	}
	/*
	 * 暂时是通过name修改其余信息，不能修改公私钥
	 */
	public void Update(Runstate rs){
		String sql="update node set id='"+rs.getId()
					+"',"+"Ip='"+rs.getIp()
					+"',"
					+"state='"+rs.getState()
					+"',"
					+"connectstate='"+rs.getConnectstate()
					+"',"
					+"main='"+rs.getMain()
					+"',"
					+"lastConnect='"+rs.getLastConnect()
					+"'where name='"+rs.getName()+"'";
		DerbyDBUtil.executeUpdate(sql);
	}
	/*
	 * 通过name查询保存的记录信息
	 */
	public Runstate queryByIp(String name){
		String sql="select * from node where name='"+name+"'";
		ResultSet rs = DerbyDBUtil.query(sql);
		Runstate rstate=new Runstate();
		try {
			if(rs.next())
			{
				String id=rs.getString("Id");
				String Ip=rs.getString("Ip");
				String s=rs.getString("state");
				String cs=rs.getString("connectstate");
				String main=rs.getString("main");
				String lc=rs.getString("lastConnect");
				String prik=rs.getString("priKey");
				String pubkx=rs.getString("pubKeyX");
				String pubky=rs.getString("pubKeyY");
				
				rstate=new Runstate(id,name,Ip,s,cs,main,lc,prik,pubkx,pubky);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rstate;
	}
}
