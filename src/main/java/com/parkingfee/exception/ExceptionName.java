package com.parkingfee.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionName {

	INVALID_TIME_RANGE_EXCEPTION("Invalid Time Range Exception");

	private String exceptionName;

}
