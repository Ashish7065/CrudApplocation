package com.CrudApplication.Repositary;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


import com.CrudApplication.DTO.HospitalDto;
import com.CrudApplication.Entities.Hospital;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Hospitalrepo extends JpaRepository<Hospital, Long> , JpaSpecificationExecutor<Hospital>{

	@Query(value = "SELECT * FROM Hospital WHERE name LIKE CONCAT('%', :search, '%') OR address LIKE CONCAT('%', :search, '%')",
			countQuery = "SELECT count(*) FROM Hospital WHERE name LIKE CONCAT('%', :search, '%') OR address LIKE CONCAT('%', :search, '%')",
			nativeQuery = true)
	Page<Hospital> getHospitals( String search, Pageable pageable);


	//Pagination
	Page<Hospital> findAll(Specification<Hospital> spec, Pageable pageable);

	Hospital save(long hospital);

	Hospital save(HospitalDto hospitaldtoId);



}
