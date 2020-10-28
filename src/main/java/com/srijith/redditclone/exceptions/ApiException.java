package com.srijith.redditclone.exceptions;


import java.time.Instant;

import org.springframework.http.HttpStatus;



public class ApiException {
	
	private final String message;
	private final Throwable throwable;
	private final HttpStatus httpStatus;
	private final Instant dateTime;
	
	public ApiException(String message,
						Throwable throwable,
						HttpStatus httpstatus) {
		this.message = message;
		this.throwable = throwable;
		this.httpStatus = httpstatus;
		this.dateTime = Instant.now();
	}
	
	public String getMessage() {
		return message;
	}
	
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	
	public Instant getDateTime() {
		return dateTime;
	}
	
	public Throwable getThrowable() {
		return throwable;
	}
}
