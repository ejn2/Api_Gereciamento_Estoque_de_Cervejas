package com.stock.beerstock.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BeerNotFoundException extends Exception{

	public BeerNotFoundException(String message) {
		super(message);
	}
	
}
