package com.iktpreobuka.e_dnevnik.controllers;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.e_dnevnik.entities.UserEntity;
import com.iktpreobuka.e_dnevnik.entities.dtos.UserDTO;
import com.iktpreobuka.e_dnevnik.repositories.UserRepository;
import com.iktpreobuka.e_dnevnik.security.Views;
import com.iktpreobuka.e_dnevnik.util.Encryption;
import com.iktpreobuka.e_dnevnik.util.RESTError;

import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@JsonView(Views.Admin.class)
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private SecretKey secretKey;
	
	@Value ("${spring.security.token-duration}")
	private Integer tokenDuration;
	
	private String getJWTToken(UserEntity userEntity) {
		  String roleAsString = userEntity.getRole().name(); // Pretvaranje ERole u String
		    List<GrantedAuthority> grantedAuthorities = AuthorityUtils
		            .commaSeparatedStringToAuthorityList(roleAsString);
		String token = Jwts.builder().setId("softtekJWT").setSubject(userEntity.getEmail())
				.claim("authorities",
						grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + this.tokenDuration)).signWith(this.secretKey)
				.compact();
		return "Bearer " + token;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestParam("user") String email, @RequestParam("password") String pwd) {
		UserEntity userEntity = userRepository.findByEmail(email);
		if (userEntity != null && Encryption.validatePassword(pwd, userEntity.getPassword())) {
			String token = getJWTToken(userEntity);
			UserDTO user = new UserDTO();
			user.setUser(email);
			user.setToken(token);
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
		return new ResponseEntity<>("Wrong credentials", HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<UserEntity>> getAllUsers() {
		List<UserEntity> users = (List<UserEntity>) userRepository.findAll();
		log.info("You got all users from the DB");
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{userId}")
	public ResponseEntity<?> changeUserByID(@PathVariable Integer userId, @RequestBody UserEntity updatedUser) {
		try {
			UserEntity user = userRepository.findById(userId).get();
			if(user == null) {
			return new ResponseEntity<>(new RESTError(1, "User is not found"), HttpStatus.BAD_REQUEST);
			}
			if (updatedUser.getUsername() != null) {
				log.error("An exception occured while changing user's username!");
				user.setUsername(updatedUser.getUsername());
			
			}
			if (updatedUser.getEmail() != null) {
				log.error("An exception occured while changing user's email!");
				user.setEmail(updatedUser.getEmail());
			
			}
			if (updatedUser.getPassword() != null) {
				log.error("An exception occured while changing user's password!");
				user.setPassword(updatedUser.getPassword());
			
			}
			if (updatedUser.getRole() != null) {
				log.error("An exception occured while changing user's role!");
				user.setRole(updatedUser.getRole());
				
			}
			userRepository.save(user);
			return new ResponseEntity<UserEntity>(user, HttpStatus.OK);

		} catch (Exception e) {
			log.error("Exception occurred when changing user data!");
			return new ResponseEntity<>(new RESTError(2, "Exception occurred:" + e.getMessage()), HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{userID}")
	public ResponseEntity<?> deleteUser(@PathVariable Integer userID) {
	    try {
	        if (userRepository.existsById(userID)) {
	            userRepository.deleteById(userID);
	            log.info("User is deleted");
	            return new ResponseEntity<>("User is deleted", HttpStatus.OK);
	        } else {
	            log.info("User not found in the DB");
	            return new ResponseEntity<>(new RESTError(1, "User not found"), HttpStatus.BAD_REQUEST);
	        }
	    } catch (Exception e) {
	        log.error("Error deleting User: " + e.getMessage());
	        return new ResponseEntity<>(new RESTError(1, "Error deleting User"), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	@RequestMapping(method = RequestMethod.GET, value = "/findById/{userId}")
	public ResponseEntity<UserEntity> findById(@PathVariable Integer userId){
		UserEntity user = userRepository.findById(userId).get();
		return new ResponseEntity<UserEntity>(user, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/findByEmail/{email}")
	public ResponseEntity<UserEntity> findByEmail(@PathVariable String email){
		UserEntity user = userRepository.findByEmail(email);
		return new ResponseEntity<UserEntity>(user, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/findByUsername")
	public ResponseEntity<UserEntity> findByUsername(@RequestParam String username){
		UserEntity user = userRepository.findByUsername(username);
		return new ResponseEntity<UserEntity>(user, HttpStatus.OK);
	}
}
