package com.CrudApplication.Services;

import java.util.List;

import com.CrudApplication.Entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.CrudApplication.DTO.StaffDto;
import com.CrudApplication.Entities.Staff;


public interface Staffservice {


	public StaffDto updateStaff(StaffDto staffDTO);

	public StaffDto getStaffById(Long id);

	public StaffDto createStaff(StaffDto staffDTO);

	//Pagination
	public Page<Staff> getAllStaff( String search , int page, int size);

	public void deleteStaff(Long id);

    //Native Query
	public Page<StaffDto> searchStaff(String search, List<Long> hospitalId, List<Long> staffrole, int page , int size);

}
