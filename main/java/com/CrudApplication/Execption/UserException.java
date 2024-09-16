package com.CrudApplication.Execption;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;



@Getter
@AllArgsConstructor
@Data

public class UserException {
	
	private final  String message;
	private final Throwable throwable;  
	private final HttpStatus httpStatus;

	
}
