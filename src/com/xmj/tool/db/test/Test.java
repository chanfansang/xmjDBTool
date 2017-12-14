package com.xmj.tool.db.test;

import java.util.ArrayList;
import java.util.List;

import com.xmj.tool.db.data.XColumn;
import com.xmj.tool.db.data.XDataTable;
import com.xmj.tool.db.data.XInsert;
import com.xmj.tool.db.data.XSq;
import com.xmj.tool.db.data.XVO;

public class Test {

	public static void main2(String[] args) {
		// TODO Auto-generated method stub
		//XSq q = new XSq("select id, Time from doubanmovie where id = 65536");
		String city1 = "广州";
		String time1 = "2017-10-22";
		String sql1 = "start_time=str_to_date(?,'%Y-%m-%d %H:%i:%s')";
		XSq q = new XSq().select("VLR_USER_ALL as QWDJYH","VLR_USER_2G/10000 as BDYH")
				.from("test").where("NE_NAME", city1).append(sql1, time1);
				//.eq("start_time", "str_to_date('"+time1+"','%Y-%m-%d %H:%i:%s')");
		XSq q1 = new XSq().select("VLR_USER_ALL as QWDJYH","VLR_USER_2G/10000 as BDYH")
				.from("test").where("NE_NAME", city1).and().append(sql1, time1);
		
		
		
		System.out.println("getSQL:"+q1.getSQL());
		XDataTable datatable = q1.use("Default").execute();
		//System.out.println("带1："+datatable.getString(1, "QWDJYH"));
		System.out.println("不带1："+datatable.getString("QWDJYH"));
		System.out.println(datatable.getColumnCount());
		System.out.println(datatable.getRowCount());
	}

	public static void main1(String[] args) {
		String city1 = "广州";
		String city2 = "深圳";
		String time1 = "2017-10-21 00:00:00";
		String time2 = "2017-10-22 00:00:00";
		String sql1 = " and start_time=str_to_date(?,'%Y-%m-%d %H:%i:%s')";
		XSq q = new XSq().select("VLR_USER_ALL/10000 as QWDJYH")
				.from("test").where("NE_NAME", city1).append(sql1, time1);
				//.eq("start_time", "str_to_date('"+time1+"','%Y-%m-%d %H:%i:%s')");
		
		
		System.out.println("getSQL:"+q.getSQL());
		String id = q.getResultString();
		System.out.println(id);
				
	}
	
	public static void main4(String[] args) {
		XSq q = new XSq("select id, VLR_USER_ALL from test where id = 1");
		
		System.out.println("getSQL:"+q.getSQL());
		XDataTable datatable = q.use("Default").execute();
		System.out.println(datatable.getString(1, "VLR_USER_ALL"));
		System.out.println("VLR_USER_ALL:"+datatable.getString("VLR_USER_ALL")+"!");
		System.out.println(datatable.getColumnCount());
		System.out.println(datatable.getRowCount());
		System.out.println(q.use("Default").toString());
	}
	public static void main3(String[] args) {
		
		String table = "test1";
		
		List<XColumn> ll = new XSq().use("Default").getTableMsg(table);
		for(XColumn x:ll){
			System.out.println(x.getColumnName());
		}
		
	}
	
	public static void main(String[] args) {
		String table = "test1";
		XVO xvo = new XVO();
		xvo.setValue(4,"qwe",null);
		List<XVO> lists = new ArrayList<>();
		lists.add(xvo);
		
		
		XInsert xi = new XSq().use("Default").insert(table);
		for (XVO x : lists) {
			xi.execute(x);
		}
		xi.commit();
		
	}
}
