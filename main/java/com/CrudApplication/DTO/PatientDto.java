package com.CrudApplication.DTO;

import com.CrudApplication.Entities.Staff;
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

public class PatientDto {

	
	   private long id;
	    private String name;
	    private String condition;
	    private String treatment;
	    private long hospital;
	    private long appUser;

	    
	    
	    
	    
}
