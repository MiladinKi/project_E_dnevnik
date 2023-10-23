package com.iktpreobuka.e_dnevnik.services;

import java.util.List;

import com.iktpreobuka.e_dnevnik.entities.MarkEntity;

public interface MarkService {
	public List<MarkEntity> findAllMarksByPupilId(Integer pupilId);
	public List<MarkEntity> findMarkByPupilFromSubject(Integer pupilId, Integer subjectId);

}
