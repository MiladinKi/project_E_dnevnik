package com.iktpreobuka.e_dnevnik.controllers;

import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.e_dnevnik.entities.ESemester;
import com.iktpreobuka.e_dnevnik.entities.MarkEntity;
import com.iktpreobuka.e_dnevnik.entities.ParentEntity;
import com.iktpreobuka.e_dnevnik.entities.PupilEntity;
import com.iktpreobuka.e_dnevnik.entities.SubjectEntity;
import com.iktpreobuka.e_dnevnik.entities.UserEntity;
import com.iktpreobuka.e_dnevnik.entities.dtos.PupilDTO;
import com.iktpreobuka.e_dnevnik.repositories.MarkRepository;
import com.iktpreobuka.e_dnevnik.repositories.ParentRepository;
import com.iktpreobuka.e_dnevnik.repositories.PupilRepository;
import com.iktpreobuka.e_dnevnik.repositories.SubjectRepository;
import com.iktpreobuka.e_dnevnik.repositories.UserRepository;
import com.iktpreobuka.e_dnevnik.security.Views;
import com.iktpreobuka.e_dnevnik.util.RESTError;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "api/v1/pupils")
public class PupilController {

	@JsonView(Views.Admin.class)
	public static final Logger log = LoggerFactory.getLogger(PupilController.class);

	@Autowired
	private PupilRepository pupilRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MarkRepository markRepository;

	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private ParentRepository parentRepository;

