package com.parkingfee.exception;

import org.springframework.http.HttpStatus;

public class InvalidTimeRangeException extends RootException {

	private static final long serialVersionUID = 1L;

	public InvalidTimeRangeException() {
		super();
	}

	public InvalidTimeRangeException(String exceptionName, String description) {
		super(exceptionName, description, HttpStatus.BAD_REQUEST);
	}

}
