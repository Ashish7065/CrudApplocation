package com.CrudApplication.Services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.CrudApplication.DTO.HospitalDto;
import com.CrudApplication.Entities.Hospital;

import java.util.List;

public interface HospitalService {
	
	  public HospitalDto createHospital(HospitalDto hospitalDto);
	  
	  public HospitalDto updateHospital(Long id, HospitalDto hospitalDto);
	  
	  public HospitalDto getHospitalById(Long id);

	  public void deleteHospital(Long id);


	  //////////////////////////

      //Pagination
	  public Page<Hospital> getAllHospital(String search , Pageable pageable);

    // Native query
	public Page<HospitalDto> getHospitals( String search , int page , int size);


}


