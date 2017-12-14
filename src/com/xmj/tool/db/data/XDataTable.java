package com.xmj.tool.db.data;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class XDataTable {

	private List<XRow> rows;
	private ArrayList<XColumn> columns;
	
	public XDataTable(ResultSet rs) {
		this.rows = new ArrayList<>();
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			ArrayList<XColumn> types = new ArrayList<XColumn>();
			for (int i = 1; i <= columnCount; ++i) {
				String name = rsmd.getColumnLabel(i);
				boolean b = rsmd.isNullable(i) == 1;
				XColumn dc = new XColumn();
				dc.setAllowNull(b);
				dc.setColumnName(name);
				
				int dataType = rsmd.getColumnType(i);
				if ((dataType == 1) || (dataType == 12)) {
					dc.setColumnType(1);
				} else if ((dataType == 93) || (dataType == 91)) {
					dc.setColumnType(12);
				} else if (dataType == 3) {
					dc.setColumnType(4);
					int dataScale = rsmd.getScale(i);
					int dataPrecision = rsmd.getPrecision(i);
					if ((dataScale == 0) && (dataPrecision != 0))
						dc.setColumnType(7);
					else if ((dataScale > 0) && (dataScale + dataPrecision > 17))
						dc.setColumnType(3);
					else
						dc.setColumnType(4);
				} else if ((dataType == 8) || (dataType == 7)) {
					dc.setColumnType(6);
				} else if (dataType == 6) {
					dc.setColumnType(5);
				} else if (dataType == 4) {
					dc.setColumnType(8);
				} else if ((dataType == 5) || (dataType == -6)) {
					dc.setColumnType(9);
				} else if (dataType == -7) {
					dc.setColumnType(11);
				} else if (dataType == -5) {
					dc.setColumnType(7);
				} else if ((dataType == 2004) || (dataType == -4)) {
					dc.setColumnType(2);
				} else if ((dataType == 2005) || (dataType == -1)) {
					dc.setColumnType(10);
				} else if (dataType == 2) {
					int dataScale = rsmd.getScale(i);
					int dataPrecision = rsmd.getPrecision(i);
					if ((dataScale == 0) && (dataPrecision != 0))
						dc.setColumnType(7);
					else if ((dataScale > 0) && (dataScale + dataPrecision > 17))
						dc.setColumnType(3);
					else
						dc.setColumnType(6);
				} else {
					dc.setColumnType(1);
				}
				types.add(dc);
				
			}
			columns = types;
			
			while (rs.next()) {
				XRow row = new XRow();
				for (int j = 1; j <= columnCount; ++j) {
					//int columnType = this.columns.get(j-1).getColumnType();
					this.columns.get(j-1).setColumnValue(rs.getObject(j));

				}
				row.setRow(this,columns);
				rows.add(row);				
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
	
	public int getColumnIndex(String columnName) {
		//System.out.println("columns.size():"+columns.size());
		for (int i = 0; i < this.columns.size(); ++i) {
			//System.out.println("columns.get(i).getColumnName():"+columns.get(i).getColumnName());
			if(columns.get(i).getColumnName().equals(columnName)){
				return i;
			}
			
		}
		return -1;
	}
	
	public int getRowCount() {
		return this.rows.size();
	}
	
	public int getColumnCount() {
		return this.columns.size();
	}
	
	public String getString(String columnName){
		if(rows.size() == 0){
			System.out.println("没有查询到数据。");
			return null;
		}
		return rows.get(0).getString(columnName);
		
	}
	public String getString(int rowNum ,String columnName){
		if(rows.size() == 0){
			System.out.println("rows.size():"+rows.size());
			
			System.out.println("没有查询到数据。");
			return null;
		}
		return rows.get(rowNum-1).getString(columnName);
		
	}
	
	public String getColumnMsg(int colNum){
		
		
		return null;
	}
	
	
	
	
	
	
	
	
	
}
