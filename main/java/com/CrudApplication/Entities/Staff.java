package com.CrudApplication.Entities;

import com.CrudApplication.DTO.StaffDto;

import jakarta.persistence.*;
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
public class Staff {

	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private String role;


	@ManyToOne
	private Hospital hospital;

	@OneToOne
	private AppUser appUser;


}
