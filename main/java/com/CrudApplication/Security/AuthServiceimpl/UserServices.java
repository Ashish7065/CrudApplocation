package com.CrudApplication.Security.AuthServiceimpl;

import com.CrudApplication.AuthController.AuthController;
import com.CrudApplication.AuthController.JWTRequest;
import com.CrudApplication.AuthController.JWTResponse;
import com.CrudApplication.DTO.PatientDto;
import com.CrudApplication.Entities.AppUser;
import com.CrudApplication.Entities.Hospital;
import com.CrudApplication.Entities.Patient;
import com.CrudApplication.Exception.Response.GetResponse;
import com.CrudApplication.Execption.UserNotFoundException;
import com.CrudApplication.Repositary.AppUserRepo;
import com.CrudApplication.Repositary.Hospitalrepo;
import com.CrudApplication.Repositary.Patientrepo;
import com.CrudApplication.Security.JWTHelper;
import com.CrudApplication.TokenService.TokenService;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Service
public class UserServices {

	@Autowired
	private AppUserRepo appUserRepo;

	@Autowired
	private TokenService tokenService;


	@Autowired
	private AuthenticationManager manager; // This is using for authenticate the user...........


	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JWTHelper helper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// Create-user
	public ResponseEntity<AppUser> CreateUser(@RequestBody AppUser appUser) {
		// Here use, customException
		if (appUser.getUsername() == null || appUser.getUsername().isEmpty()) {
			System.out.println("Username cannot be null or empty");
		}
		appUser.setId(appUser.getId());
		appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
		appUser.setRole(appUser.getRole());

		AppUser save = appUserRepo.save(appUser);

		return new ResponseEntity<>(save, HttpStatus.OK);
	}

	// Login process
	public String login(String username, String password) { // here use for generate the token with username
		manager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		String token = helper.generateToken(userDetails);
		tokenService.saveToken(token, username);
		return token;
	}

	// Logout process
	public ResponseEntity<String> logout(@RequestHeader("Authorization") String requestHeader) {
		if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
			String jwt = requestHeader.substring(7);
			if (tokenService.isTokenPresent(jwt)) {
				tokenService.deleteToken(jwt);
				return new ResponseEntity<>("User logged out successfully", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Token not found", HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<>("Authorization header must start with Bearer ", HttpStatus.BAD_REQUEST);
		}
	}

}
