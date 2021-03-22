package com.funrecipes.aop;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
	private static final int MIN_TIME_GET_REQUEST = 35;
	
	private static List<Long> postDateRequests = new ArrayList<Long>();
	private static List<Long> getDateRequests = new ArrayList<Long>();
	
	private static int failedAttempts = 0;
	
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
			logger.warn("Execution failed, number of failed attemts: " + failedAttempts + " ; exception message: " + e.getMessage());
			failedAttempts++;
			return e.getMessage();
		}
		
		logger.info("Stored recipes");
		return "request successful";
	}
	
	List<RecipeDTO> around(RecipesServiceImpl recipesServiceImpl) throws MaxRequestsReachedException: getRecipes() 
	&& target(recipesServiceImpl) {
		List<RecipeDTO> recipeDTOs = null;
		checkNumberOfCalls(MAX_GET_REQUESTS, MIN_TIME_GET_REQUEST, getDateRequests);
		recipeDTOs = proceed(recipesServiceImpl);
		return recipeDTOs;
	}
	
	private void checkNumberOfCalls(int numberOfRequests, int minTime, List<Long> dateRequests) throws MaxRequestsReachedException {
		int counter = 0;
		int retryTime = 0;
		
		long now = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
		System.out.println(dateRequests);
		if(dateRequests.size() >= numberOfRequests) {
			for (Iterator<Long> iterator = dateRequests.iterator(); iterator.hasNext();) {
				Long date = iterator.next();
				int timeDifference = (int) (now - date);
				if(timeDifference < minTime) {
					if(retryTime == 0) retryTime = timeDifference;
					retryTime = timeDifference > retryTime ? timeDifference : retryTime;
					counter++;
				} else {
					iterator.remove();
				}
			}
			
			if(counter >= numberOfRequests) {
				throw new MaxRequestsReachedException("Max calls reached (" + numberOfRequests + "), please try again in ", minTime - retryTime);
			}else {
				dateRequests.add(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
			}
		}else {
			dateRequests.add(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
			System.out.println(dateRequests);
		}
	}
}
