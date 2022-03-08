package com.stock.beerstock.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BeerAlreadyExistsException extends Exception{

	public BeerAlreadyExistsException(String message) {
		super(message);
	}
	
}
