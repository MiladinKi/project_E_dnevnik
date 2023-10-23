package com.iktpreobuka.e_dnevnik.controllers;

import java.util.List;

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
import com.iktpreobuka.e_dnevnik.entities.SubjectEntity;
import com.iktpreobuka.e_dnevnik.entities.TeacherEntity;
import com.iktpreobuka.e_dnevnik.entities.UserEntity;
import com.iktpreobuka.e_dnevnik.repositories.SubjectRepository;
import com.iktpreobuka.e_dnevnik.repositories.TeacherRepository;
import com.iktpreobuka.e_dnevnik.repositories.UserRepository;
import com.iktpreobuka.e_dnevnik.security.Views;
import com.iktpreobuka.e_dnevnik.util.RESTError;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "api/v1/teachers")
public class TeacherController {

	@JsonView(Views.Admin.class)
	private static final Logger log = LoggerFactory.getLogger(TeacherController.class);

	@Autowired
	private TeacherRepository teacherRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SubjectRepository subjectRepository;

	@Secured("ADMIN")
	@RequestMapping(method = RequestMethod.POST, value = "/{userId}")
	public ResponseEntity<?> addTeacher(@Valid @RequestBody TeacherEntity newTeacher, @PathVariable Integer userId) {
		try {
			UserEntity user = userRepository.findById(userId).orElse(null);
			TeacherEntity teacher = new TeacherEntity();
			log.info("Adding new teacher{}", newTeacher.getFirstname());
			teacher.setFirstname(newTeacher.getFirstname());
			log.info("Adding new teacher{}", newTeacher.getLastname());
			teacher.setLastname(newTeacher.getLastname());
			log.info("Adding new teacher{}", newTeacher.getAge());
			teacher.setAge(newTeacher.getAge());
			teacher.setUser(user);
			log.error("An exception occured while posting a new teacher");
			teacherRepository.save(teacher);
			return new ResponseEntity<>(teacher, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(1, "Exception occurred:" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Secured("ADMIN")
	@RequestMapping(method = RequestMethod.POST, value = "/teacher/{teacherId}/subject/{subjectId}")
	public TeacherEntity addTeacherToSubject(@PathVariable Integer subjectId, @PathVariable Integer teacherId) {
		TeacherEntity teacher = teacherRepository.findById(teacherId).orElse(null);
		SubjectEntity subject = subjectRepository.findById(subjectId).orElse(null);
		log.error("An exception occured while adding a new teacher!");
		teacher.getSubjects().add(subject);
		teacherRepository.save(teacher);
		return teacher;

	}

	@Secured("ADMIN")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllTeachers() {
		return new ResponseEntity<>(teacherRepository.findAll(), HttpStatus.OK);
	}

	@Secured("ADMIN")
	@RequestMapping(method = RequestMethod.PUT, value = "/{teacherId}")
	public ResponseEntity<?> changeTeacher(@Valid @RequestBody TeacherEntity updatedTeacher,
			@PathVariable Integer teacherId) {
		try {
			TeacherEntity teacher = teacherRepository.findById(teacherId).orElse(null);
			if (updatedTeacher.getFirstname() != null) {
				log.error("An exception ocuured while changing a teacher firstname");
				teacher.setFirstname(updatedTeacher.getFirstname());
			}
			if (updatedTeacher.getLastname() != null) {
				log.error("An exception ocuured while changing a teacher lastname");
				teacher.setLastname(updatedTeacher.getLastname());
			}
			if (updatedTeacher.getAge() != null) {
				log.error("An exception ocuured while changing a teacher's age");
				teacher.setAge(updatedTeacher.getAge());
			}
			teacherRepository.save(teacher);
			return new ResponseEntity<>(teacher, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Exception occurred when changing parent!");
			return new ResponseEntity<>(new RESTError(1, "Exception occurred:" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Secured("ADMIN")
	@RequestMapping(method = RequestMethod.DELETE, value = "/{teacherId}")
	public ResponseEntity<?> deleteTeacher(@PathVariable Integer teacherId){
		try {
			if(teacherRepository.existsById(teacherId)) {
				teacherRepository.deleteById(teacherId);
				return new ResponseEntity<>("Teacher is deleted", HttpStatus.OK);
			}
			else {
				log.info("Teacher is not foubd in the DB");
				return new ResponseEntity<>(new RESTError(1, "Teacher is not found"), HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			log.error("Error deleting teacher:" + e.getMessage());
			return new ResponseEntity<>(new RESTError(2,  "Error deleting teacher"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Secured("ADMIN")
	@RequestMapping(method = RequestMethod.GET, value = "/findById/{teacherId}")
	public ResponseEntity<TeacherEntity> findById (@PathVariable Integer teacherId){
		TeacherEntity teacher = teacherRepository.findById(teacherId).get();
		return new ResponseEntity<TeacherEntity>(teacher, HttpStatus.OK);
	}
	
	@Secured("ADMIN")
	@RequestMapping(method = RequestMethod.GET, value = "/findByFirstname/{firstname}")
	public ResponseEntity<?> findByFirstname (@PathVariable String firstname){
		List<TeacherEntity> teacher = teacherRepository.findByFirstname(firstname);
		return new ResponseEntity<>(teacher, HttpStatus.OK);
	}
	
	@Secured("ADMIN")
	@RequestMapping(method = RequestMethod.GET, value = "/findByLastname/{lastname}")
	public ResponseEntity<?> findByLastname (@PathVariable String lastname){
		List<TeacherEntity> teacher = teacherRepository.findByLastname(lastname);
		return new ResponseEntity<>(teacher, HttpStatus.OK);
	}
	
	@Secured("ADMIN")
	@RequestMapping(method = RequestMethod.GET, value = "/findByAge/{age}")
	public ResponseEntity<?> findByAge(@PathVariable Integer age){
		List<TeacherEntity> teacher = teacherRepository.findByAge(age);
		return new ResponseEntity<>(teacher, HttpStatus.OK);
	}
}
