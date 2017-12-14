package com.xmj.tool.db.data;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class XInsert {

	
	

	private Connection com = null;
	private PreparedStatement cmd = null;

	public XInsert(Connection com, PreparedStatement cmd) {
		this.com = com;
		this.cmd = cmd;
		

	}
	
	private int num = 1;
	public void execute(XVO list){
		num++;
		try {
			int i = 1;
			for(Object o : list.getXobjectList()){
				if(o == null){
					cmd.setString(i, "");
				}
				if(o instanceof String){
					cmd.setString(i, o.toString());
				}
				else if(o instanceof Integer){
					cmd.setInt(i, Integer.parseInt(o.toString()));
				}
				else if(o instanceof Long){
					cmd.setLong(i, Long.parseLong(o.toString()));
				}
				else if(o instanceof Date){
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date = null;
					try {
						date = sdf.parse(o.toString());
						cmd.setTimestamp(i, new java.sql.Timestamp(date.getTime()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						cmd.setString(i,"");
					}  
					//cmd.setDate(i, java.sql.Date.valueOf(date.toLocaleString()));
					
				}
				else if(o instanceof Double){
					cmd.setDouble(i, Double.parseDouble(o.toString()));
				}
				else{
					cmd.setObject(i, o);
				}
				
				
				
				i++;
			}
			cmd.addBatch();
			if(num%1000==0){
				cmd.executeBatch();
			}
			
			
		
		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		            
		
	}

	
	public boolean commit(){
		try {
			cmd.executeBatch();
			com.commit();
			if(cmd!=null){
				try {
					cmd.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				}
			}
			if(com!=null){
				try {
					com.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				}
			}
			return true;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return false;
		
	}
}
