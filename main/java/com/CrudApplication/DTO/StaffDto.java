package com.CrudApplication.DTO;

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

public class StaffDto {

	private long id;
	private String name;
	private String role;
	private Long hospital;
	private long appUser;




}
