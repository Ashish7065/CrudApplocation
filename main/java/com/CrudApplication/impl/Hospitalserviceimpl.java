package com.CrudApplication.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import com.CrudApplication.DTO.StaffDto;
import com.CrudApplication.Entities.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.CrudApplication.DTO.HospitalDto;

import com.CrudApplication.Entities.Hospital;

import com.CrudApplication.Repositary.Hospitalrepo;
import com.CrudApplication.Repositary.Patientrepo;
import com.CrudApplication.Repositary.Staffrepo;
import com.CrudApplication.Services.HospitalService;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class Hospitalserviceimpl implements HospitalService {

	@Autowired
	private Hospitalrepo hospitalrepo;

	@Autowired
	private Patientrepo patientrepo; // Add this repo file to add patient data...

	@Autowired
	private Staffrepo staffrepo; // Add this repo file to add staff data...

	public HospitalDto createHospital(HospitalDto hospitalDto) {
		Hospital hospital = convertToEntity(hospitalDto);
		Hospital savedHospital = hospitalrepo.save(hospital);
		return convertToDto(savedHospital);
	}

	public HospitalDto updateHospital(Long id, HospitalDto hospitalDto) {
		Hospital existingHospital = hospitalrepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Hospital not found"));

		existingHospital.setName(hospitalDto.getName());
		existingHospital.setCity(hospitalDto.getCity());
		existingHospital.setAddress(hospitalDto.getAddress());
		existingHospital.setState(hospitalDto.getState());
		Hospital updatedHospital = hospitalrepo.save(existingHospital);
		return convertToDto(updatedHospital);
		
	}

	public HospitalDto getHospitalById(Long id) {
		Hospital hospital = hospitalrepo.findById(id).orElseThrow(() -> new RuntimeException("Hospital not found"));
		return convertToDto(hospital);
	}

	
	//pagination
	public Page<Hospital> getAllHospital(String search , Pageable pageable) {

		Specification<Hospital> specification = new Specification<Hospital>() {
			@Override
			public Predicate toPredicate(Root<Hospital> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
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

		return hospitalrepo.findAll(specification, pageable);
	}

	//Native query
	public Page<HospitalDto> getHospitals( String search , int page , int size) {

		Pageable pageable = PageRequest.of(page, size);

		Page<Hospital> result = hospitalrepo.getHospitals(search, pageable);

		List<HospitalDto> hospitalList = result.getContent().stream()
				.map(Hospital -> new HospitalDto(
						Hospital.getId(),
						Hospital.getName(),
						Hospital.getCity(),
						Hospital.getAddress(),
						Hospital.getState()
				))
				.collect(Collectors.toList());

		return new PageImpl<>(hospitalList, pageable, result.getTotalElements());
	}


	@Override
	public void deleteHospital(Long id) {
		hospitalrepo.deleteById(id);
	}

	private Hospital convertToEntity(HospitalDto hospitalDto) {
		Hospital hospital = new Hospital();
		hospital.setId(hospitalDto.getId());
		hospital.setName(hospitalDto.getName());
		hospital.setCity(hospitalDto.getCity());
		hospital.setAddress(hospitalDto.getAddress());
		hospital.setState(hospitalDto.getState());
		return hospital;
	}

	private HospitalDto convertToDto(Hospital hospital) {
		HospitalDto hospitalDto = new HospitalDto();
		hospitalDto.setId(hospital.getId());
		hospitalDto.setName(hospital.getName());
		hospitalDto.setCity(hospital.getCity());
		hospitalDto.setAddress(hospital.getAddress());
		hospitalDto.setState(hospital.getState());
		
		return hospitalDto;
	}

}