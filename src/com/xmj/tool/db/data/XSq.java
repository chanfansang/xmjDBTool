package com.xmj.tool.db.data;

import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.xmj.tool.db.exception.QueryException;
import com.xmj.tool.db.jdbc.Connect;
import com.xmj.tool.db.util.DataUtil;

public class XSq {

	StringBuilder sql = new StringBuilder();
	ArrayList<Object> params = new ArrayList<Object>();

	private Connect dbConn = null;
	private Connection conn;

	public XSq() {

	}

	public XSq(String sql) {
		super();
		this.sql.append(sql);
	}

	public XSq select(String... columns) {
		this.sql.append("select");
		boolean first = true;
		for (String column : columns) {
			if (first) {
				first = false;
				this.sql.append(" ");
			} else {
				this.sql.append(",");
			}
			// verifyName(column);
			this.sql.append(column);
		}
		return this;
	}

	public XSq from(String... tables) {
		this.sql.append(" from");
		boolean first = true;
		for (String table : tables) {
			// verifyName(table);
			if (first) {
				this.sql.append(" ");
				first = false;
			} else {
				this.sql.append(",");
			}
			this.sql.append(table);
		}
		return this;
	}

	public XSq where() {
		this.sql.append(" where");
		return this;
	}

	public XSq where(String field, Object value) {
		this.sql.append(" where");
		eq(field, value);
		return this;
	}

	public XSq eq(String field, Object value) {
		// verifyName(field);
		this.sql.append(" ");
		this.sql.append(field);
		if ((field.equals("1")) && (value instanceof String))
			append("=?", new Object[] { Integer.valueOf(Integer.parseInt(value.toString())) });
		else {
			append("=?", new Object[] { value });
		}
		return this;
	}

	public XSq append(String sqlPart, Object... params) {
		// System.out.println(this.sql);
		// System.out.println(sqlPart+"\n");
		if ((params != null) && (params.length == 1) && (params[0] != null)) {
			this.sql.append(" " + sqlPart);
			if (params[0] instanceof Integer) {
				sql.replace(this.sql.indexOf("?"), this.sql.indexOf("?") + 1, params[0].toString());
			}
			if (params[0] instanceof String) {
				sql.replace(this.sql.indexOf("?"), this.sql.indexOf("?") + 1, "'" + params[0] + "'");
			}
			return this;
		}

		this.sql.append(" " + sqlPart);
		if (params == null) {
			return this;
		}

		for (Object obj : params) {

			if (obj instanceof Integer) {
				sql.replace(this.sql.indexOf("?"), this.sql.indexOf("?") + 1, obj.toString());
			}
			if (obj instanceof String) {
				sql.replace(this.sql.indexOf("?"), this.sql.indexOf("?") + 1, "'" + obj + "'");
			}

			// System.out.println(sql);
		}

		return this;
	}

	public XSq in(String field, String values) {
		return in(field, values, true);
	}

	public XSq in(String field, XSq sub) {
		if (sub == null) {
			return ignore();
		}
		// verifyName(field);
		this.sql.append(" ");
		this.sql.append(field);
		this.sql.append(" in ");
		sub(sub);
		return this;
	}

	public XSq in(String field, String values, boolean ignoreNull) {
		if ((ignoreNull) && values.isEmpty()) {
			return ignore();
		}
		List<Object> list = null;
		if ((field.toLowerCase().endsWith("id")) && (values.matches("^\\d+(\\,\\d+)*$")))
			list = convertValues(values, Long.class);
		else {
			list = convertValues(values, String.class);
		}
		in(field, list);
		return this;
	}

	public XSq in(String field, String values, Class<?> type) {
		in(field, convertValues(values, type));
		return this;
	}

	public XSq in(String field, Object[] values) {
		return in(field, DataUtil.toList(values));
	}

	public XSq in(String field, String[] values) {
		return in(field, DataUtil.toList(values));
	}

	public XSq in(String field, Collection<?> values) {
		return in(field, values, true);
	}

