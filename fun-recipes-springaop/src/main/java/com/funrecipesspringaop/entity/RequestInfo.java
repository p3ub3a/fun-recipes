package com.funrecipesspringaop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class RequestInfo {
	@Id
	@GeneratedValue
	private long id;
	
	@Column(name = "failed_post_requests")
	private long postRequestFailedAttempts;

	@Column(name = "failed_get_requests")
	private long getRequestFailedAttempts;

	@Column(name = "failed_put_requests")
	private long putRequestFailedAttempts;

	@Column(name = "failed_delete_requests")
	private long deleteRequestFailedAttempts;

	@Column(name = "failed_patch_requests")
	private long patchRequestFailedAttempts;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getPostRequestFailedAttempts() {
		return this.postRequestFailedAttempts;
	}

	public void setPostRequestFailedAttempts(long postRequestFailedAttempts) {
		this.postRequestFailedAttempts = postRequestFailedAttempts;
	}

	public long getGetRequestFailedAttempts() {
		return this.getRequestFailedAttempts;
	}

	public void setGetRequestFailedAttempts(long getRequestFailedAttempts) {
		this.getRequestFailedAttempts = getRequestFailedAttempts;
	}

	public long getPutRequestFailedAttempts() {
		return this.putRequestFailedAttempts;
	}

	public void setPutRequestFailedAttempts(long putRequestFailedAttempts) {
		this.putRequestFailedAttempts = putRequestFailedAttempts;
	}

	public long getDeleteRequestFailedAttempts() {
		return this.deleteRequestFailedAttempts;
	}

	public void setDeleteRequestFailedAttempts(long deleteRequestFailedAttempts) {
		this.deleteRequestFailedAttempts = deleteRequestFailedAttempts;
	}

	public long getPatchRequestFailedAttempts() {
		return this.patchRequestFailedAttempts;
	}

	public void setPatchRequestFailedAttempts(long patchRequestFailedAttempts) {
		this.patchRequestFailedAttempts = patchRequestFailedAttempts;
	}
	
	
}
