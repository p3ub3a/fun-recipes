package com.funrecipes.exception;

import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RecipeExceptionHandler {

	@ExceptionHandler(value = {MaxRequestsReachedException.class})
	public ResponseEntity<Object> handleException(MaxRequestsReachedException e){
		HttpStatus badRequest = HttpStatus.BAD_REQUEST;
		
		RecipeException re = new RecipeException(e.getMessage(), 
				e.getTimer(),
				badRequest, 
				ZonedDateTime.now());
		
		return new ResponseEntity<>(re, badRequest);
	}
}
