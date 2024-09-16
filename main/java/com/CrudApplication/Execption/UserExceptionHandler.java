package com.CrudApplication.Execption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



@ControllerAdvice //(Use for globally exception)
public class UserExceptionHandler {
	
	@ExceptionHandler(value = {UserNotFoundException.class})  //this is used for handling exception in specific handler class or method...
	public ResponseEntity<Object> HandlerException(UserNotFoundException userNotFoundException) {

		UserException userException = new UserException(userNotFoundException.getMessage(),
				userNotFoundException.getCause(), HttpStatus.NOT_FOUND);

		return new ResponseEntity<>(userException, HttpStatus.NOT_FOUND);
	}
	
}

