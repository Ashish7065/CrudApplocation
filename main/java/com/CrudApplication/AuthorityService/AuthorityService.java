package com.CrudApplication.AuthorityService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CrudApplication.Entities.Hospital;
import com.CrudApplication.Entities.Patient;
import com.CrudApplication.Repositary.Hospitalrepo;
import com.CrudApplication.Repositary.Patientrepo;

@Service
public class AuthorityService {

	@Autowired
	private Hospitalrepo hospitalrepo;
	
	@Autowired
	private Patientrepo patientrepo;
	
	// Patience
		public List<Patient> getAllPatients() {
			return patientrepo.findAll();

		}

		// hospital
		public List<Hospital> getAllHospitals() {
			return hospitalrepo.findAll();
		}
}
