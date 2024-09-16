package com.CrudApplication.AuthorityController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CrudApplication.AuthorityService.AuthorityService;
import com.CrudApplication.Entities.Hospital;
import com.CrudApplication.Entities.Patient;

@RestController
@RequestMapping("/api")
public class AuthorityController {

	@Autowired
	private AuthorityService authorityService;
	

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin")
	public ResponseEntity<List<Hospital>> getAllHospitals() {
		List<Hospital> hospitals = authorityService.getAllHospitals();
		return ResponseEntity.ok(hospitals);

	}

	@PreAuthorize("hasRole('USER')")
	@GetMapping("/user")
	public ResponseEntity<List<Patient>> getAllPatients() {
		List<Patient> patients = authorityService.getAllPatients();
		return ResponseEntity.ok(patients);
	}

}