	public XSq in(String field, Collection<?> values, boolean ignoreNull) {
		if ((ignoreNull) && (values == null)) {
			return ignore();
		}
		// verifyName(field);
		this.sql.append(" ");
		this.sql.append(field);
		this.sql.append(" in (");
		boolean first = true;
		boolean isID = false;
		if (field.toLowerCase().endsWith("id")) {
			isID = true;
		}
		if ((values != null) && (values.size() > 0))
			for (Iterator localIterator = values.iterator(); localIterator.hasNext();) {
				Object obj = localIterator.next();
				if (obj == null) {
					continue;
				}
				if (first)
					first = false;
				else {
					this.sql.append(",");
				}
				if ((isID) && (obj instanceof String) && (DataUtil.isLong((String) obj))) {
					obj = Long.valueOf(Long.parseLong((String) obj));
				}
				append("?", new Object[] { obj });
			}
		else {
			this.sql.append("null");
		}
		this.sql.append(")");
		return this;
	}

	public XSq as(String name) {
		// verifyName(name);
		this.sql.append(" as ").append(name);
		return this;
	}

	public XSq avg(String field) {
		// verifyName(field);
		this.sql.append("avg(");
		this.sql.append(field);
		this.sql.append(')');
		return this;
	}

	public XSq between(String field, Object start, Object end) {
		this.sql.append(" ");
		// verifyName(field);
		this.sql.append(field);
		append(" between ? and ?", new Object[] { start, end });
		return this;
	}

	public XSq comma() {
		this.sql.append(',');
		return this;
	}

	public XSq space() {
		this.sql.append(' ');
		return this;
	}

	public XSq count(String field) {
		// verifyName(field);
		this.sql.append("count(");
		this.sql.append(field);
		this.sql.append(')');
		return this;
	}

	public XSq sum(String field) {
		// verifyName(field);
		this.sql.append("sum(");
		this.sql.append(field);
		this.sql.append(')');
		return this;
	}

	public XSq delete() {
		this.sql.append("delete");
		return this;
	}

	public XSq update(String table) {
		this.sql.append("update ");
		// verifyName(table);
		this.sql.append(table);
		return this;
	}

	public XSq set() {
		this.sql.append(" set ");
		return this;
	}

	public XSq set(String field, Object value) {
		if (this.sql.toString().toLowerCase().indexOf(" set ") > 0)
			this.sql.append(",");
		else {
			this.sql.append(" set ");
		}
		// verifyName(field);
		this.sql.append(field);
		append("=?", new Object[] { value });
		return this;
	}

	public XSq exists() {
		this.sql.append(" exists");
		return this;
	}

	public XSq exists(XSq sub) {
		this.sql.append(" exists");
		sub(sub);
		return this;
	}

	public XSq ne(String field, Object value) {
		// verifyName(field);
		this.sql.append(" ");
		this.sql.append(field);
		if ((field.equals("1")) && (value instanceof String))
			append("<>?", new Object[] { Integer.valueOf(Integer.parseInt(value.toString())) });
		else {
			append("<>?", new Object[] { value });
		}
		return this;
	}

	public XSq gt(String field, Object value) {
		// verifyName(field);
		this.sql.append(" ");
		this.sql.append(field);
		append(">?", new Object[] { value });
		return this;
	}

	public XSq lt(String field, Object value) {
		// verifyName(field);
		this.sql.append(" ");
		this.sql.append(field);
		append("<?", new Object[] { value });
		return this;
	}

	public XSq ge(String field, Object value) {
		// verifyName(field);
		this.sql.append(" ");
		this.sql.append(field);
		append(">=?", new Object[] { value });
		return this;
	}

	public XSq le(String field, Object value) {
		// verifyName(field);
		this.sql.append(" ");
		this.sql.append(field);
		append("<=?", new Object[] { value });
		return this;
	}

