package com.iktpreobuka.e_dnevnik.entities.dtos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.iktpreobuka.e_dnevnik.entities.EGrade;
import com.iktpreobuka.e_dnevnik.entities.ESemester;
import com.iktpreobuka.e_dnevnik.entities.MarkEntity;
import com.iktpreobuka.e_dnevnik.entities.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PupilDTO {
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

	private ESemester semester;

	private EGrade grade;

	private UserEntity user;

	protected List<MarkEntity> marks = new ArrayList<MarkEntity>();

	public PupilDTO() {
		super();
	}

	public PupilDTO(
			@NotBlank(message = "Pupil firstname has to be providede.") @Size(min = 2, max = 30, message = "Pupil firstname name must be between {min} and {max} characters long.") String firstname,
			@NotBlank(message = "Pupil lastname has to be providede.") @Size(min = 2, max = 30, message = "Pupil lastname name must be between {min} and {max} characters long.") String lastname,
			LocalDate dateOfBirth, ESemester semester, EGrade grade, UserEntity user, List<MarkEntity> marks) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.dateOfBirth = dateOfBirth;
		this.semester = semester;
		this.grade = grade;
		this.user = user;
		this.marks = marks;
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

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
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

}
