package com.CrudApplication.AuthController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CrudApplication.DTO.HospitalDto;
import com.CrudApplication.DTO.PatientDto;
import com.CrudApplication.Entities.AppUser;
import com.CrudApplication.Entities.Hospital;
import com.CrudApplication.Entities.Patient;
import com.CrudApplication.Exception.Response.GetResponse;
import com.CrudApplication.Security.AuthServiceimpl.UserServices;


@RestController
@RequestMapping("/auth")
public class AuthController {


	@Autowired
	private UserServices userServiced;
	
	
	@PostMapping("/login")
	public ResponseEntity<JWTResponse> login(@RequestBody JWTRequest request) {
		String token = userServiced.login(request.getUsername(), request.getPassword()); //Get userdetails from appuserservice then send to token
		JWTResponse response = JWTResponse.builder().jwttoken(token).username(request.getUsername()).build(); //Will get response(token, username)
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/create-user")
	public ResponseEntity<?> CreateUser(@RequestBody AppUser appUser) {
		this.userServiced.CreateUser(appUser);
		return GetResponse.responseGetUser("New User registered successfully with username : " + appUser.getUsername(),
				HttpStatus.OK);
	}
	
	@PostMapping("/logout")
	public ResponseEntity<String> logout(@RequestHeader("Authorization") String requestHeader) {
		return userServiced.logout(requestHeader);
	}
	

}


