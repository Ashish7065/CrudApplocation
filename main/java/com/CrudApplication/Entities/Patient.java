package com.CrudApplication.Entities;

import org.antlr.v4.runtime.misc.NotNull;

import com.CrudApplication.DTO.StaffDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter

@Entity
public class Patient {
	
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	   private long id;
	    private String name;
	    private String condition;
	    private String treatment;
	    
	    @ManyToOne
	    @JoinColumn(name = "hospital_id")
	    private Hospital hospital;
	    
	    @OneToOne
	    @JoinColumn(name = "appUser_id")
	    private AppUser appUser;


}
