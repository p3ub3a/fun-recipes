package com.funrecipes.aop;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.funrecipes.dto.RecipeDTO;
import com.funrecipes.exception.MaxRequestsReachedException;
import com.funrecipes.service.RecipesServiceImpl;

public aspect Monitoring {
	private static final int MAX_POST_REQUESTS = 1;
	private static final int MIN_TIME_POST_REQUEST = 30;
	
	private static final int MAX_GET_REQUESTS = 3;
	private static final int MIN_TIME_GET_REQUEST = 30;
	
	private static List<LocalDateTime> postDateRequests = new ArrayList<LocalDateTime>();
	private static List<LocalDateTime> getDateRequests = new ArrayList<LocalDateTime>();
	
	private Logger logger = LoggerFactory.getLogger(Monitoring.class);
	
	pointcut addRecipes(): call (* com.funrecipes.service.RecipesService.addRecipes(..));
	
	pointcut getRecipes(): call (* com.funrecipes.service.RecipesService.getRecipes(..));
	
	before() throws MaxRequestsReachedException: addRecipes(){
		checkNumberOfCalls(MAX_POST_REQUESTS, MIN_TIME_POST_REQUEST, postDateRequests);
		logger.info("Calling method addRecipes()...");
	}
	
	String around(RecipesServiceImpl recipesServiceImpl, List<RecipeDTO> recipeDTOs): addRecipes() 
	&& target(recipesServiceImpl) 
	&& args(recipeDTOs){
		try {
			logger.info("Trying to store recipes... ");
			proceed(recipesServiceImpl, recipeDTOs);
		} catch(IllegalArgumentException e) {
			logger.warn("Execution failed, exception message: " + e.getMessage());
			
			return "bad request, make sure each recipe has at least one ingredient";
		}
		
		logger.info("Stored recipes");
		return "request successful";
	}
	
	List<RecipeDTO> around(RecipesServiceImpl recipesServiceImpl) throws MaxRequestsReachedException: getRecipes() 
	&& target(recipesServiceImpl) {
		List<RecipeDTO> recipeDTOs = null;
		try {
			logger.info("Trying to get recipes... ");
			checkNumberOfCalls(MAX_GET_REQUESTS, MIN_TIME_GET_REQUEST, getDateRequests);
			recipeDTOs = proceed(recipesServiceImpl);
		} catch(IllegalArgumentException e) {
			logger.warn("Execution failed, exception message: " + e.getMessage());
		}
		logger.info("Got recipes");
		return recipeDTOs;
	}
	
	private void checkNumberOfCalls(int numberOfRequests, int minTime, List<LocalDateTime> dateRequests) throws MaxRequestsReachedException {
		int counter = 0;
		
		System.out.println(dateRequests);
		if(dateRequests.size() >= numberOfRequests) {
			for (Iterator<LocalDateTime> iterator = dateRequests.iterator(); iterator.hasNext();) {
				LocalDateTime date = iterator.next();
				if(date.isAfter(LocalDateTime.now().minus(minTime, ChronoUnit.SECONDS))) {
					counter++;
				} else {
					iterator.remove();
				}
			}
			
			System.out.println(counter);
			if(counter >= numberOfRequests) {
				throw new MaxRequestsReachedException("Max calls reached (" + numberOfRequests + "), try again in " + minTime +" seconds");
			}else {
				dateRequests.add(LocalDateTime.now());
			}
		}else {
			dateRequests.add(LocalDateTime.now());
			System.out.println(dateRequests);
		}
	}
}