	public XSq orderby(String orderby) {
		if ((orderby == null) || ((orderby = orderby.trim()).length() == 0)) {
			return this;
		}
		int last = 0;
		boolean nameFlag = true;
		for (int i = 0; i < orderby.length(); ++i) {
			char c = orderby.charAt(i);
			if (c == ' ') {
				if (!(nameFlag)) {
					throw new QueryException("Maybe a SQL Injection: orderby=" + orderby);
				}
				// verifyName(orderby.substring(last, i));
				last = i + 1;
				nameFlag = false;
			} else if (c == ',') {
				String str = orderby.substring(last, i).trim();
				if (nameFlag) {
					// verifyName(str);
				} else if ((str.equals("")) || (str.equalsIgnoreCase("asc")) || (str.equalsIgnoreCase("desc")))
					last = i + 1;
				else {
					throw new QueryException("Maybe a SQL Injection: orderby=" + orderby);
				}

				do
					last = ++i;
				while ((i + 1 < orderby.length()) && (orderby.charAt(i + 1) == ' '));

				nameFlag = true;
			}
		}
		if (nameFlag) {
			// verifyName(orderby.substring(last));
		} else {
			String order = orderby.substring(last);
			if ((!(order.equals(""))) && (!(order.equalsIgnoreCase("asc"))) && (!(order.equalsIgnoreCase("desc")))) {
				throw new QueryException("Maybe a SQL Injection: orderby=" + orderby);
			}
		}
		this.sql.append(" order by " + orderby);
		return this;
	}

	public XSq groupby(String field) {
		// verifyName(field);
		this.sql.append(" group by " + field);
		return this;
	}

	public XSq having() {
		this.sql.append(" having");
		return this;
	}

	


