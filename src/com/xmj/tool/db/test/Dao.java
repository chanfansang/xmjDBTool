package com.xmj.tool.db.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.xmj.tool.db.data.XSq;
import com.xmj.tool.db.data.XVO;
import com.xmj.tool.db.data.XInsert;

public class Dao {

	public static void main(String[] args) {
		
		
		XVO xvo = new XVO();
		xvo.setValue(4,"qwe",null);
		List<XVO> lists = new ArrayList<>();
		lists.add(xvo);
		
		

		String table = "test1";
		XInsert xi = new XSq().use("Default").insert(table);
		for (XVO x : lists) {
			xi.execute(x);
		}
		xi.commit();
	}

}
