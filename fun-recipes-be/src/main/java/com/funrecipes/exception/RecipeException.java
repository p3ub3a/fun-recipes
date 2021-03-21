package com.funrecipes.exception;

import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;

public class RecipeException {
	private final String message;
	private final HttpStatus httpStatus;
	private final ZonedDateTime timestamp;
	
	public RecipeException(String message, HttpStatus httpStatus, ZonedDateTime timestamp) {
		this.message = message;
		this.httpStatus = httpStatus;
		this.timestamp = timestamp;
	}
	
	public String getMessage() {
		return message;
	}
	
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	
	public ZonedDateTime getTimestamp() {
		return timestamp;
	}
}
