package com.CrudApplication.Controller;

import java.util.List;

import com.CrudApplication.DTO.StaffDto;
import com.CrudApplication.Entities.Hospital;
import com.CrudApplication.Entities.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import com.CrudApplication.DTO.PatientDto;
import com.CrudApplication.Entities.Patient;
import com.CrudApplication.Services.PatientServicess;

@RestController
@RequestMapping("/patient")
public class PatientController {

	@Autowired
	private PatientServicess patientServicess;

	@PostMapping
	public ResponseEntity<PatientDto> createPatient(@RequestBody PatientDto patientDTO ) {
		PatientDto createdPatient = patientServicess.createPatient(patientDTO );
		return new ResponseEntity<>(createdPatient, HttpStatus.CREATED);
	}

	@PutMapping("/update-Patient-user/{id}")
	public ResponseEntity<PatientDto> updatePatient(@PathVariable Long id, @RequestBody PatientDto patientDTO) {
		patientDTO.setId(id);
		PatientDto updatedPatient = patientServicess.updatePatient(patientDTO);
		return new ResponseEntity<>(updatedPatient, HttpStatus.OK);
	}

	@GetMapping("/fetch-Patient-user")
	public ResponseEntity<PatientDto> getPatientById(@RequestParam("id") @PathVariable Long id) {
		PatientDto patientDTO = patientServicess.getPatientById(id);
		return new ResponseEntity<>(patientDTO, HttpStatus.OK);
	}


	@GetMapping("/search")
	public Page<Patient> getAllPatients(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(required = false) String search) {

		return patientServicess.getAllPatients(search , page , size);
	}


	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/searchdata")
	public Page<PatientDto> findBySearchwithdoctorIdorhospitalId(@RequestParam(required = false) String search,  @RequestParam(required = false) List<Long> hospitalId,
																 @RequestParam(required = false) List<Long> doctorId,  @RequestParam(defaultValue = "0") int page,
																 @RequestParam(defaultValue = "10") int size) {
		return patientServicess.findBySearchwithdoctorIdorhospitalId(search, hospitalId, doctorId, page, size);

	}


	@DeleteMapping("/delete-Patient-user")
	public ResponseEntity<Void> deletePatient(@RequestParam("id") @PathVariable Long id) {
		patientServicess.deletePatient(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
