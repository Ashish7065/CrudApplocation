package com.CrudApplication.Repositary;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


import com.CrudApplication.DTO.AppUserDto;
import com.CrudApplication.Entities.AppUser;


public interface AppUserRepo extends JpaRepository<AppUser, Long> , JpaSpecificationExecutor<AppUser>{

	AppUser save(AppUserDto appUserDtoId);

	AppUser save(long id);

	 
	 public AppUser findByusername(String username);  // For security purpose...


	



}
