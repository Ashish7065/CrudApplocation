package com.CrudApplication.Controller;



import com.CrudApplication.Entities.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CrudApplication.DTO.StaffDto;

import com.CrudApplication.Entities.Staff;
import com.CrudApplication.Services.Staffservice;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequestMapping("/staff")
public class StaffController {

	@Autowired
	private Staffservice staffservice;

	@PostMapping
	public ResponseEntity<StaffDto> createStaff(@RequestBody StaffDto staffDTO) {
		StaffDto createdStaff = staffservice.createStaff(staffDTO);
		return new ResponseEntity<>(createdStaff, HttpStatus.CREATED);
	}

	@PutMapping("update-staff-user/{id}")
	public ResponseEntity<StaffDto> updateStaff(@PathVariable Long id, @RequestBody StaffDto staffDTO) {
		staffDTO.setId(id); // Ensure the ID from the URL path is set in the DTO
		StaffDto updatedStaff = staffservice.updateStaff(staffDTO);
		return new ResponseEntity<>(updatedStaff, HttpStatus.OK);
	}

	@GetMapping("fetch-staff-user")
	public ResponseEntity<StaffDto> getStaffById(@RequestParam("id") @PathVariable Long id) {
		StaffDto staffDTO = staffservice.getStaffById(id);
		return new ResponseEntity<>(staffDTO, HttpStatus.OK);
	}

	@GetMapping("/search")
	public Page<Staff> getAllStaff(@RequestParam(required = false) String search, int page, int size) {
		return staffservice.getAllStaff(search,  page , size);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/searchdata")
	public Page<StaffDto> searchStaff(@RequestParam(required = false)String search, @RequestParam(required = false) List<Long> hospitalId,
									  @RequestParam(required = false) List<Long> staffrole, @RequestParam(defaultValue = "0") int page,
									  @RequestParam(defaultValue = "10") int size) {
		return staffservice.searchStaff(search, hospitalId, staffrole, page, size);

	}

	@DeleteMapping("/delete-staff-user")
	public ResponseEntity<Void> deleteStaff(@RequestParam("id") @PathVariable Long id) {
		staffservice.deleteStaff(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}



//((
// http://localhost:1020/staff-delete-id?employeId=2
// ))

