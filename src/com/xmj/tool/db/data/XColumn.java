package com.xmj.tool.db.data;

import java.util.Map;
import java.util.TreeMap;

public class XColumn {
	private String columnName;
	private Object columnValue;
	private int columnType;
	private boolean isAllowNull = true;
	
	public boolean isAllowNull() {
		return isAllowNull;
	}
	public void setAllowNull(boolean isAllowNull) {
		this.isAllowNull = isAllowNull;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public Object getColumnValue() {
		return columnValue;
	}
	public void setColumnValue(Object columnValue) {
		this.columnValue = columnValue;
	}
	public int getColumnType() {
		return columnType;
	}
	public void setColumnType(int i) {
		this.columnType = i;
	}
	
	
}
