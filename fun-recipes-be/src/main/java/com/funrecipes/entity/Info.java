package com.funrecipes.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Info {
	@Id
	@GeneratedValue
	private long id;
	
	private String methodName;
	
	private long nrOfCalls;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public long getNrOfCalls() {
		return nrOfCalls;
	}

	public void setNrOfCalls(long nrOfCalls) {
		this.nrOfCalls = nrOfCalls;
	}
	
	
}
