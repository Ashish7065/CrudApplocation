package com.CrudApplication.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.CrudApplication.Entity2.Token;



public interface Tokenrepo extends MongoRepository<Token, String> {

    Optional<Token> findByToken(String token);

	 
	 void deleteByToken(String token);


	


	 
}