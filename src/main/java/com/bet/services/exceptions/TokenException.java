package com.bet.services.exceptions;

public class TokenException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TokenException(String msg) {
		super(msg);
	}

	public TokenException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
