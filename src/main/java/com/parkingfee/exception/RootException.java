package com.parkingfee.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class RootException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String exceptionName;
	private String description;
	private HttpStatus httpStatusCode;

	public RootException() {
		super();
	}

	public RootException(String exceptionName, String description, HttpStatus httpStatusCode) {
		super();
		this.exceptionName = exceptionName;
		this.description = description;
		this.httpStatusCode = httpStatusCode;
	}

}
