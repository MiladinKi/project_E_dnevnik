package com.iktpreobuka.e_dnevnik.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.e_dnevnik.entities.AdminEntity;

public interface AdminRepository extends CrudRepository<AdminEntity, Integer> {

	public  List<AdminEntity> findByFirstname(String firstname);
	public  List<AdminEntity> findByLastname(String lastname);
	public  AdminEntity findByEmail(String email);

}
