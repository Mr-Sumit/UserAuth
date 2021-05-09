package com.authentication.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;

public class ApiError {
	private HttpStatus status;
	private Date timeStamp;
	private String message;
	
	public ApiError() {
		super();
	}

	public ApiError(HttpStatus status, Date timeStamp, String message) {
		super();
		this.status = status;
		this.timeStamp = timeStamp;
		this.message = message;
	}

	/**
	 * @return the status
	 */
	public HttpStatus getStatus() {
		return status;
	}

	/**
	 * @return the timeStamp
	 */
	public Date getTimeStamp() {
		return timeStamp;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
}