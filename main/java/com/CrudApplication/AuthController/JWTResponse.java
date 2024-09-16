package com.CrudApplication.AuthController;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class JWTResponse {

	//This will give the response after verify the user deatils....
	
	private  String jwttoken;
	private  String username;
	
	


}