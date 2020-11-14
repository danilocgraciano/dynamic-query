package com.query.dynamic.exception;

@SuppressWarnings("serial")
public class InvalidOperationException extends RuntimeException {

	public InvalidOperationException(String errorMessage) {
		super(errorMessage);
	}

}
