package com.iktpreobuka.e_dnevnik.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.e_dnevnik.entities.MarkEntity;
import com.iktpreobuka.e_dnevnik.entities.PupilEntity;
import com.iktpreobuka.e_dnevnik.entities.SubjectEntity;

public interface MarkRepository extends CrudRepository<MarkEntity, Integer> {
	public List<MarkEntity> findAllByPupilAndSubject(PupilEntity pupil, SubjectEntity subject);
	public List<MarkEntity> findAllByPupil(PupilEntity pupil);
}
