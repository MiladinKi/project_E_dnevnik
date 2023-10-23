package com.iktpreobuka.e_dnevnik.controllers;

import java.util.List;

import org.slf4j.Logger;
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
import com.iktpreobuka.e_dnevnik.entities.SubjectEntity;
import com.iktpreobuka.e_dnevnik.entities.TeacherEntity;
import com.iktpreobuka.e_dnevnik.entities.dtos.SubjectDTO;
import com.iktpreobuka.e_dnevnik.repositories.SubjectRepository;
import com.iktpreobuka.e_dnevnik.repositories.TeacherRepository;
import com.iktpreobuka.e_dnevnik.security.Views;
import com.iktpreobuka.e_dnevnik.util.RESTError;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/subjects")
public class SubjectController {

	@JsonView(Views.Admin.class)
	private static final Logger log = org.slf4j.LoggerFactory.getLogger(SubjectController.class);

	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private TeacherRepository teacherRepository;

	@Secured("ADMIN")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> newSubject(@Valid @RequestBody SubjectDTO newSubject) {
		try {
			SubjectEntity subject = new SubjectEntity();
			log.info("Adding new subject{}", newSubject.getName());
			subject.setName(newSubject.getName());
			log.info("Adding new subject{}", newSubject.getWeekClassFund());
			subject.setWeekClassFund(newSubject.getWeekClassFund());
			subjectRepository.save(subject);
			log.error("An exception occured while posting a new subject");
			return new ResponseEntity<>(subject, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error("Exception occurred when adding a new subject!");
			return new ResponseEntity<>(new RESTError(1, "Exception occurred:" + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@Secured({ "ADMIN", "PUPIL", "PARENT", "TEACHER" })
	@RequestMapping(method = RequestMethod.GET)
	public List<SubjectEntity> getAllSubjects() {
		log.error("An exception occured while getting all subjects");
		return (List<SubjectEntity>) subjectRepository.findAll();
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{subjectId}")
	public ResponseEntity<?> changeSubjectByID(@Valid @RequestBody SubjectDTO updatedSubject,
			@PathVariable Integer subjectId) {
		try {
			SubjectEntity subject = subjectRepository.findById(subjectId).orElse(null);
			if (updatedSubject.getName() != null) {
				log.error("An exception ocuured while changing a subject name");
				subject.setName(updatedSubject.getName());
			}
			if (updatedSubject.getWeekClassFund() != null) {
				log.error("An exception ocuured while changing a subject class fund");
				subject.setWeekClassFund(updatedSubject.getWeekClassFund());
			}
			subjectRepository.save(subject);
			return new ResponseEntity<>(subject, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Exception occurred when changing subject!");
			return new ResponseEntity<>(new RESTError(1, "Exception occurred:" + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@Secured("ADMIN")
	@RequestMapping(method = RequestMethod.DELETE, value = "/{subjectId}")
	public ResponseEntity<?> deleteSubjectByID(@PathVariable Integer subjectId) {
		try {
			if (subjectRepository.existsById(subjectId)) {
				subjectRepository.deleteById(subjectId);
				log.info("Subject is deleted");
				return new ResponseEntity<>("Subject is deleted", HttpStatus.OK);
			} else {
				log.info("Subject is not foubd in the DB");
				return new ResponseEntity<>(new RESTError(1, "Subject is not found"), HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			log.error("Error deleting subject:" + e.getMessage());
			return new ResponseEntity<>(new RESTError(2, "Error deleting subject"), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Secured("ADMIN")
	@RequestMapping(method = RequestMethod.POST, value = "/subject/{subjectId}/teacher/{teacherId}")
	public SubjectEntity addSubjectToTeacher(@PathVariable Integer subjectId, @PathVariable Integer teacherId) {
		TeacherEntity teacher = teacherRepository.findById(teacherId).orElse(null);
		SubjectEntity subject = subjectRepository.findById(subjectId).orElse(null);
		log.error("An exception occured while adding teacher to subject!");
		subject.getTeachers().add(teacher);
		subjectRepository.save(subject);
		return subject;

	}

	@Secured("ADMIN")
	@RequestMapping(method = RequestMethod.GET, value = "/findById/{subjectId}")
	public ResponseEntity<SubjectEntity> findById(@PathVariable Integer subjectId) {
		SubjectEntity subject = subjectRepository.findById(subjectId).get();
		return new ResponseEntity<SubjectEntity>(subject, HttpStatus.OK);
	}

	@Secured("ADMIN")
	@RequestMapping(method = RequestMethod.GET, value = "/findByName/{name}")
	public ResponseEntity<?> findByName(@PathVariable String name) {
		List<SubjectEntity> subject = subjectRepository.findByName(name);
		return new ResponseEntity<>(subject, HttpStatus.OK);
	}

}
