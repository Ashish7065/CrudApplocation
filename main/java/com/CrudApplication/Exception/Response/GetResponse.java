package com.CrudApplication.Exception.Response;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.CrudApplication.AuthController.JWTResponse;

public class GetResponse {
	
	
	public static ResponseEntity<?> responseGetUser(String message, HttpStatus httpStatus){
		
		Map<String , Object> respose = new HashMap<>();  // Here, Use map to put given data to (response).........
		respose.put("message" , message);
		respose.put("httpstatus" , httpStatus);
		

		return new ResponseEntity<>(respose , httpStatus.OK);
	}
	
	
	//This is for Get response in logout case
	public static ResponseEntity<Object> responseLogoutUser(String message, HttpStatus httpStatus) {

		Map<String, Object> respose = new HashMap<>(); // Here, Use map to put given data to (response).........
		respose.put("message", message);
		respose.put("httpstatus", httpStatus);
	

		return new ResponseEntity<>(respose, httpStatus.OK);
	}
	

}

