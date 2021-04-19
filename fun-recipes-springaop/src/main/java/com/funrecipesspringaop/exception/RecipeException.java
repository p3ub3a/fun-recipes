package com.funrecipesspringaop.exception;

import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;

public class RecipeException {
	private final String message;
	private final int timer;
	private final HttpStatus httpStatus;
	private final ZonedDateTime timestamp;
	
	public RecipeException(String message, int timer, HttpStatus httpStatus, ZonedDateTime timestamp) {
		this.message = message;
		this.timer = timer;
		this.httpStatus = httpStatus;
		this.timestamp = timestamp;
	}
	
	public String getMessage() {
		return message;
	}
	public int getTimer() {
		return timer;
	}
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	
	public ZonedDateTime getTimestamp() {
		return timestamp;
	}
}
