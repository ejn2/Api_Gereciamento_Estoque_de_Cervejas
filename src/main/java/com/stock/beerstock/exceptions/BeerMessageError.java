package com.stock.beerstock.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class BeerMessageError {
	
	private String message;
	private Integer statusCode;
	private LocalDateTime timesTamps;
	
	public BeerMessageError(String message, Integer statusCode, LocalDateTime timesTamps) {
		this.message = message;
		this.statusCode = statusCode;
		this.timesTamps = timesTamps;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public LocalDateTime getTimesTamps() {
		return timesTamps;
	}

	public void setTimesTamps(LocalDateTime timesTamps) {
		this.timesTamps = timesTamps;
	}
	
	
	
	
}
