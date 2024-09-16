package com.CrudApplication.Repositary;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


import com.CrudApplication.DTO.StaffDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.CrudApplication.Entities.Staff;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Staffrepo extends JpaRepository<Staff, Long> , JpaSpecificationExecutor<Staff> {

//	@Query(value =
//      "SELECT s.* FROM Staff s " +
//     "JOIN hospital h ON s.hospital_id = h.id " +
//   " WHERE (:search IS NULL OR s.name ILIKE %:search% OR h.name ILIKE %:search%)" +
//      " AND (:hospitalId IS NULL OR h.id IN (:hospitalId))" +
//      " AND (:staffrole IS NULL OR s.role = :staffrole)",
//       nativeQuery = true)


	@Query(value =
			"SELECT s.* FROM Staff s " +
					"JOIN hospital h ON s.hospital_id = h.id " +
					"JOIN app_user au ON s.app_user_id = au.id " +
					" WHERE (:search IS NULL OR s.name ILIKE %:search% OR h.name ILIKE %:search%)" +
					"AND (:staffrole IS NULL OR s.app_user_id IN (:staffrole)) " +
					"AND (:hospitalId IS NULL OR s.hospital_id IN (:hospitalId))",
			nativeQuery = true)

	 public Page<Staff> findBySearch(String search, List<Long> hospitalId, List<Long> staffrole, Pageable pageable);

	//Fetch all Hospital staff with hospitalName , staffName search and pagination add  Hospital  and role (Doctor, nurse)  filters

	Staff save(StaffDto staffdtoId);



	
	


}
