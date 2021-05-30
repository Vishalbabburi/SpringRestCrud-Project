package com.luv2code.springdemo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestControllerAdvice {

	//handle exceptions here, It will work application wide
	
	//handler for CustomerNotFoundEception anywhere in the application
	@ExceptionHandler
	public ResponseEntity<CustomerErrorResponse> errorhandler(CustomerNotFoundException exc) {
		
		CustomerErrorResponse error=new CustomerErrorResponse(HttpStatus.NOT_FOUND.value(),exc.getMessage(),System.currentTimeMillis());
		
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
	
	// handler for all other general exceptions thrown by controllers in entire application
	@ExceptionHandler
	public ResponseEntity<CustomerErrorResponse> errorhandler(Exception exc) {
		
		CustomerErrorResponse error=new CustomerErrorResponse(HttpStatus.BAD_REQUEST.value(),exc.getMessage(),System.currentTimeMillis());
		
		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
	}
}
