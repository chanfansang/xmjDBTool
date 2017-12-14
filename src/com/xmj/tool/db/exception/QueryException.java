package com.xmj.tool.db.exception;

public class QueryException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public QueryException(String message) {
		super(message);
	}

	public QueryException(Throwable e) {
		super(e);
	}
}