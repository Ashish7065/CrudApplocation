package com.CrudApplication.Repositary;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.CrudApplication.DTO.PatientDto;

import com.CrudApplication.Entities.Patient;

import java.util.List;


@Repository
public interface Patientrepo extends JpaRepository<Patient, Long>, JpaSpecificationExecutor<Patient> {

//	@Query(value =
//			"SELECT p.* FROM patient p " +
//					"JOIN staff s ON p.staff_id = s.id " +   //add one more join of appuser in the same way at staff too
//					" WHERE (:search IS NULL OR s.name ILIKE %:search% OR p.name ILIKE %:search%)" +
//					" AND (:doctorId IS NULL OR p.staff_id IN (:doctorId))" +   //p.app_user_id mention while using app_user join
//					" AND (:hospitalId IS NULL OR p.hospital_id IN (:hospitalId))",
//			nativeQuery = true)

//	@Query(value =  working
//			"SELECT p.* FROM patient p " +
//					"JOIN hospital h ON p.hospital_id = h.id " +
//					"JOIN app_user au ON p.app_user_id = au.id " +
//					"WHERE au.role = 'ROLE_DOCTOR' " +
//					"AND (:search IS NULL OR p.name ILIKE %:search% OR au.username ILIKE %:search%) " +
//					"AND (:doctorId IS NULL OR p.app_user_id IN (:doctorId)) " +
//					"AND (:hospitalId IS NULL OR p.hospital_id IN (:hospitalId))",
//			nativeQuery = true)


	@Query(value =
			"SELECT p.*  FROM patient p " +  // Selects all patient columns and the username from the app_user table
					"JOIN hospital h ON p.hospital_id = h.id " +  //Join the table
					"JOIN app_user au ON p.app_user_id = au.id " +  // Join the table
					"WHERE au.role IN ('ROLE_DOCTOR', 'ROLE_USER') " +
					"AND (:search IS NULL OR p.name ILIKE %:search% OR au.username ILIKE %:search%) " +  //Use for Search
					"AND (:doctorId IS NULL OR p.app_user_id IN (:doctorId)) " +   //Use for Filter
					"AND (:hospitalId IS NULL OR p.hospital_id IN (:hospitalId))",   //Use for Filter
			nativeQuery = true)

	public Page<Patient> findBySearchwithdoctorIdorhospitalId(String search, List<Long> doctorId, List<Long> hospitalId, Pageable pageable);


	Patient save(PatientDto patientDtoId);

	//  Fetch all patients with patientName , doctorName search and pagination, add filters Doctor and Hospital Filters

}
