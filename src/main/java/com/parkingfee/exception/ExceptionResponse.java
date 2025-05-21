package com.parkingfee.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {

	private String exceptionName;
	private String description;
	private HttpStatus httpStatusCode;
	private Date timeStamp;

}
