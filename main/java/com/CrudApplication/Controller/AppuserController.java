package com.CrudApplication.Controller;

import java.util.List;
import java.util.Optional;

import com.CrudApplication.Entities.Hospital;
import com.CrudApplication.Entities.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CrudApplication.DTO.AppUserDto;
import com.CrudApplication.Entities.AppUser;
import com.CrudApplication.Services.AppUserService;

@RestController
@RequestMapping("/appuser")
public class AppuserController {

	@Autowired
	private AppUserService appUserService;

	@PostMapping
	public ResponseEntity<AppUserDto> createAppUser(@RequestBody AppUserDto appUserDto) {
		AppUserDto createdAppUser = appUserService.createAppUser(appUserDto);
		return new ResponseEntity<>(createdAppUser, HttpStatus.CREATED);
	}

	@PutMapping("/update-AppUser/{id}")
	public ResponseEntity<AppUserDto> updateAppUser(@PathVariable Long id, @RequestBody AppUserDto appUserDto) {
		AppUserDto updatedAppUser = appUserService.updateAppUser(id, appUserDto);
		return new ResponseEntity<>(updatedAppUser, HttpStatus.OK);
	}

	@GetMapping("/fetch-AppUser-user")
	public ResponseEntity<AppUserDto> getAppUserById(@RequestParam("id") @PathVariable Long id) {
		AppUserDto appUser = appUserService.getAppUserById(id);
		return new ResponseEntity<>(appUser, HttpStatus.OK);
	}

	@GetMapping("/search")
	public Page<AppUser> getAllAppUsers(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(required = false) String search) {
		return appUserService.getAllAppUsers(search , page, size);
	}

	@DeleteMapping("/delete-AppUser")
	public ResponseEntity<Void> deleteAppUser(@RequestParam("id") @PathVariable Long id) {
		appUserService.deleteAppUser(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}


