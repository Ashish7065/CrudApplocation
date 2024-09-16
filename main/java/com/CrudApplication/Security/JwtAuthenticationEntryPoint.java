package com.CrudApplication.Security;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  //It will set status that one is unauthorized user try to access it...
		PrintWriter Writer = response.getWriter();  // we will get the response , during unauthorized user try to access the file....
		Writer.println("Access denied : " + authException.getMessage());  // Get the particular message during unauthorized user try to access the file...
		
	}


}

// HttpServletRequest request (Here user try to access the data using credential, that person request the to access)
// HttpServletResponse response (Here , we will get response after getting the feedback..)