	public XInsert insert(String table) {
		List<XColumn> lists = getTableMsg(table);
		//dbConn = new Connect(databaseName);
		//conn = dbConn.getConn();
		
		this.sql.append("insert into ");
		this.sql.append(table);
		this.sql.append(" values(");

		for (int i = 1; i < lists.size(); i++) {
			this.sql.append("?,");
		}
		this.sql.append("?)");
		
		XInsert xt = null;
		try {
			System.out.println(sql.toString());
			conn.setAutoCommit(false);
			PreparedStatement cmd = conn.prepareStatement(sql.toString());
			xt = new XInsert(conn,cmd);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xt;

	}
	
	public List<XColumn> getTableMsg(String table){
		List<XColumn> list = new ArrayList<>();
		dbConn = new Connect(databaseName);
		conn = dbConn.getConn();
		try {
			String sql = "select * from " + table;

			ResultSet rs = conn.createStatement().executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			// 获取当前表格的列数
			int columns = rsmd.getColumnCount();

			for (int i = 1; i <= columns; i++) {

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

				list.add(dc);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	

	public XSq insertInto(String table, String[] columns, Object[] values) {
		if ((columns.length == 0) || (columns.length != values.length)) {
			throw new QueryException("Insert into failed:values's length is not equal columns.length!");
		}
		this.sql.append("insert into ");
		this.sql.append(table);
		this.sql.append(" (");
		for (int i = 0; i < columns.length; ++i) {
			if (i != 0) {
				this.sql.append(",");
			}
			this.sql.append(columns[i]);
		}
		this.sql.append(") values (");
		for (int i = 0; i < columns.length; ++i) {
			if (i != 0) {
				this.sql.append(",");
			}
			append("?", new Object[] { values[i] });
		}
		this.sql.append(")");
		return this;
	}

	public XSq insertInto(String table, Collection<String> columns, Collection<?> values) {
		if ((columns.size() == 0) || (columns.size() != values.size())) {
			throw new QueryException("Insert into failed:values's length is not equal columns.length!");
		}
		this.sql.append("insert into ");
		this.sql.append(table);
		this.sql.append(" (");
		boolean first = true;
		for (String column : columns) {
			if (!(first))
				this.sql.append(",");
			else {
				first = false;
			}
			append(column, new Object[0]);
		}
		this.sql.append(") values (");
		first = true;
		Iterator it = values.iterator();
		while (it.hasNext()) {
			Object value = it.next();
			if (!(first))
				this.sql.append(",");
			else {
				first = false;
			}
			append("?", new Object[] { value });
		}

		this.sql.append(")");
		return this;
	}

	public XSq like(String field, String value) {
		return like(field, value, true);
	}

	public XSq like(String field, String value, boolean ignoreNull) {
		if ((ignoreNull) && (value.isEmpty())) {
			return ignore();
		}
		// verifyName(field);
		this.sql.append(" ");
		this.sql.append(field);
		append(" like ?", new Object[] { "%" + value + "%" });
		return this;
	}

	public XSq likeRight(String field, String value) {
		return likeRight(field, value, true);
	}

	public XSq likeRight(String field, String value, boolean ignoreNull) {
		if ((ignoreNull) && (value.isEmpty())) {
			return ignore();
		}
		// verifyName(field);
		this.sql.append(" ");
		this.sql.append(field);
		append(" like ?", new Object[] { value + "%" });
		return this;
	}

	public XSq likeLeft(String field, String value) {
		return likeLeft(field, value, true);
	}

	public XSq likeLeft(String field, String value, boolean ignoreNull) {
		if ((ignoreNull) && (value.isEmpty())) {
			return ignore();
		}
		// verifyName(field);
		this.sql.append(" ");
		this.sql.append(field);
		append(" like ?", new Object[] { "%" + value });
		return this;
	}

	public XSq sub(XSq q) {
		this.sql.append(" (");
		addPart(q);
		this.sql.append(")");
		return this;
	}

	public XSq addPart(XSq wherePart) {
		if (wherePart == null) {
			return this;
		}
		this.sql.append(wherePart.getSQL());
		getParams().addAll(wherePart.getParams());
		return this;
	}

	public ArrayList<Object> getParams() {
		return this.params;
	}

	public void setParams(ArrayList<Object> list) {
		this.params = list;
	}

	public XSq add(Object... params) {
		for (Object param : params) {
			this.params.add(param);
		}
		return this;
	}

	public XSq and() {
		this.sql.append(" and");
		return this;
	}

	public XSq not() {
		this.sql.append(" not");
		return this;
	}

	public XSq or() {
		this.sql.append(" or");
		return this;
	}

	public String getSQL() {
		return this.sql.toString();
	}

	/*
	 * private void verifyName(String field) { int i1 = field.indexOf(40); if
	 * (i1 > 0) { int i2 = field.indexOf(32, i1); if (i2 > 0) { throw new
	 * QueryException("Maybe a SQL Injection: name=" + field); } } else if
	 * (field.indexOf(32) > 0) { String[] arr = field.split("\\s"); if
	 * (arr.length > 3) throw new QueryException("Maybe a SQL Injection: name="
	 * + field); if ((arr.length == 3) && (!(arr[1].equalsIgnoreCase("as"))))
	 * throw new QueryException("Maybe a SQL Injection: name=" + field); } }
	 */

	/*
	 * public boolean checkParams() { char[] arr =
	 * this.sql.toString().toCharArray(); boolean StringCharFlag = false; int
	 * count = 0; for (char c : arr) { if (c == '\'') { if (!(StringCharFlag))
	 * StringCharFlag = true; else StringCharFlag = false; } else { if ((c !=
	 * '?') || (StringCharFlag)) continue; ++count; } }
	 * 
	 * if (count != this.params.size()) { throw new QueryException("SQL has " +
	 * count + " parameters but value count is " + this.params.size()); } return
	 * true; }
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.sql);
		sb.append("\t{");
		for (int i = 0; i < this.params.size(); ++i) {
			if (i != 0) {
				sb.append(",");
			}
			Object o = this.params.get(i);
			if (o == null) {
				sb.append("null");
			} else {
				String str = this.params.get(i).toString();
				if (str.length() > 40) {
					str = str.substring(0, 37);
					sb.append(str);
					sb.append("...");
				} else {
					sb.append(str);
				}
			}
		}
		sb.append("}");
		return sb.toString();
	}

	/**
	 * ***************************************************************
	 * 
	 */

	private String databaseName = "Default";

	public XSq use(String databaseName) {
		this.databaseName = databaseName;
		return this;
	}

	public XDataTable run() {
		return execute();
	}

	public XDataTable execute() {
		// conn = dbConn.getConnection(poolName);
		dbConn = new Connect(databaseName);
		conn = dbConn.getConn();

		XDataTable dt = null;
		try {
			System.out.println("正在执行的SQL是:" + this.getSQL());
			// Q q=new Q("select * from user");
			dt = execute(this);
		} catch (Throwable e) {
			// DataAccess.log(System.currentTimeMillis(), "Error:" +
			// e.getMessage(), null, new Object[0]);
			if (e instanceof QueryException) {
				throw ((QueryException) e);
			}
			throw new QueryException(e);
		}
		return dt;
	}

	public XDataTable execute(XSq q) {

		PreparedStatement stmt = null;
		ResultSet rs = null;
		XDataTable dt = null;
		String sql = q.getSQL();
		try {

			// try {
			// sql = new String(sql.getBytes("UTF-8"), "ISO-8859-1");
			// } catch (UnsupportedEncodingException e) {
			// e.printStackTrace();
			// }

			stmt = this.prepareStatement(sql, 1003, 1007);

			rs = stmt.executeQuery();
			dt = new XDataTable(rs);
			// this.setLastSuccessExecuteTime(System.currentTimeMillis());
		} catch (SQLException e) {
			throw new QueryException(e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return dt;
	}

	protected String lastSQL = null;

	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
			throws SQLException {
		this.lastSQL = sql;
		return this.conn.prepareStatement(sql, resultSetType, resultSetConcurrency);
	}
	/*
	 * protected long lastSuccessExecuteTime = System.currentTimeMillis();
	 * public void setLastSuccessExecuteTime(long lastSuccessExecuteTime) {
	 * this.lastSuccessExecuteTime = lastSuccessExecuteTime; }
	 */

	public String getResultString() {
		dbConn = new Connect(databaseName);
		conn = dbConn.getConn();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = this.getSQL();
		Object t = null;
		try {
			/*
			 * try { sql = new String(sql.getBytes("UTF-8"), "ISO-8859-1"); }
			 * catch (UnsupportedEncodingException e) { e.printStackTrace(); }
			 */

			stmt = this.prepareStatement(sql, 1003, 1007);

			rs = stmt.executeQuery();
			if (rs.next()) {
				t = rs.getObject(1);
				System.out.println("t:" + t);
				if (t instanceof Clob) {
					// t = DBUtil.clobToString((Clob) t);
				}
				if (t instanceof Blob) {
					// t = DBUtil.blobToBytes((Blob) t);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new QueryException(e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return t.toString();
	}

	private XSq ignore() {
		String str = this.sql.toString().trim().toLowerCase();
		if (str.endsWith(" and"))
			this.sql.delete(this.sql.length() - 4, this.sql.length());
		else if (str.endsWith(" having"))
			this.sql.delete(this.sql.length() - 7, this.sql.length());
		else if (str.endsWith(" where"))
			this.sql.delete(this.sql.length() - 6, this.sql.length());
		else if (str.endsWith(" or"))
			this.sql.delete(this.sql.length() - 3, this.sql.length());
		else {
			throw new QueryException("Invalid SQL:" + this.sql);
		}
		return this;
	}

	private List<Object> convertValues(String values, Class<?> type) {
		if (values == null) {
			return null;
		}
		boolean literal = false;
		List<Object> list = new ArrayList<>();
		int last = 0;
		for (int i = 0; i < values.length(); ++i) {
			char c = values.charAt(i);
			if ((c == ',') && (!(literal))) {
				String str = values.substring(last, i).trim();
				if (str.startsWith("'")) {
					str = str.substring(1, str.length() - 1);
				}
				if (type == Integer.class)
					list.add(Integer.valueOf(Integer.parseInt(str)));
				else if (type == Long.class)
					list.add(Long.valueOf(Long.parseLong(str)));
				else if (type == Double.class)
					list.add(Double.valueOf(Double.parseDouble(str)));
				else if (type == Float.class)
					list.add(Float.valueOf(Float.parseFloat(str)));
				else {
					list.add(str);
				}
				last = i + 1;
			} else if (c == '\'') {
				literal = !(literal);
			}
		}

		if (last != values.length()) {
			String str = values.substring(last).trim();
			if (str.startsWith("'")) {
				str = str.substring(1, str.length() - 1);
				list.add(str);
			} else if (type == Integer.class) {
				list.add(Integer.valueOf(Integer.parseInt(str)));
			} else if (type == Long.class) {
				list.add(Long.valueOf(Long.parseLong(str)));
			} else if (type == Double.class) {
				list.add(Double.valueOf(Double.parseDouble(str)));
			} else if (type == Float.class) {
				list.add(Float.valueOf(Float.parseFloat(str)));
			} else {
				list.add(str);
			}
		}

		return list;
	}
}
