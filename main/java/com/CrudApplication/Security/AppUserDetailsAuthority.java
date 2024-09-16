package com.CrudApplication.Security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.CrudApplication.Entities.AppUser;

public class AppUserDetailsAuthority implements UserDetails {

	private AppUser appuser;
	
	
	public AppUserDetailsAuthority(AppUser appuser) {
		super();
		this.appuser = appuser;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(appuser.getRole());
		return List.of(simpleGrantedAuthority);
	}

	@Override
	public String getPassword() {
		return appuser.getPassword();
	}

	@Override
	public String getUsername() {
		return appuser.getUsername();
	}
	

}
