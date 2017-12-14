package com.xmj.tool.db.test;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.TreeMap;

import com.alibaba.fastjson.JSON;
import com.xmj.tool.db.data.XDataTable;
import com.xmj.tool.db.data.XSq;

public class Test1 {

	public static void main(String[] args) {
		//String sqpart = " and start_time=to_date(?,'yyyy-mm-dd')";
		String sqpart = " and start_time=str_to_date(?,'%Y-%m-%d')";
		String[] colName = {
				"VLR_USER_ALL/10000 as QWDJYH",	
				"VLR_USER_2G/10000 as BDYH",		
				"VLR_USER_TD/10000 as GWDJYH",		
				"ATTACH_USER_LTE/10000 as TWDJYH", 	
				"TRAF_VOICE_ALL/10000 as TWBDYH",		
				"TRAF_VOICE_2G/10000 as LTEFZYH",	
				"TRAF_VOICE_TD/10000 as QWHWL",		
				"TRAF_DATA_ALL/1000 as GWHWL",		
				"TRAF_DATA_2G/1000 as TWHWL",		
				"TRAF_DATA_TD/1000 as QWSJLL",		
				"TRAF_DATA_LTE/1000/1000 as GWSJLL",		
				"ATT_MO/10000 as TWSJLL",		
				"USER_LOCAL_2G as LTESJLL",	
				"USER_LOCAL_TD as DXLL",		
				"USER_LOCAL_ALL as VLRDJYHS",	
				"USER_MAXREGISTER_TD/10000 as TWMAXDJYHS",
				"VOLTEHWL/10000 as VOLTEHWL",	
				"VOLTEZCYHS/10000 as VOLTEZCYHS"
		};
		
		String city1 = "广州";
		String city2 = "深圳";
		String time1 = "2017-10-24";
		String time2 = "2017-10-22";
		
		
		XSq q1 = new XSq().select(colName)
				.from("test").where("ne_name", city1).append(sqpart, time1);
		XSq q2 = new XSq().select(colName)
				.from("test").where("ne_name", city2).append(sqpart, time2);
		
		System.out.println("getSQL1:"+q1.getSQL());
		XDataTable datatable1 = q1.use("Default").execute();
		System.out.println("getSQL2:"+q2.getSQL());
		XDataTable datatable2 = q2.use("Default").execute();
		Test1 t = new Test1();
		Object json = t.calculation(datatable1, datatable2);
		System.out.println("json:\n"+json);
		
	}
	
