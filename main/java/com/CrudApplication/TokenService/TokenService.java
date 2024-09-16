package com.CrudApplication.TokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CrudApplication.Entity2.Token;
import com.CrudApplication.repo.Tokenrepo;


@Service
public class TokenService {

	
	 @Autowired
	    private Tokenrepo tokenrepo;
	 

	    public void saveToken(String token, String username) {
	    Token newToken = new Token();
	        newToken.setToken(token);
	        newToken.setUsername(username);
	        tokenrepo.save(newToken);
	    }

	    public void deleteToken(String token) {
	    	tokenrepo.deleteByToken(token);
	    }


	    public boolean isTokenPresent(String token) {
	        return tokenrepo.findByToken(token).isPresent();
	    }
}
