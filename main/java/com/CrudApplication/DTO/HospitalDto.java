package com.CrudApplication.DTO;


import com.CrudApplication.Entities.Hospital;
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

public class HospitalDto {

	private long id;
	 private String name;
	    private String city;
	    private String address;
	    private String state;





}
