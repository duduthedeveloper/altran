package com.altran.infrastructure.exceptions;

import org.springframework.http.HttpStatus;

public class ExternalQueryException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final HttpStatus code;

	public ExternalQueryException(final HttpStatus code) {
		super(code.toString());
		this.code = code;
	}

	public HttpStatus getCode() {
		return code;
	}

}
