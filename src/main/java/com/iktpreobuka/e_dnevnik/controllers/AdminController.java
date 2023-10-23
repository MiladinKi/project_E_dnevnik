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
import com.iktpreobuka.e_dnevnik.entities.AdminEntity;
import com.iktpreobuka.e_dnevnik.entities.UserEntity;
import com.iktpreobuka.e_dnevnik.entities.dtos.AdminDTO;
import com.iktpreobuka.e_dnevnik.repositories.AdminRepository;
import com.iktpreobuka.e_dnevnik.repositories.UserRepository;
import com.iktpreobuka.e_dnevnik.security.Views;
import com.iktpreobuka.e_dnevnik.services.AdminService;
import com.iktpreobuka.e_dnevnik.util.RESTError;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/admins")
@Secured("ADMIN")
public class AdminController {

	@JsonView(Views.Admin.class)
	private static final Logger log = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	public AdminService adminService;

	@Secured("ADMIN")
	@JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.POST, value = "/{userId}")
	public ResponseEntity<?> newAdmin(@Valid @RequestBody AdminDTO addAdmin, @PathVariable Integer userId) {
		try {
			AdminEntity newAdmin = new AdminEntity();
			UserEntity user = userRepository.findById(userId).get();
			log.info("Adding new administrator{}", addAdmin.getFirstname());
			newAdmin.setFirstname(addAdmin.getFirstname());
			log.info("Adding new administrator{}", addAdmin.getLastname());
			newAdmin.setLastname(addAdmin.getLastname());
			log.info("Adding new administrator{}", addAdmin.getEmail());
			newAdmin.setEmail(addAdmin.getEmail());
			log.info("Adding new administrator{}", addAdmin.getUser());
			newAdmin.setUser(user);
			log.error("An exception occured while posting a new administrator");
			adminRepository.save(newAdmin);
			return new ResponseEntity<AdminEntity>(newAdmin, HttpStatus.CREATED);
		} catch (Exception e) {
			
			return new ResponseEntity<>(new RESTError(1, "Exception occurred:" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Secured("ADMIN")
	@JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllAdmins() {
		List<AdminEntity> admins = (List<AdminEntity>) adminRepository.findAll();
		log.error("An exception occured while getting all admins");
		return new ResponseEntity<>(admins, HttpStatus.OK);
	}

	@Secured("ADMIN")
	@JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.PUT, value = "/{adminID}")
	public ResponseEntity<?> changeAdminById(@RequestBody AdminDTO updatedAdmin, @PathVariable Integer adminID) {
		try {
			AdminEntity newAdmin = adminRepository.findById(adminID).orElse(null);

			if (newAdmin == null) {
				log.info("There is no admin with that ID");
				return new ResponseEntity<>(new RESTError(1, "There is no admin with that ID"), HttpStatus.BAD_REQUEST);
			}

			if (updatedAdmin.getFirstname() != null) {
				log.error("An exception occured while changing admin's firstname!");
				newAdmin.setFirstname(updatedAdmin.getFirstname());
			}

			if (updatedAdmin.getLastname() != null) {
				log.error("An exception occured while changing admin's lastname!");
				newAdmin.setLastname(updatedAdmin.getLastname());
			}

			if (updatedAdmin.getEmail() != null) {
				log.error("An exception occured while changing admin's email!");
				newAdmin.setEmail(updatedAdmin.getEmail());
			}
			adminRepository.save(newAdmin);
			return new ResponseEntity<>(newAdmin, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			log.error("Exception occurred when changing admin!");
			return new ResponseEntity<>(new RESTError(2, "Exception occurred:" + e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@Secured("ADMIN")
	@JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.DELETE, value = "/{adminID}")
	public ResponseEntity<?> deleteUser(@PathVariable Integer adminID) {
		try {
			if (adminRepository.existsById(adminID)) {
				adminRepository.deleteById(adminID);
				log.info("Admin is deleted");
				return new ResponseEntity<>("Admin is deleted", HttpStatus.OK);
			} else {
				log.info("Admin not found in the DB");
				return new ResponseEntity<>(new RESTError(1, "Admin not found"), HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			log.error("Error deleting admin: " + e.getMessage());
			return new ResponseEntity<>(new RESTError(1, "Exception occurred:" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Secured("ADMIN")
	@JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.GET, value = "/findById/{adminId}")
	public ResponseEntity<AdminEntity> findById(@PathVariable Integer adminId){
		AdminEntity admin = adminRepository.findById(adminId).orElse(null);
		return new ResponseEntity<AdminEntity>(admin, HttpStatus.OK);
	}
	
	@Secured("ADMIN")
	@JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.GET, value = "/findByFirstname/{firstname}")
	public ResponseEntity<?> findAdminByFirstname(@PathVariable String firstname){
		List<AdminEntity> admins = adminRepository.findByFirstname(firstname);
		return new ResponseEntity<> (admins, HttpStatus.OK);
	}
	
	@Secured("ADMIN")
	@JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.GET, value = "/findByLastname/{lastname}")
	public ResponseEntity<?> findAdminByLastname(@PathVariable String lastname){
		List<AdminEntity> admins = adminRepository.findByLastname(lastname);
		return new ResponseEntity<> (admins, HttpStatus.OK);
	}
	
	@Secured("ADMIN")
	@JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.GET, value = "/findByEmail/{email}")
	public ResponseEntity<?> findAdminByEmail(@PathVariable String email){
		AdminEntity admin = adminRepository.findByEmail(email);
		return new ResponseEntity<> (admin, HttpStatus.OK);
	}

}
