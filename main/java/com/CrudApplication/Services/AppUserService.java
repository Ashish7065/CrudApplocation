package com.CrudApplication.Services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.CrudApplication.DTO.AppUserDto;
import com.CrudApplication.DTO.StaffDto;
import com.CrudApplication.Entities.AppUser;
import com.CrudApplication.Entities.Hospital;
import com.CrudApplication.Entities.Staff;

public interface AppUserService {
	
	 public AppUserDto createAppUser(AppUserDto appUserDto );
	 
	   public AppUserDto updateAppUser(Long id, AppUserDto appUserDto);
	   
	   public AppUserDto getAppUserById(Long id);

	public Page<AppUser> getAllAppUsers(String search , int page, int size);
	   
	   public void deleteAppUser(Long id);
}