	@Secured("ADMIN")
	@RequestMapping(method = RequestMethod.POST, value = "/userID/{userId}")
	public ResponseEntity<?> newPupil(@Valid @RequestBody PupilDTO addPupil, @PathVariable Integer userId) {
		try {
			PupilEntity pupil = new PupilEntity();
			UserEntity user = userRepository.findById(userId).orElse(null);
			if (user != null) {
				pupil.setUser(user);
			}
			log.info("Adding new pupil{}", addPupil.getFirstname());
			pupil.setFirstname(addPupil.getFirstname());
			log.info("Adding new pupil{}", addPupil.getLastname());
			pupil.setLastname(addPupil.getLastname());
			log.info("Adding new pupil{}", addPupil.getSemester());
			pupil.setSemester(addPupil.getSemester());
			log.info("Adding new pupil{}", addPupil.getGrade());
			pupil.setGrade(addPupil.getGrade());
			log.info("Adding new pupil{}", addPupil.getDateOfBirth());
			pupil.setDateOfBirth(addPupil.getDateOfBirth());
			log.info("Adding new pupil{}", addPupil.getMarks());
			pupil.setMarks(addPupil.getMarks());
			log.info("Adding new pupil{}", addPupil.getUser());
			pupil.setUser(user);
			pupilRepository.save(pupil);
			log.error("An exception occured while posting a new pupil");
			return new ResponseEntity<>(pupil, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(1, "Exception occurred:" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Secured("ADMIN")
	@RequestMapping(method = RequestMethod.POST, value = "/{pupilId}/mark/{markId}/subject/{subjectId}")
	public ResponseEntity<?> addMarkToSubject(@PathVariable Integer pupilId, @PathVariable Integer markId,
			@PathVariable Integer subjectId, @RequestParam ESemester semester) {
		try {
			PupilEntity pupil = pupilRepository.findById(pupilId).orElse(null);
			MarkEntity mark = markRepository.findById(markId).orElse(null);
			SubjectEntity subject = subjectRepository.findById(subjectId).orElse(null);

			if (pupil == null || mark == null || subject == null) {
				return new ResponseEntity<>(new RESTError(1, "Invalid pupil, mark, or subject IDs"),
						HttpStatus.BAD_REQUEST);
			}

			pupil.setSemester(semester);

			MarkEntity newMark = new MarkEntity();
			log.error("An exception occured while setting new mark!");
			newMark.setMark(mark.getMark());
			log.error("An exception occured while setting mark to pupil!");
			newMark.setPupil(pupil);
			log.error("An exception occured while setting mark to subject!");
			newMark.setSubject(subject);
			log.error("An exception occured while adding mark to pupil!");
			pupil.getMarks().add(newMark);
			markRepository.save(newMark);

			return new ResponseEntity<>(pupil, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Exception occurred when changing pupil's data!");
			return new ResponseEntity<>(new RESTError(2, "Error while processing the request: " + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@Secured("ADMIN")
	@RequestMapping(method = RequestMethod.POST, value = "/{pupilId}/parent/{parentId}")
	public ResponseEntity<?> addParentToPupil(@PathVariable Integer pupilId, @PathVariable Integer parentId) {
		try {
			PupilEntity pupil = pupilRepository.findById(pupilId).get();
			ParentEntity parent = parentRepository.findById(parentId).get();
			log.error("Exception occurred when setting parent to pupil!");
			pupil.setParent(parent);
			pupilRepository.save(pupil);
			return new ResponseEntity<>(pupil, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Exception occurred when adding parent to pupil!");
			return new ResponseEntity<>(new RESTError(1, "Exception occurred:" + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@Secured("ADMIN")
	@RequestMapping(method = RequestMethod.POST, value = "/{pupilId}/subject/{subjectId}")
	public ResponseEntity<?> addSubjectToPupil(@PathVariable Integer pupilId, @PathVariable Integer subjectId) {
		try {
			PupilEntity pupil = pupilRepository.findById(pupilId).get();
			SubjectEntity subject = subjectRepository.findById(subjectId).get();
			log.error("Exception occurred when setting subject to pupil!");
			pupil.getSubjects().add(subject);
			pupilRepository.save(pupil);
			return new ResponseEntity<>(pupil, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Exception occurred when adding subject to pupil!");
			return new ResponseEntity<>(new RESTError(1, "Exception occurred:" + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@Secured({"ADMIN","TEACHER","PARENT"})
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllPupils() {
		log.error("An exception occured while getting all pupils");
		return new ResponseEntity<>(pupilRepository.findAll(), HttpStatus.OK);
	}

	@Secured("ADMIN")
	@RequestMapping(method = RequestMethod.PUT, value = "/{pupilId}")
	public ResponseEntity<?> changePupil(@RequestBody PupilDTO updatedPupil, @PathVariable Integer pupilId) {
		try {
			PupilEntity pupil = pupilRepository.findById(pupilId).get();
			if (updatedPupil.getFirstname() != null) {
				log.error("An exception occured while changing pupil's firstname!");
				pupil.setFirstname(updatedPupil.getFirstname());
			}
			if (updatedPupil.getLastname() != null) {
				log.error("An exception occured while changing pupil's lastname!");
				pupil.setLastname(updatedPupil.getLastname());
			}
			if (updatedPupil.getDateOfBirth() != null) {
				log.error("An exception occured while changing pupil's date of birth!");
				pupil.setDateOfBirth(updatedPupil.getDateOfBirth());
			}
			if (updatedPupil.getSemester() != null) {
				log.error("An exception occured while changing pupil's semester!");
				pupil.setSemester(updatedPupil.getSemester());
			}
			pupilRepository.save(pupil);
			return new ResponseEntity<>(pupil, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Exception occurred when changing pupil!");
			return new ResponseEntity<>(new RESTError(1, "Exception occurred:" + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@Secured("ADMIN")
	@RequestMapping(method = RequestMethod.DELETE, value = "/{pupilId}")
	public ResponseEntity<?> deletePupil(@PathVariable Integer pupilId) {
		try {
			if (pupilRepository.existsById(pupilId)) {
				pupilRepository.deleteById(pupilId);
				return new ResponseEntity<>("Pupil is deleted", HttpStatus.OK);
			} else {
				log.info("Pupil is not foubd in the DB");
				return new ResponseEntity<>(new RESTError(1, "Pupil is not found"), HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			log.error("Error deleting pupil:" + e.getMessage());
			return new ResponseEntity<>(new RESTError(2, "Error deleting pupil"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Secured("ADMIN")
	@RequestMapping(method = RequestMethod.GET, value = "/findById/{pupilId}")
	public ResponseEntity<PupilEntity> findById(@PathVariable Integer pupilId) {
		PupilEntity pupil = pupilRepository.findById(pupilId).get();
		return new ResponseEntity<PupilEntity>(pupil, HttpStatus.OK);
	}

	@Secured("ADMIN")
	@RequestMapping(method = RequestMethod.GET, value = "/findByFirstname/{firstname}")
	public ResponseEntity<?> findByFirstname(@PathVariable String firstname) {
		List<PupilEntity> pupils = pupilRepository.findByFirstname(firstname);
		return new ResponseEntity<>(pupils, HttpStatus.OK);
	}

	@Secured("ADMIN")
	@RequestMapping(method = RequestMethod.GET, value = "/findByLastname/{lastname}")
	public ResponseEntity<?> findByLastname(@PathVariable String lastname) {
		List<PupilEntity> pupils = pupilRepository.findByLastname(lastname);
		return new ResponseEntity<>(pupils, HttpStatus.OK);
	}

	@Secured("ADMIN")
	@RequestMapping(method = RequestMethod.GET, value = "/findByDateOfBirth")
	public ResponseEntity<?> findByDateOfBirth(@RequestParam LocalDate dateOfBirth) {
		List<PupilEntity> pupils = pupilRepository.findByDateOfBirth(dateOfBirth);
		return new ResponseEntity<>(pupils, HttpStatus.OK);
	}

}
