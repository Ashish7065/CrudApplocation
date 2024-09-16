package com.CrudApplication.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.CrudApplication.DTO.AppUserDto;
import com.CrudApplication.Entities.AppUser;

import com.CrudApplication.Repositary.AppUserRepo;

import com.CrudApplication.Services.AppUserService;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class AppUserServiceImpl implements AppUserService {

	@Autowired
	private AppUserRepo appUserRepo;

	public AppUserDto createAppUser(AppUserDto appUserDto) {
		AppUser appUser = convertToEntity(appUserDto);
		AppUser savedAppUser = appUserRepo.save(appUser);
		return convertToDto(savedAppUser);
	
		
	}
	

	public AppUserDto updateAppUser(Long id, AppUserDto appUserDto) {
		AppUser existingAppUser = appUserRepo.findById(id).orElseThrow(() -> new RuntimeException("AppUser not found"));
		existingAppUser.setUsername(appUserDto.getUsername());
		existingAppUser.setPassword(appUserDto.getPassword());

		AppUser updatedAppUser = appUserRepo.save(existingAppUser);
		return convertToDto(updatedAppUser);
	}

	public AppUserDto getAppUserById(Long id) {
		AppUser appUser = appUserRepo.findById(id).orElseThrow(() -> new RuntimeException("AppUser not found"));
		return convertToDto(appUser);
	}

	//Pagination
	public Page<AppUser> getAllAppUsers( String search , int page, int size) {

		Pageable pageable = PageRequest.of(page, size);

		Specification<AppUser> specification = new Specification<AppUser>() {
			@Override
			public Predicate toPredicate(Root<AppUser> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if (search != null && !search.isEmpty()) {
					predicates.add(criteriaBuilder.or(
							criteriaBuilder.like(root.get("name"), "%" + search + "%"),
							criteriaBuilder.like(root.get("city"), "%" + search + "%"),
							criteriaBuilder.like(root.get("address"), "%" + search + "%"),
							criteriaBuilder.like(root.get("state"), "%" + search + "%")
					));
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
			}
		};

		return appUserRepo.findAll(specification, pageable);
	}
	

		public void deleteAppUser(Long id) {
			appUserRepo.deleteById(id);
		}

		private AppUser convertToEntity(AppUserDto appUserDto) {
			AppUser appUser = new AppUser();
			appUser.setId(appUserDto.getId());
			appUser.setUsername(appUserDto.getUsername());
			appUser.setPassword(appUserDto.getPassword());

			return appUser;
		}

		private AppUserDto convertToDto(AppUser appUser) {
			AppUserDto appUserDto = new AppUserDto();
			appUserDto.setId(appUser.getId());
			appUserDto.setUsername(appUser.getUsername());
			appUserDto.setPassword(appUser.getPassword());

			return appUserDto;
		}

	}






