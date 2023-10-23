package com.iktpreobuka.e_dnevnik.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class AdminEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "admin_id")
	private Integer id;
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

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = true)
	private UserEntity user;

	public AdminEntity() {
		super();
	}

	public AdminEntity(Integer id,
			@NotBlank(message = "Admin firstname has to be providede.") @Size(min = 2, max = 30, message = "Admin firstname name must be between {min} and {max} characters long.") String firstname,
			@NotBlank(message = "Admin lastname has to be provided.") @Size(min = 2, max = 30, message = "Admin lastname name must be between {min} and {max} characters long.") String lastname,
			String email, UserEntity user) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.user = user;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
