package com.iktpreobuka.e_dnevnik.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.e_dnevnik.entities.MarkEntity;
import com.iktpreobuka.e_dnevnik.entities.PupilEntity;
import com.iktpreobuka.e_dnevnik.entities.SubjectEntity;
import com.iktpreobuka.e_dnevnik.repositories.MarkRepository;
import com.iktpreobuka.e_dnevnik.repositories.PupilRepository;
import com.iktpreobuka.e_dnevnik.repositories.SubjectRepository;

@Service
public class MarkServiceImpl implements MarkService {

	@Autowired
	private PupilRepository pupilRepository;
	
	@Autowired
	private SubjectRepository subjectRepository;
	
	@Autowired
	private MarkRepository markRepository;

	
	@Override
	public List<MarkEntity> findAllMarksByPupilId(Integer pupilId) {
	    PupilEntity pupil = pupilRepository.findById(pupilId).orElse(null); // Treba proveriti da li je učenik pronađen
	    if (pupil != null) {
	        return pupil.getMarks();
	    } else {
	        return new ArrayList<>(); // Vratite praznu listu ako učenik nije pronađen
	    }
	}


	@Override
	public List<MarkEntity> findMarkByPupilFromSubject(Integer pupilId, Integer subjectId) {
		PupilEntity pupil = pupilRepository.findById(pupilId).get();
		SubjectEntity subject = subjectRepository.findById(subjectId).get();
		return markRepository.findAllByPupilAndSubject(pupil, subject);
	}


}
