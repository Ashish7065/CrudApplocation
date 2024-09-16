package com.CrudApplication.Services;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.CrudApplication.DTO.PatientDto;

import com.CrudApplication.Entities.Patient;

public interface PatientServicess {

	//Native query
	public Page<PatientDto> findBySearchwithdoctorIdorhospitalId(String search, List<Long> doctorId, List<Long> hospitalId, int page, int size );

		//Pagination
		public Page<Patient> getAllPatients( String search , int page, int size);

	public PatientDto getPatientById(long patientDtoId);
	
	public PatientDto createPatient(PatientDto patientDto );
		
	public PatientDto  deletePatient(long patientDtoId);
	
	 public PatientDto updatePatient( PatientDto patientDTO);

	
}
