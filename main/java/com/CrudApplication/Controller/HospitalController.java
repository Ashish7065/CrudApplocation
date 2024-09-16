package com.CrudApplication.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CrudApplication.DTO.HospitalDto;
import com.CrudApplication.Entities.Hospital;
import com.CrudApplication.Services.HospitalService;

import java.util.List;

@RestController
@RequestMapping("/hospital")
public class HospitalController {

	@Autowired
	private HospitalService hospitalService;

	@PostMapping
	public ResponseEntity<HospitalDto> createHospital(@RequestBody HospitalDto hospitalDto) {
		HospitalDto createdHospital = hospitalService.createHospital(hospitalDto);
		return new ResponseEntity<>(createdHospital, HttpStatus.CREATED);
	}

	@PutMapping("/update-Hospital-user/{id}")
	public ResponseEntity<HospitalDto> updateHospital(@PathVariable Long id, @RequestBody HospitalDto hospitalDto) {
		HospitalDto updatedHospital = hospitalService.updateHospital(id, hospitalDto);
		return new ResponseEntity<>(updatedHospital, HttpStatus.OK);
	}


	@GetMapping("/fetch-Hospital-user")
	public ResponseEntity<HospitalDto> getHospitalById(@RequestParam("id") @PathVariable Long id) {
		HospitalDto hospital = hospitalService.getHospitalById(id);
		return new ResponseEntity<>(hospital, HttpStatus.OK);
	}

    // For Pagination
	@GetMapping("/search")
	public Page<Hospital> getAllHospital(String search , Pageable pageable){
		return hospitalService.getAllHospital(search, pageable);
	}

	// For Native Query
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/searchdata")
	public Page<HospitalDto> getHospitals(@RequestParam(required = false) String search, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		return hospitalService.getHospitals(search, page, size);
	}



	@DeleteMapping("/delete-Hospital-user")
	public ResponseEntity<Void> deleteHospital(@RequestParam("id") @PathVariable Long id) {
		hospitalService.deleteHospital(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
