package com.iktpreobuka.e_dnevnik.entities.dtos;

import com.iktpreobuka.e_dnevnik.entities.UserEntity;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AdminDTO {
	@NotBlank(message = "Admin firstname has to be providede.")
	@Size(min = 2, max = 30, message = "Admin firstname name must be between {min} and {max} characters long.")
	@Column(name = "Admin firstname")
	private String firstname;
	@Column(name = "Admin lastname")
	@NotBlank(message = "Admin lastname has to be provided.")
	@Size(min = 2, max = 30, message = "Admin lastname name must be between {min} and {max} characters long.")
	private String lastname;
	@Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "Email is not valid.")
	private String email;

	private UserEntity user;

	public AdminDTO() {
		super();
	}

	public AdminDTO(
			@NotBlank(message = "Admin firstname has to be providede.") @Size(min = 2, max = 30, message = "Admin firstname name must be between {min} and {max} characters long.") String firstname,
			@NotBlank(message = "Admin lastname has to be provided.") @Size(min = 2, max = 30, message = "Admin lastname name must be between {min} and {max} characters long.") String lastname,
			@Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "Email is not valid.") String email,
			UserEntity user) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.user = user;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

}
