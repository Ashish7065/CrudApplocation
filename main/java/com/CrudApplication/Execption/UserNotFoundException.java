package com.CrudApplication.Execption;



@SuppressWarnings("serial")
public class UserNotFoundException extends RuntimeException { 

	public UserNotFoundException(String message, Throwable cause) { 
		super(message, cause);
	}

	public UserNotFoundException(String message) { 
		super(message);
	}

}
