package com.iktpreobuka.e_dnevnik.entities;

import java.util.ArrayList;
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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class TeacherEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "teacher_id")
	private Integer id;
	@Column(name = "Teacher firstname")
	@NotBlank(message = "Teacher firstname has to be providede.")
	@Size(min = 2, max = 30, message = "Teacher firstname name must be between {min} and {max} characters long.")
	private String firstname;
	@Column(name = "Teacher lastname")
	@NotBlank(message = "Teacher lastname has to be providede.")
	@Size(min = 2, max = 30, message = "Teacher lastname name must be between {min} and {max} characters long.")
	private String lastname;
	@NotNull
	@Min(value = 22, message = "Week class fund must be between 22 and 65")
	@Max(value = 65, message = "Week class fund must be between 22 and 65")
	private Integer age;
	@Version
	private Integer version;
	
//	private ERole role;

	@OneToOne
	@JoinColumn(name = "user_id")
	private UserEntity user;

	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinTable(name = "teacher_subject", joinColumns = {
			@JoinColumn(name = "teacher_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "subject_id", nullable = false, updatable = false) })
	private List<SubjectEntity> subjects;

	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinTable(name = "teacher_pupil", joinColumns = {
			@JoinColumn(name = "teacher_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "pupil_id", nullable = false, updatable = false) })
	private List<PupilEntity> pupils;

	public TeacherEntity() {
		super();
	}

	public TeacherEntity(Integer id, String firstname, String lastname, Integer age, Integer version, ERole role,
			UserEntity user, List<SubjectEntity> subjects, List<PupilEntity> pupils) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.age = age;
		this.version = version;
//		this.role = role;
		this.user = user;
		this.subjects = subjects;
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

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

//	public ERole getRole() {
//		return role;
//	}
//
//	public void setRole(ERole role) {
//		this.role = role;
//	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public List<SubjectEntity> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<SubjectEntity> subjects) {
		this.subjects = subjects;
	}

	public List<PupilEntity> getPupils() {
		return pupils;
	}

	public void setPupils(List<PupilEntity> pupils) {
		this.pupils = pupils;
	}

}
