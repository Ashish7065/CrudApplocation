package com.CrudApplication.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.CrudApplication.DTO.StaffDto;
import com.CrudApplication.Entities.Staff;
import com.CrudApplication.Repositary.Staffrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authorization.method.AuthorizeReturnObject;
import org.springframework.stereotype.Service;

import com.CrudApplication.DTO.AppUserDto;
import com.CrudApplication.DTO.PatientDto;
import com.CrudApplication.Entities.AppUser;
import com.CrudApplication.Entities.Hospital;
import com.CrudApplication.Entities.Patient;
import com.CrudApplication.Repositary.AppUserRepo;
import com.CrudApplication.Repositary.Hospitalrepo;
import com.CrudApplication.Repositary.Patientrepo;
import com.CrudApplication.Services.PatientServicess;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;



@Service
public class PatientServiceImpl implements PatientServicess {

	@Autowired
	private Patientrepo Patientrepo;

	@Autowired
	private Staffrepo staffrepo;

	@Autowired
	private Hospitalrepo hospitalrepo;

	@Autowired
	private AppUserRepo appUserRepo;

	private Page<Patient> all;


	//Pagination
	public Page<Patient> getAllPatients( String search , int page, int size) {

		Pageable pageable = PageRequest.of(page, size);

		Specification<Patient> specification = new Specification<Patient>() {
			@Override
			public Predicate toPredicate(Root<Patient> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if (search != null && !search.isEmpty()) {
					predicates.add(criteriaBuilder.or(
							criteriaBuilder.like(root.get("name"), "%" + search + "%"),
							criteriaBuilder.like(root.get("city"), "%" + search + "%"),
							criteriaBuilder.like(root.get("address"), "%" + search + "%"),
							criteriaBuilder.like(root.get("state"), "%" + search + "%")
					));
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
			}
		};

		return Patientrepo.findAll(specification, pageable);
	}


	//Native query
	public Page<PatientDto> findBySearchwithdoctorIdorhospitalId(String search, List<Long> doctorId, List<Long> hospitalId, int page, int size) {

		Pageable pageable = PageRequest.of(page, size);

		// Convert lists to arrays, handle null lists by passing empty arrays  ((returns an empty array (new Long[0]) instead of a null array.))
		Long[] doctorIdArray = (doctorId != null && !doctorId.isEmpty()) ? doctorId.toArray(new Long[0]) : new Long[0];
		Long[] hospitalIdArray = (hospitalId != null && !hospitalId.isEmpty()) ? hospitalId.toArray(new Long[0]) : new Long[0];

		Page<Patient> result;

		if (search == null && hospitalId == null && doctorId == null) {
			result = Patientrepo.findAll(pageable);
		} else {

			if (search == null) {
				search = "";
			}
			result = Patientrepo.findBySearchwithdoctorIdorhospitalId(search, List.of(hospitalIdArray), List.of(doctorIdArray), pageable);
		}
		List<PatientDto> patientList = result.getContent().stream().map(patient -> new PatientDto(patient.getId(), patient.getName(), patient.getCondition(), patient.getTreatment(), patient.getHospital().getId(), patient.getAppUser().getId())).collect(Collectors.toList());

		return new PageImpl<>(patientList, pageable, result.getTotalElements());
	}



	public void deleteHospital(Long id) {
		hospitalrepo.deleteById(id);
	}

	@Override
	public PatientDto getPatientById(long patientDtoId) {
		Patient getIddata = Patientrepo.findById(patientDtoId).get();
		return ConvertPatientEntitytoDto(getIddata);

	}

	@Override
	public PatientDto createPatient(PatientDto patientDto ) {

		Patient patient = convertPatientDtoToEntity(patientDto);
		if (patient.getAppUser() != null && patient.getAppUser() == null) {
			AppUser savedAppUser = appUserRepo.save(patient.getAppUser().getId());
			patient.setAppUser(savedAppUser);
		}
		if (patient.getHospital() != null && patient.getHospital() == null) {
			Hospital savedHospital = hospitalrepo.save(patient.getHospital());
			patient.setHospital(savedHospital);
		}
		Patient PatientSave = Patientrepo.save(patient);
		return ConvertPatientEntitytoDto(PatientSave);
	}	


	public PatientDto updatePatient(PatientDto patientDTO) {
		Patient existingPatient = Patientrepo.findById(patientDTO.getId())
				.orElseThrow(() -> new RuntimeException("Patient not found"));

		existingPatient.setName(patientDTO.getName());
		existingPatient.setCondition(patientDTO.getCondition());

		Hospital hospital = hospitalrepo.findById(patientDTO.getHospital())
				.orElseThrow(() -> new RuntimeException("Hospital not found"));
		existingPatient.setHospital(hospital);

		AppUser appUser = appUserRepo.findById(patientDTO.getAppUser())
				.orElseThrow(() -> new RuntimeException("AppUser not found"));
		existingPatient.setAppUser(appUser);

		Patient updatedPatient = Patientrepo.save(existingPatient);
		return ConvertPatientEntitytoDto(updatedPatient);
	}

	@Override
	public PatientDto deletePatient(long patientDtoId) {
		Patient deleteId = Patientrepo.findById(patientDtoId).get();
		Patientrepo.delete(deleteId);
		return null;
	}

	
	
	private PatientDto ConvertPatientEntitytoDto(Patient patient) {
		PatientDto patientDto = new PatientDto();
		patientDto.setId(patient.getId());
		patientDto.setName(patient.getName());
		patientDto.setCondition(patient.getCondition());
		patientDto.setTreatment(patient.getTreatment());
		if (patient.getHospital() != null) {
			patientDto.setHospital(patient.getHospital().getId());
		}
		if (patient.getAppUser() != null) {
			patientDto.setAppUser(patient.getAppUser().getId());
			
		}
		
		return patientDto;
	}
	
	 

	private Patient convertPatientDtoToEntity(PatientDto patientDto) {

		Patient patient = new Patient();
		patient.setId(patientDto.getId());
		patient.setName(patientDto.getName());
		patient.setCondition(patientDto.getCondition());
		patient.setTreatment(patientDto.getTreatment());

		Hospital hospital = hospitalrepo.findById(patientDto.getHospital()).orElse(null);
		patient.setHospital(hospital);

		AppUser appUser = appUserRepo.findById(patientDto.getAppUser()).orElse(null);
		patient.setAppUser(appUser);


		return patient;

	}
	
	
}
