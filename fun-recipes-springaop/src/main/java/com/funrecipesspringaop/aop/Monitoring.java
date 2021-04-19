package com.funrecipesspringaop.aop;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.funrecipesspringaop.entity.RequestInfo;
import com.funrecipesspringaop.exception.MaxRequestsReachedException;
import com.funrecipesspringaop.repository.MonitoringRepository;

@Aspect
@Component
public class Monitoring {
	private static final int MAX_POST_REQUESTS = 1;
	private static final int MIN_TIME_POST_REQUEST = 30;
	
	private static final int MAX_GET_REQUESTS = 3;
	private static final int MIN_TIME_GET_REQUEST = 35;

	private static final String GET = "GET";
	private static final String POST = "POST";
	private static final String PUT = "PUT";
	private static final String PATCH = "PATCH";
	private static final String DELETE = "DELETE";
	
	private static List<Long> postDateRequests = new ArrayList<Long>();
	private static List<Long> getDateRequests = new ArrayList<Long>();

	private MonitoringRepository monitoringRepository;
	
	private Logger logger = LoggerFactory.getLogger(Monitoring.class);
	
	@Autowired
	Monitoring(MonitoringRepository monitoringRepository){
		this.monitoringRepository = monitoringRepository;
	}

	@Pointcut("execution(* com.funrecipesspringaop.service.RecipesServiceImpl.addRecipes(..))")
	private void postRecipes() {}

	@Pointcut("execution(* com.funrecipesspringaop.service.RecipesServiceImpl.getRecipes())")
	private void getRecipes() {}
	
	@Before("postRecipes()")
	public void validateAddRecipes() throws MaxRequestsReachedException{
		checkNumberOfCalls(POST, MAX_POST_REQUESTS, MIN_TIME_POST_REQUEST, postDateRequests);
		logger.info("Calling method addRecipes()...");
	}

	@Before("getRecipes()")
	public void validateGetRecipes() throws MaxRequestsReachedException{
		checkNumberOfCalls(GET, MAX_GET_REQUESTS, MIN_TIME_GET_REQUEST, getDateRequests);
		logger.info("Calling method getRecipes()...");
	}

	@After(value = "execution(* com.funrecipesspringaop.service.RecipesServiceImpl.*(..))", argNames = "joinPoint")
	public void logRecipes(JoinPoint joinPoint) throws MaxRequestsReachedException{
		logger.info("Successfully called method :" + joinPoint.getSignature().toShortString() + "; with arguments: " + Arrays.asList(joinPoint.getArgs()));
	}
	
	@Transactional
	private void checkNumberOfCalls(String type, int numberOfRequests, int minTime, List<Long> dateRequests) throws MaxRequestsReachedException {
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
				persistFailedAttempt(type);
				throw new MaxRequestsReachedException("Max "+ type + " requests reached (" + numberOfRequests + "), please try again in ", minTime - retryTime);
			}else {
				dateRequests.add(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
			}
		}else {
			dateRequests.add(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
			System.out.println(dateRequests);
		}
	}

	private void persistFailedAttempt(String type) {
		RequestInfo requestInfo;
		if(monitoringRepository.findAll().size() == 0){
			requestInfo = new RequestInfo();
			monitoringRepository.save(requestInfo);
		}else {
			requestInfo = monitoringRepository.findFirstByOrderByGetRequestFailedAttemptsAsc();
		}
		switch(type){
			case GET:
				requestInfo.setGetRequestFailedAttempts(requestInfo.getGetRequestFailedAttempts() + 1);
				break;
			case POST:
				requestInfo.setPostRequestFailedAttempts(requestInfo.getPostRequestFailedAttempts() + 1);
				break;
			case PUT:
				requestInfo.setPutRequestFailedAttempts(requestInfo.getPutRequestFailedAttempts() + 1);
				break;
			case PATCH:
				requestInfo.setPatchRequestFailedAttempts(requestInfo.getPatchRequestFailedAttempts() + 1);
				break;
			case DELETE:
				requestInfo.setDeleteRequestFailedAttempts(requestInfo.getDeleteRequestFailedAttempts() + 1);
				break;
			default:
		}
		monitoringRepository.save(requestInfo);
		logger.info("Saved failed " + type + " request");
	}
}
