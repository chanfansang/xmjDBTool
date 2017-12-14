package com.xmj.tool.db.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

import com.xmj.tool.db.util.ConfigUtil;
import com.xmj.tool.db.util.Gobal;
import com.xmj.tool.db.util.XMLConfigUtil;

public class Connect {

	protected Connection conn;
	//private XMLConfigUtil xml = new XMLConfigUtil();
	
	public Connection getConn() {
		return conn;
	}


	public void setConn(Connection conn) {
		this.conn = conn;
	}


	private String username;
	private String password;
	private String classname;
	private String url;
	
	
	
	public Connect(String databaseName){
		XMLConfigUtil util = new XMLConfigUtil().getName(databaseName);
		
		if("MYSQL".equalsIgnoreCase(util.getType())){
			this.classname = "com.mysql.jdbc.Driver";
		}
		if("ORACLE".equalsIgnoreCase(util.getType())){
			this.classname = "oracle.jdbc.driver.OracleDriver";
		}
		this.username = util.getUsername();
		this.password = util.getPassword();
		
		if(util.getUrl().contains("?")){
			
			if(util.getUrl().contains("rewriteBatchedStatements")){
				this.url = util.getUrl();
			}else{
				this.url = util.getUrl()+"&useServerPrepStmts=false&rewriteBatchedStatements=true";
			}
		}else{
			this.url = util.getUrl()+"?useServerPrepStmts=false&rewriteBatchedStatements=true";
			
		}
		
		
		System.out.println("url:"+url);
		getConnection(Gobal.TYPE);
	}
	
	
	private Connection getConnection(String xml) {
		
		Connection con = null;
		try {
			Class.forName(classname);
			//System.out.println("Connecting to database...");
			con = DriverManager.getConnection(url, username,
					password);
			
			setConn(con);
		} catch (Exception e) {
			System.out.println("jdbc:" + e.getMessage());
			e.printStackTrace();
		}
		return con;
	}
	
	
	
}
