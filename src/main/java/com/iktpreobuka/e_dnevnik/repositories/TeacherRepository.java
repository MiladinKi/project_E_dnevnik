package com.iktpreobuka.e_dnevnik.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.e_dnevnik.entities.TeacherEntity;

public interface TeacherRepository extends CrudRepository<TeacherEntity, Integer> {
	public List<TeacherEntity> findByFirstname(String firstname);
	public List<TeacherEntity> findByLastname(String lastname);
	public List<TeacherEntity> findByAge(Integer age);
	
}
