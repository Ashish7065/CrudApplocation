package com.CrudApplication.Security;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.CrudApplication.Entities.AppUser;
import com.CrudApplication.Execption.UserNotFoundException;
import com.CrudApplication.Repositary.AppUserRepo;


@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private AppUserRepo appUserRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		AppUser appuser = this.appUserRepo.findByusername(username);
		
		if(appuser == null) {
			throw new UserNotFoundException("User not Found");
		}
		
		AppUserDetailsAuthority appUserDetailsAuthority = new AppUserDetailsAuthority(appuser);
		
		return appUserDetailsAuthority;
		
		
	}

}