	private Object calculation(XDataTable datatable1,XDataTable datatable2){
		String QWDJYH1 = datatable1.getString(1, "QWDJYH");	
		String BDYH1 = datatable1.getString(1, "BDYH");			
		String GWDJYH1 = datatable1.getString(1, "GWDJYH");			
		String TWDJYH1 = datatable1.getString(1, "TWDJYH");	 	
		String TWBDYH1 = datatable1.getString(1, "TWBDYH");			
		String LTEFZYH1 = datatable1.getString(1, "LTEFZYH");		
		String QWHWL1 = datatable1.getString(1, "QWHWL");			
		String GWHWL1 = datatable1.getString(1, "GWHWL");			
		String TWHWL1 = datatable1.getString(1, "TWHWL");			
		String QWSJLL1 = datatable1.getString(1, "QWSJLL");			
		String GWSJLL1 = datatable1.getString(1, "GWSJLL");			
		String TWSJLL1 = datatable1.getString(1, "TWSJLL");			
		String LTESJLL1 = datatable1.getString(1, "LTESJLL");		
		String DXLL1 = datatable1.getString(1, "DXLL");			
		String VLRDJYHS1 = datatable1.getString(1, "VLRDJYHS");		
		String TWMAXDJYHS1 = datatable1.getString(1, "TWMAXDJYHS");	
		String VOLTEHWL1 = datatable1.getString(1, "VOLTEHWL");		
		String VOLTEZCYHS1 = datatable1.getString(1, "VOLTEZCYHS");	
		
		String QWDJYH2 = datatable2.getString(1, "QWDJYH");	
		String BDYH2 = datatable2.getString(1, "BDYH");			
		String GWDJYH2 = datatable2.getString(1, "GWDJYH");			
		String TWDJYH2 = datatable2.getString(1, "TWDJYH");	 	
		String TWBDYH2 = datatable2.getString(1, "TWBDYH");			
		String LTEFZYH2 = datatable2.getString(1, "LTEFZYH");		
		String QWHWL2 = datatable2.getString(1, "QWHWL");			
		String GWHWL2 = datatable2.getString(1, "GWHWL");			
		String TWHWL2 = datatable2.getString(1, "TWHWL");			
		String QWSJLL2 = datatable2.getString(1, "QWSJLL");			
		String GWSJLL2 = datatable2.getString(1, "GWSJLL");			
		String TWSJLL2 = datatable2.getString(1, "TWSJLL");			
		String LTESJLL2 = datatable2.getString(1, "LTESJLL");		
		String DXLL2 = datatable2.getString(1, "DXLL");			
		String VLRDJYHS2 = datatable2.getString(1, "VLRDJYHS");		
		String TWMAXDJYHS2 = datatable2.getString(1, "TWMAXDJYHS");	
		String VOLTEHWL2 = datatable2.getString(1, "VOLTEHWL");		
		String VOLTEZCYHS2 = datatable2.getString(1, "VOLTEZCYHS");
		
		System.out.println("QWDJYH:"+QWDJYH1+","+QWDJYH2);
		
		String QWDJYH = toResult(QWDJYH1,QWDJYH2);
		String BDYH = toResult(BDYH1,BDYH2);
		String GWDJYH = toResult(GWDJYH1,GWDJYH2);
		String TWDJYH = toResult(TWDJYH1,TWDJYH2);
		String TWBDYH = toResult(TWBDYH1,TWBDYH2);
		String LTEFZYH = toResult(LTEFZYH1,LTEFZYH2);
		String QWHWL = toResult(QWHWL1,QWHWL2);
		String GWHWL = toResult(GWHWL1,GWHWL2);
		String TWHWL = toResult(TWHWL1,TWHWL2);
		String QWSJLL = toResult(QWSJLL1,QWSJLL2);
		String GWSJLL = toResult(GWSJLL1,GWSJLL2);
		String TWSJLL = toResult(TWSJLL1,TWSJLL2);
		String LTESJLL = toResult(LTESJLL1,LTESJLL2);
		String DXLL = toResult(DXLL1,DXLL2);
		String VLRDJYHS = toResult(VLRDJYHS1,VLRDJYHS2);
		String TWMAXDJYHS = toResult(TWMAXDJYHS1,TWMAXDJYHS2);
		String VOLTEHWL = toResult(VOLTEHWL1,VOLTEHWL2);
		String VOLTEZCYHS = toResult(VOLTEZCYHS1,VOLTEZCYHS2);
		
		Map<String, String> map = new TreeMap<String, String>();
		
		map.put("全网登记用户(万户)", QWDJYH);
		map.put("本地用户(万户)", BDYH);
		map.put("G网登记用户(万户)", GWDJYH);
		map.put("T网登记用户(万户)", TWDJYH);
		map.put("T网本地用户(万户)", TWBDYH);
		map.put("LTE附着用户数(万户)", LTEFZYH);
		map.put("全网话务量(万ERL)", QWHWL);
		map.put("G网话务量(万ERL)", GWHWL);
		map.put("T网话务量(万ERL)", TWHWL);
		map.put("G+T网数据流量(TB)", QWSJLL);
		map.put("G网数据流量(TB)", GWSJLL);
		map.put("T网数据流量(TB)", TWSJLL);
		map.put("LTE网数据流量(TB)", LTESJLL);
		map.put("短信流量(万条)", DXLL);
		map.put("VLR登记用户数(万户)", VLRDJYHS);
		map.put("T网最大登记用户数(万户)", TWMAXDJYHS);
		map.put("VOLTE话务量", VOLTEHWL);
		map.put("VOLTE网络注册用户数", VOLTEZCYHS);
		
		return JSON.toJSONString(map);
	}
	private String toResult(String str1,String str2){
		if("".equals(str1)){
			str1 = "100";
		}
		if("".equals(str2)){
			str2 = "100";
		}
		double d = Double.parseDouble(str1)/Double.parseDouble(str2)*100 - 100;	
		//System.out.println("d:"+d);
		return toTwo(d);
	}
	private String toTwo(double d){
		DecimalFormat df=new DecimalFormat(".##");
		String st=df.format(d);
		return st;
	}
	
}
