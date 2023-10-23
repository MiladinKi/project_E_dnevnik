package com.iktpreobuka.e_dnevnik.controllers;

import java.util.List;
import java.util.Optional;

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
import com.iktpreobuka.e_dnevnik.entities.ParentEntity;
import com.iktpreobuka.e_dnevnik.entities.PupilEntity;
import com.iktpreobuka.e_dnevnik.entities.UserEntity;
import com.iktpreobuka.e_dnevnik.repositories.ParentRepository;
import com.iktpreobuka.e_dnevnik.repositories.PupilRepository;
import com.iktpreobuka.e_dnevnik.repositories.UserRepository;
import com.iktpreobuka.e_dnevnik.security.Views;
import com.iktpreobuka.e_dnevnik.util.RESTError;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "api/v1/parents")
public class ParentController {

	@JsonView(Views.Admin.class)
	private static final Logger log = LoggerFactory.getLogger(ParentController.class);

	@Autowired
	private ParentRepository parentRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PupilRepository pupilRepository;

//	@RequestMapping(method = RequestMethod.POST, value = "/pupils/{pupilId}")
//	public ResponseEntity<?> newParent(@Valid @RequestBody ParentEntity addParent, @PathVariable Integer pupilId) {
//		try {
//			ParentEntity parent = new ParentEntity();
//			UserEntity user = userRepository.findById(pupilId).get();
//			log.info("Adding new parent{}", addParent.getFirstname());
//			parent.setFirstname(addParent.getFirstname());
//			log.info("Adding new parent{}", addParent.getLastname());
//			parent.setLastname(addParent.getLastname());
//			log.info("Adding new parent{}", addParent.getEmail());
//			parent.setEmail(addParent.getEmail());
//			log.info("Adding new parent{}", addParent.getUser());
//			parent.setUser(addParent.getUser());
//			parentRepository.save(parent);
//			log.error("An exception occured while posting a new parent");
//			return new ResponseEntity<>(parent, HttpStatus.CREATED);
//		} catch (Exception e) {
//			return new ResponseEntity<>(new RESTError(1, "Exception occurred:" + e.getMessage()),
//					HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
//	

	@Secured("ADMIN")
	@RequestMapping(method = RequestMethod.POST, value = "/user/{userId}/pupils/{pupilId}")
	public ResponseEntity<?> newParent(@Valid @RequestBody ParentEntity addParent, @PathVariable Integer userId,
			@PathVariable Integer pupilId) {
		try {
			ParentEntity parent = new ParentEntity();

			Optional<PupilEntity> optionalPupil = pupilRepository.findById(pupilId);
			if (optionalPupil.isPresent()) {
				PupilEntity pupil = optionalPupil.get();
				UserEntity user = userRepository.findById(userId).orElse(null); 
																				
				if (user != null) {
					log.info("Adding new parent {}", addParent.getFirstname());
					parent.setFirstname(addParent.getFirstname());
					log.info("Adding new parent {}", addParent.getLastname());
					parent.setLastname(addParent.getLastname());
					log.info("Adding new parent {}", addParent.getEmail());
					parent.setEmail(addParent.getEmail());
					log.info("Adding new parent {}", addParent.getUser());
					parent.setUser(user); // Pridružite korisnika roditelju
					parentRepository.save(parent);
					return new ResponseEntity<>(parent, HttpStatus.CREATED);
				} else {
					// Ako korisnik (roditelj) nije pronađen, vratite odgovarajući odgovor
					return new ResponseEntity<>(new RESTError(2, "User not found for parent"), HttpStatus.NOT_FOUND);
				}
			} else {
				// Ako učenik nije pronađen, vratite odgovarajući odgovor
				return new ResponseEntity<>(new RESTError(1, "Pupil with id " + pupilId + " not found"),
						HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(new RESTError(1, "Exception occurred:" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Secured("ADMIN")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllParents() {
		log.error("An exception occured while getting all parents");
		return new ResponseEntity<>(parentRepository.findAll(), HttpStatus.OK);
	}

	@Secured("ADMIN")
	@RequestMapping(method = RequestMethod.PUT, value = "/{parentId}")
	public ResponseEntity<?> changeParent(@RequestBody ParentEntity updatedParent, @PathVariable Integer parentId) {
		try {
			ParentEntity parent = parentRepository.findById(parentId).get();
			if (updatedParent.getFirstname() != null) {
				log.error("An exception occured while changing parent's firstname!");
				parent.setFirstname(updatedParent.getFirstname());
			}
			if (updatedParent.getLastname() != null) {
				log.error("An exception occured while changing parent's lastname!");
				parent.setLastname(updatedParent.getLastname());
			}
			if (updatedParent.getEmail() != null) {
				log.error("An exception occured while changing parent's email!");
				parent.setEmail(updatedParent.getEmail());
			}
			parentRepository.save(parent);
			return new ResponseEntity<>(parent, HttpStatus.OK);

		} catch (Exception e) {
			log.error("Exception occurred when changing parent!");
			return new ResponseEntity<>(new RESTError(2, "Exception occurred:" + e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}

	}

	@Secured("ADMIN")
	@RequestMapping(method = RequestMethod.DELETE, value = "/{parentId}")
	public ResponseEntity<?> deleteParentById(@PathVariable Integer parentId) {
		try {
			if (parentRepository.existsById(parentId)) {
				parentRepository.deleteById(parentId);
				return new ResponseEntity<>("Parent is deleted", HttpStatus.OK);
			} else {
				log.error("Parent is not found!");
				return new ResponseEntity<>(new RESTError(1, "Parent is not found"), HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			log.error("Exception occurred while deleting parent!");
			return new ResponseEntity<>(new RESTError(2, "Error deleting parent"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Secured("ADMIN")
	@RequestMapping(method = RequestMethod.GET, value = "/findById/{parentId}")
	public ResponseEntity<ParentEntity> findById(@PathVariable Integer parentId) {
		ParentEntity parent = parentRepository.findById(parentId).get();
		return new ResponseEntity<ParentEntity>(parent, HttpStatus.OK);
	}

	@Secured("ADMIN")
	@RequestMapping(method = RequestMethod.GET, value = "/findByFirstname/{firstname}")
	public ResponseEntity<?> findByFirstname(@PathVariable String firstname) {
		List<ParentEntity> parents = parentRepository.findByFirstname(firstname);
		return new ResponseEntity<>(parents, HttpStatus.OK);
	}

	@Secured("ADMIN")
	@RequestMapping(method = RequestMethod.GET, value = "/findByLastname/{lastname}")
	public ResponseEntity<?> findByLastname(@PathVariable String lastname) {
		List<ParentEntity> parents = parentRepository.findByLastname(lastname);
		return new ResponseEntity<>(parents, HttpStatus.OK);
	}

	@Secured("ADMIN")
	@RequestMapping(method = RequestMethod.GET, value = "/findByEmail/{email}")
	public ResponseEntity<?> findByEmail(@PathVariable String email) {
		List<ParentEntity> parents = parentRepository.findByEmail(email);
		return new ResponseEntity<>(parents, HttpStatus.OK);
	}

}
