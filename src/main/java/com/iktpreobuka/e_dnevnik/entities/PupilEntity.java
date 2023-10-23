package com.iktpreobuka.e_dnevnik.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;

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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class PupilEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "pupil_id")
	private Integer id;
	@Column(name = "Pupil firstname")
	@NotBlank(message = "Pupil firstname has to be providede.")
	@Size(min = 2, max = 30, message = "Pupil firstname name must be between {min} and {max} characters long.")
	private String firstname;
	@Column(name = "Pupil lastname")
	@NotBlank(message = "Pupil lastname has to be providede.")
	@Size(min = 2, max = 30, message = "Pupil lastname name must be between {min} and {max} characters long.")
	private String lastname;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate dateOfBirth;
	@Version
	private Integer version;

	private ESemester semester;

	private EGrade grade;

	@OneToOne
	@JoinColumn(name = "user_id")
	private UserEntity user;

	@JsonIgnore
	@OneToMany(mappedBy = "pupil", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	protected List<MarkEntity> marks = new ArrayList<MarkEntity>();

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent")
	protected ParentEntity parent;

	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinTable(name = "Subject_Pupil", joinColumns = {
			@JoinColumn(name = "pupil_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "subject_id", nullable = false, updatable = false) })
	protected List<SubjectEntity> subjects = new ArrayList<>();

	public PupilEntity() {
		super();
	}

	public PupilEntity(Integer id,
			@NotBlank(message = "Pupil firstname has to be providede.") @Size(min = 2, max = 30, message = "Pupil firstname name must be between {min} and {max} characters long.") String firstname,
			@NotBlank(message = "Pupil lastname has to be providede.") @Size(min = 2, max = 30, message = "Pupil lastname name must be between {min} and {max} characters long.") String lastname,
			LocalDate dateOfBirth, Integer version, ESemester semester, EGrade grade, UserEntity user,
			List<MarkEntity> marks, ParentEntity parent, List<SubjectEntity> subjects) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.dateOfBirth = dateOfBirth;
		this.version = version;
		this.semester = semester;
		this.grade = grade;
		this.user = user;
		this.marks = marks;
		this.parent = parent;
		this.subjects = subjects;
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

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public ESemester getSemester() {
		return semester;
	}

	public void setSemester(ESemester semester) {
		this.semester = semester;
	}

	public EGrade getGrade() {
		return grade;
	}

	public void setGrade(EGrade grade) {
		this.grade = grade;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public List<MarkEntity> getMarks() {
		return marks;
	}

	public void setMarks(List<MarkEntity> marks) {
		this.marks = marks;
	}

	public ParentEntity getParent() {
		return parent;
	}

	public void setParent(ParentEntity parent) {
		this.parent = parent;
	}

	public List<SubjectEntity> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<SubjectEntity> subjects) {
		this.subjects = subjects;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

}
