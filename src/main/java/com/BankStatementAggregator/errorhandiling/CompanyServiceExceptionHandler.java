package com.BankStatementAggregator.errorhandiling;

import org.springframework.http.HttpStatus;

public class CompanyServiceExceptionHandler extends RuntimeException {

	private HttpStatus httpStatus;
	
	public CompanyServiceExceptionHandler (String message, HttpStatus httpStatus) {
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
