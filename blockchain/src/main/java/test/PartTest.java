package test;

import org.junit.Test;

import edu.ictt.blockchain.bean.Runstate;
import edu.ictt.blockchain.common.FastJsonUtil;
import edu.ictt.blockchain.common.util.DerbyDBUtil;
import edu.ictt.blockchain.manager.ManageMessage;

public class PartTest {

	@Test
	public void jsontest(){
		Runstate rs=new Runstate("1","ss","123","1","1","main","12:00:00","fdf","fdf","dfdf");
		String text=FastJsonUtil.toJSONString(rs);
		System.out.println(text);
		Runstate rw=FastJsonUtil.toBean(text, Runstate.class);
		System.out.println(rw);
	}
	/*
	 * 数据库查询测试
	 */
	@Test
	public void querytest(){
		//Runstate rs=new Runstate("1","ss","123","1","1","12:00:00","fdf","fdf","dfdf");
		ManageMessage mm=new ManageMessage();
		//mm.Regist(rs);
		Runstate rw=mm.queryByIp("ss");
		System.out.println(rw);
	}
	/*
	 * 数据库写入测试
	 */
	@Test
	public void inserttest(){
		Runstate rs=new Runstate("1","ss","123","1","1","main","12:00:00","fdf","fdf","dfdf");
		//ManageMessage mm=new ManageMessage();
		System.out.println(rs.toString());
		//mm.Regist(rs);
	}
	@Test
	public void updatetest(){
		Runstate rs=new Runstate("1","ss","123","1","1","main","12:00:00","fdf","fdf","dfdf");
		ManageMessage mm=new ManageMessage();
		System.out.println(rs.toString());
		mm.Update(rs);
	}
	@Test
	public void destroytest(){
		String sql="drop table node";
		DerbyDBUtil.executeInit(sql);
	}
}
