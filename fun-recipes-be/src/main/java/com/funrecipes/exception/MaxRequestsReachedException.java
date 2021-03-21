package com.funrecipes.exception;

public class MaxRequestsReachedException extends Exception {
	public MaxRequestsReachedException(String message) {
		super(message);
	}
	
	public MaxRequestsReachedException(String message, Throwable e) {
		super(message, e);
	}
}
