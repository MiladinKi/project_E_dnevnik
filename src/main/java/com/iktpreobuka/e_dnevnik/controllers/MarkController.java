package com.iktpreobuka.e_dnevnik.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.e_dnevnik.entities.MarkEntity;
import com.iktpreobuka.e_dnevnik.entities.PupilEntity;
import com.iktpreobuka.e_dnevnik.entities.SubjectEntity;
import com.iktpreobuka.e_dnevnik.repositories.MarkRepository;
import com.iktpreobuka.e_dnevnik.repositories.PupilRepository;
import com.iktpreobuka.e_dnevnik.repositories.SubjectRepository;
import com.iktpreobuka.e_dnevnik.security.Views;
import com.iktpreobuka.e_dnevnik.services.MarkService;
import com.iktpreobuka.e_dnevnik.util.RESTError;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "api/v1/marks")
public class MarkController {
	
	@JsonView(Views.Admin.class)
	private static final Logger log = LoggerFactory.getLogger(MarkRepository.class);

	@Autowired
	private MarkRepository markRepository;

	@Autowired
	private PupilRepository pupilRepository;
	
	@Autowired
	private MarkService markService;
	
	@Autowired
	private SubjectRepository subjectRepository;

	@Secured({"ADMIN", "TEACHER"})
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> newMark(@Valid@RequestBody MarkEntity addMark){
		try {
			MarkEntity mark = new MarkEntity();
			mark.setMark(addMark.getMark());
			markRepository.save(mark);
			return new ResponseEntity<>(mark, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(1, "Exception occurred:" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@Secured({"ADMIN", "TEACHER"})
	@RequestMapping(method = RequestMethod.POST, value = "/{markId}/pupil/{pupilId}/subject/{subjectId}")
	public ResponseEntity<?> newMark(@PathVariable Integer markId, @PathVariable Integer pupilId,
			@PathVariable Integer subjectId){
		try {
			MarkEntity mark = new MarkEntity();
			mark = markRepository.findById(markId).get();
			PupilEntity pupil = pupilRepository.findById(pupilId).get();
			SubjectEntity subject = subjectRepository.findById(subjectId).get();
			mark.setPupil(pupil);
			mark.setSubject(subject);
			markRepository.save(mark);
			return new ResponseEntity<>(mark, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(1, "Exception occurred:" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@Secured("ADMIN")
	@JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllMarks(){
		log.info("There is no marks in the DB");
		return new ResponseEntity<>(markRepository.findAll(), HttpStatus.OK);
	}
	
	@Secured({"ADMIN", "TEACHER"})
	@RequestMapping(method = RequestMethod.DELETE, value = "/{markId}")
	public ResponseEntity<?> deleteMark(@PathVariable Integer markId){
		try {
			if(markRepository.existsById(markId)) {
				markRepository.deleteById(markId);
				log.info("Mark is deleted");
				return new ResponseEntity<>("Mark is deleted", HttpStatus.OK);
			} else {
				log.info("Mark not found in the DB");
				return new ResponseEntity<>(new RESTError(1, "Mark not found"), HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			log.error("Error deleting Mark: " + e.getMessage());
			return new ResponseEntity<>(new RESTError(1, "Exception occurred:" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Secured({"PUPIL", "ADMIN", "TEACHER", "PARENT"})
	@RequestMapping(method = RequestMethod.GET, value = "/findById/{markId}")
	public ResponseEntity<MarkEntity> findMarkById(@PathVariable Integer markId){
		MarkEntity mark = markRepository.findById(markId).get();
		log.error("An exception occured while getting mark by pupil Id!");
		return new ResponseEntity<MarkEntity>(mark, HttpStatus.OK);
	}
	
	@Secured({"PUPIL", "ADMIN", "TEACHER", "PARENT"})
	@RequestMapping(method = RequestMethod.GET, value = "/marksByPupil/{pupilId}")
	public ResponseEntity<?> getMarksByPupil(@PathVariable Integer pupilId){
		List<MarkEntity> marks = markService.findAllMarksByPupilId(pupilId);
		for(MarkEntity mark : marks) {
			log.error("An exception occured while listing mark by pupil Id!");
			mark.getPupil().setSubjects(null);
		}
		return new ResponseEntity<>(marks, HttpStatus.OK);
	}
	
	@Secured({"PUPIL", "ADMIN", "TEACHER", "PARENT"})
	@RequestMapping(method = RequestMethod.GET, value = "/pupil/{pupilId}/subject/{subjectId}")
	public ResponseEntity<?> findMarksByPupilFromSubject(@PathVariable Integer pupilId, @PathVariable Integer subjectId){
		List<MarkEntity> marks = markService.findMarkByPupilFromSubject(pupilId, subjectId);
		for(MarkEntity mark:marks) {
			log.error("An exception occured while listing marks by pupil and subject id!");
			mark.getPupil().setSubjects(null);
		}
		return new ResponseEntity<>(marks, HttpStatus.OK);
	}
	
	@Secured({"PUPIL", "ADMIN", "TEACHER", "PARENT"})
	@RequestMapping(method = RequestMethod.GET, value = "/avgMark/subject/{subjectId}/pupil/{pupilId}")
	public ResponseEntity<?> averageMarkForSubject(@PathVariable Integer subjectId, @PathVariable Integer pupilId) {
		SubjectEntity subject = subjectRepository.findById(subjectId).get();
		PupilEntity pupil = pupilRepository.findById(pupilId).get();
		List<MarkEntity> marks = markRepository.findAllByPupilAndSubject(pupil, subject);
		long sum = 0;
		double avg;
		for(MarkEntity mark:marks) {
			sum +=mark.getMark();
		}
		log.error("An exception occured while listing marks by pupil id and subject id!");
		avg = sum/marks.size();
		return new ResponseEntity<>(avg, HttpStatus.OK);
	}
	
	@Secured({"PUPIL", "ADMIN", "TEACHER", "PARENT"})
	@RequestMapping(method = RequestMethod.GET, path = "/avgMarkPerSubject/pupil/{pupilId}")
	public ResponseEntity<?> averageMarkPerSubjectForPupil( @PathVariable Integer pupilId) {
		
		PupilEntity pupil = pupilRepository.findById(pupilId).get();
		List<MarkEntity> marks = markRepository.findAllByPupil(pupil);
		Map<String, Integer> sumPerSubject = new HashMap<>();
		Map<String, Integer> counterPerSubject = new HashMap<>();
		for (MarkEntity mark : marks) {
			String subjectName = mark.getSubject().getName();
			if(sumPerSubject.containsKey(subjectName)) {
				Integer sum = sumPerSubject.get(subjectName)+mark.getMark();
				sumPerSubject.put(subjectName, sum);
				log.error("An exception occured while suming marks per subject!");
			} else {
				sumPerSubject.put(subjectName, mark.getMark());
			}
			if(counterPerSubject.containsKey(subjectName)) {
				log.error("An exception occured while counting subject!");
				Integer sum = counterPerSubject.get(subjectName) + 1;
				counterPerSubject.put(subjectName, sum);
			} else {
				counterPerSubject.put(subjectName, 1);
			}
		}
		Map<String, Double> avgPerSubject = new HashMap<>(sumPerSubject.size());
		for (Map.Entry<String, Integer> sumEntry : sumPerSubject.entrySet()) {
			log.error("An exception occured while average marks per subject in  map!");
			String subjectName =sumEntry.getKey();
			Integer counter = counterPerSubject.get(subjectName);
			avgPerSubject.put(subjectName, ((double) sumEntry.getValue()/counter));
		}
		log.error("An exception occured while getting average mark per subject by pupil id!");
		return new ResponseEntity<>(avgPerSubject, HttpStatus.OK);
	}

}
