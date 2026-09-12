package com.bet.services.exceptions;

public class my4BetException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public my4BetException(String msg) {
		super(msg);
	}
	
	public my4BetException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
