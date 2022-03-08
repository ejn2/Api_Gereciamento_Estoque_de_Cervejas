package com.stock.beerstock.custom;

import java.util.Map;

public class ValidationsMessages {

	private Map<String, String> errors;
	private Integer Status;


	public ValidationsMessages(Map<String, String> errors, Integer status) {
		this.errors = errors;
		Status = status;
	}

	public Map<String, String> getErrors() {
		return this.errors;
	}

	public Integer getStatus() {
		return Status;
	}
	
	
	
	
}
