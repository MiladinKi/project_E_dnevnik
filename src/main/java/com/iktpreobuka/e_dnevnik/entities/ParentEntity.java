package com.iktpreobuka.e_dnevnik.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class ParentEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(name = "Parent firstname")
	@NotBlank(message = "Parent firstname has to be providede.")
	@Size(min = 2, max = 30, message = "Parent firstname name must be between {min} and {max} characters long.")
	private String firstname;
	@Column(name = "Parent lastname")
	@NotBlank(message = "Parent lastname has to be provided.")
	@Size(min = 2, max = 30, message = "Parent lasname name must be between {min} and {max} characters long.")
	private String lastname;
	@Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "Email is not valid.")
	private String email;
	@Version
	private Integer version;
	


	@OneToOne
	@JoinColumn(name = "user_id")
	private UserEntity user;
	
	@JsonIgnore
	@OneToMany(mappedBy = "parent", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<PupilEntity> pupils;

	public ParentEntity() {
		super();
	}



	public ParentEntity(Integer id,
			@NotBlank(message = "Parent firstname has to be providede.") @Size(min = 2, max = 30, message = "Parent firstname name must be between {min} and {max} characters long.") String firstname,
			@NotBlank(message = "Parent lastname has to be provided.") @Size(min = 2, max = 30, message = "Parent lasname name must be between {min} and {max} characters long.") String lastname,
			@NotNull @Pattern(regexp = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$\r\n", message = "Email is not valid") String email,
			Integer version,  UserEntity user, List<PupilEntity> pupils) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.version = version;
		this.user = user;
		this.pupils = pupils;
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

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}



	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public List<PupilEntity> getPupils() {
		return pupils;
	}

	public void setPupils(List<PupilEntity> pupils) {
		this.pupils = pupils;
	}



	

}
