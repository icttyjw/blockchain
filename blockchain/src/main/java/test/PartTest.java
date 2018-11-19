package test;

import org.junit.Test;

import edu.ictt.blockchain.bean.Runstate;
import edu.ictt.blockchain.common.FastJsonUtil;

public class PartTest {

	@Test
	public void jsontest(){
		Runstate rs=new Runstate("1","ss","123","1","1","12:00:00","fdf","dfdf");
		String text=FastJsonUtil.toJSONString(rs);
		System.out.println(text);
	}
}
