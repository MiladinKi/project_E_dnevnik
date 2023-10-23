package com.iktpreobuka.e_dnevnik.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.e_dnevnik.entities.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {
	public UserEntity findByUsername(String username);
	public UserEntity findByEmail(String email);
}
