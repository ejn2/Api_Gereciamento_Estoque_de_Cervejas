package com.stock.beerstock.handle;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.stock.beerstock.custom.ValidationsMessages;
import com.stock.beerstock.exceptions.BeerAlreadyExistsException;
import com.stock.beerstock.exceptions.BeerMessageError;
import com.stock.beerstock.exceptions.BeerNotFoundException;

import net.bytebuddy.dynamic.loading.PackageDefinitionStrategy.Definition.Undefined;

@RestControllerAdvice
public class HandleExceptions extends ResponseEntityExceptionHandler{
	
	
	
	//========================== [ Beer Not Found ] ==========================
	
	@ExceptionHandler({ BeerNotFoundException.class })
	public ResponseEntity<?> handleBeerNotFoundException(BeerNotFoundException ex) {
		
		BeerMessageError error = new BeerMessageError(
				ex.getMessage(),
				HttpStatus.NOT_FOUND.value(),
				LocalDateTime.now()
				);
		
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
		
	}
	
	
	
	//========================== [ Beer Already Exists ] ==========================
	
	@ExceptionHandler({ BeerAlreadyExistsException.class })
	public ResponseEntity<?> handleBeerAlreadyExistsException(BeerAlreadyExistsException ex) {
		
		BeerMessageError error = new BeerMessageError(
					ex.getMessage(),
					HttpStatus.BAD_REQUEST.value(),
					LocalDateTime.now()
				);
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		
	}
	

	
	//========================== [ Method not Allowed ] ==========================
	
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
			HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		
		BeerMessageError error = new BeerMessageError(
				ex.getMessage(),
				status.value(),
				LocalDateTime.now()
			);
		
		return new ResponseEntity<>(error, status);

	}
	
	
	
	//========================== [ JSON parse error ] ==========================
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(
			HttpMessageNotReadableException ex,
			HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		String message = "JSON Error.";
		
		BeerMessageError error = new BeerMessageError(
				message,
				status.value(),
				LocalDateTime.now()
			);
		
		return new ResponseEntity<>(error, status);

	}

	
	
	//========================== [ Validations ] ==========================

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Map<String, String> errors = new HashMap();
		
		ex.getFieldErrors().forEach(e ->
			errors.put(e.getField(), e.getDefaultMessage())
				);
		
		
		ValidationsMessages messages = new ValidationsMessages(errors, status.value());
		
		return new ResponseEntity<>(messages, HttpStatus.BAD_REQUEST);
	}
	
	
	
}
