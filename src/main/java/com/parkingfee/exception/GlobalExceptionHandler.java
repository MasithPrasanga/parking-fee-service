package com.parkingfee.exception;

import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	@ExceptionHandler({ RootException.class })
	public ResponseEntity<ExceptionResponse> handleDispatcherException(RootException exception) {
		log.info(exception.getMessage(), exception);
		ExceptionResponse exceptionResponse = ExceptionResponse.builder()
				.exceptionName(exception.getExceptionName())
				.description(exception.getDescription())
				.timeStamp(new Date())
				.httpStatusCode(exception.getHttpStatusCode())
				.build();
		return new ResponseEntity<>(exceptionResponse, exception.getHttpStatusCode());
	}

}
