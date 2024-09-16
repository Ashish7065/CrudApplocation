package com.CrudApplication.Entity2;



import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.jsonwebtoken.JwtParser;
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
@Document(collection = "tokens")
public class Token {

	//This will give the response after verify the user deatils....
	
	private  String token;
	private  String username;
	
	


}