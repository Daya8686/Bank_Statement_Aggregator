package com.BankStatementAggregator.errorhandiling;

import org.springframework.http.HttpStatus;

public class BankServiceExceptionHandler extends RuntimeException {

	private HttpStatus httpStatus;
	
	public BankServiceExceptionHandler (String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatus=httpStatus;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
	
}
