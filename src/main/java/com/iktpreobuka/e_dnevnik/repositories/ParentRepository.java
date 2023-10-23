package com.iktpreobuka.e_dnevnik.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.e_dnevnik.entities.ParentEntity;

public interface ParentRepository extends CrudRepository<ParentEntity, Integer> {
	public List<ParentEntity> findByFirstname(String firstname);
	public List<ParentEntity> findByLastname(String lastname);
	public List<ParentEntity> findByEmail(String email);
//	public List<ParentEntity> findParentByPupilId(Integer pupilId);
}
