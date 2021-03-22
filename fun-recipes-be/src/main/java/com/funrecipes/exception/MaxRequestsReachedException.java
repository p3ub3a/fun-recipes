package com.funrecipes.exception;

public class MaxRequestsReachedException extends Exception {
	private int timer;
	
	public MaxRequestsReachedException(String message, int timer) {
		super(message);
		this.timer = timer;
	}
	
	public MaxRequestsReachedException(String message, Throwable e, int timer) {
		super(message, e);
		this.timer = timer;
	}
	
	public int getTimer() {
		return timer;
	}
}
