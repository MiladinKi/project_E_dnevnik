package com.iktpreobuka.e_dnevnik.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.e_dnevnik.entities.PupilEntity;

public interface PupilRepository extends CrudRepository<PupilEntity, Integer> {
	public List<PupilEntity> findByFirstname(String firstname);
	public List<PupilEntity> findByLastname(String lastname);
	public List<PupilEntity> findByDateOfBirth(LocalDate dateOfBirth);
}
