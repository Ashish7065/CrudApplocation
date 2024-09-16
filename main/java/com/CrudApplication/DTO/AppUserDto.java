package com.CrudApplication.DTO;

import java.util.List;

import com.CrudApplication.Entities.Patient;
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

public class AppUserDto {

	private long id;
    private String username;
    private String password;
    private String role;
  
   
    
    
}
