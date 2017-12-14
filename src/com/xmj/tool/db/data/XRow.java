package com.xmj.tool.db.data;

import java.util.ArrayList;

public class XRow {

	private ArrayList<XColumn> row = null;
	private XDataTable datatable = null;
	public ArrayList<XColumn> getRow() {
		return row;
	}

	public void setRow(XDataTable datatable, ArrayList<XColumn> row) {
		this.datatable = datatable;
		this.row = row;
	}
	
	public String getString(String columnName){
		int i = datatable.getColumnIndex(columnName);
		System.out.println("i:"+i);
		return getString(i);
		
	}
	public String getString(int index){
		if (index == -1) {
			return "";
		}
		return row.get(index).getColumnValue().toString();
		
		//return null;
	}
	
	
}
