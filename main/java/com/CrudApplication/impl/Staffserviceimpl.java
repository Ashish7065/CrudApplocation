package com.CrudApplication.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.CrudApplication.DTO.StaffDto;
import com.CrudApplication.Entities.AppUser;
import com.CrudApplication.Entities.Hospital;
import com.CrudApplication.Entities.Staff;
import com.CrudApplication.Repositary.AppUserRepo;
import com.CrudApplication.Repositary.Hospitalrepo;

import com.CrudApplication.Repositary.Staffrepo;
import com.CrudApplication.Services.Staffservice;

import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Service
public class Staffserviceimpl implements Staffservice {

	@Autowired
	private Staffrepo staffrepo;

	@Autowired
	private Hospitalrepo hospitalrepo;

	@Autowired
	private AppUserRepo appUserRepo;

	public StaffDto createStaff(StaffDto staffDTO) {
		Staff staff = convertToEntity(staffDTO);
		if (staff.getAppUser() != null && staff.getAppUser() == null) {
			AppUser savedAppUser = appUserRepo.save(staff.getAppUser());
			staff.setAppUser(savedAppUser);
		}
		if (staff.getHospital() != null && staff.getHospital() == null) {
			Hospital savedHospital = hospitalrepo.save(staff.getHospital());
			staff.setHospital(savedHospital);
		}
		Staff savedStaff = staffrepo.save(staff);
		return convertToDTO(savedStaff);
	}

	public StaffDto updateStaff(StaffDto staffDTO) {
		Staff existingStaff = staffrepo.findById(staffDTO.getId())
				.orElseThrow(() -> new RuntimeException("Staff not found"));
		existingStaff.setName(staffDTO.getName());
		existingStaff.setRole(staffDTO.getRole());

		Hospital hospital = hospitalrepo.findById(Long.valueOf(staffDTO.getHospital()))
				.orElseThrow(() -> new RuntimeException("Hospital not found"));
		existingStaff.setHospital(hospital);

		AppUser appUser = appUserRepo.findById(staffDTO.getAppUser())
				.orElseThrow(() -> new RuntimeException("AppUser not found"));
		existingStaff.setAppUser(appUser);

		Staff updatedStaff = staffrepo.save(existingStaff);
		return convertToDTO(updatedStaff);
	}

	public StaffDto getStaffById(Long id) {
		Staff staff = staffrepo.findById(id).orElseThrow(() -> new RuntimeException("Staff not found"));
		return convertToDTO(staff);
	}


	//Pagination
	public Page<Staff> getAllStaff(String search, int page, int size) {

		Pageable pageable = PageRequest.of(page, size);

		Specification<Staff> specification = new Specification<Staff>() {
			@Override
			public Predicate toPredicate(Root<Staff> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
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

		return staffrepo.findAll(specification, pageable);
	}


	//Native query
	public Page<StaffDto> searchStaff(String search, List<Long> hospitalId, List<Long> staffrole, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);

		// Convert lists to arrays, handle null lists by passing empty arrays  ((returns an empty array (new Long[0]) instead of a null array.))
		Long[] staffroleArray = (staffrole != null && !staffrole.isEmpty()) ? staffrole.toArray(new Long[0]) : new Long[0];
		Long[] hospitalIdArray = (hospitalId != null && !hospitalId.isEmpty()) ? hospitalId.toArray(new Long[0]) : new Long[0];

		Page<Staff> result;

		if (search == null && hospitalId == null && staffrole == null) {
			result = staffrepo.findAll(pageable);
		} else {
			if (hospitalId == null) {
				hospitalId = Collections.emptyList();
			}
			if (search == null) {
				search = "";
			}
			result = staffrepo.findBySearch(search, List.of(hospitalIdArray), List.of(staffroleArray), pageable);
		}

		List<StaffDto> staffList = result.getContent().stream()
				.map(staff -> new StaffDto(
						staff.getId(),
						staff.getName(),
						staff.getRole(),
						staff.getHospital().getId(),
						staff.getAppUser().getId()
				))
				.collect(Collectors.toList());

		return new PageImpl<>(staffList, pageable, result.getTotalElements());
	}



	public void deleteStaff(Long id) {
		staffrepo.deleteById(id);
	}

	private Staff convertToEntity(StaffDto staffDTO) {
		Staff staff = new Staff();
		staff.setId(staffDTO.getId());
		staff.setName(staffDTO.getName());
		staff.setRole(staffDTO.getRole());

		Hospital hospital = hospitalrepo.findById(Long.valueOf(staffDTO.getHospital())).orElse(null);
		staff.setHospital(hospital);

		AppUser appUser = appUserRepo.findById(staffDTO.getAppUser()).orElse(null);
		staff.setAppUser(appUser);

		return staff;
	}

	private StaffDto convertToDTO(Staff staff) {
		StaffDto staffDTO = new StaffDto();
		staffDTO.setId(staff.getId());
		staffDTO.setName(staff.getName());
		staffDTO.setRole(staff.getRole());
		if (staff.getHospital() != null) {
			staffDTO.setHospital(staff.getHospital().getId());
		}
		if (staff.getAppUser() != null) {
			staffDTO.setAppUser(staff.getAppUser().getId());
		}
		return staffDTO;
	}

}
	
	
	
	
	
	
	
	
	
	
	
	
	
