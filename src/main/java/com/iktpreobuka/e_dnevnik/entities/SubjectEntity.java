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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class SubjectEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "subject_id")
	private Integer id;
	@Column(name = "Subject name")
	@NotBlank(message = "Subject name has to be providede.")
	@Size(min = 2, max = 30, message = "Subject name must be between {min} and {max} characters long.")
	private String name;
	@NotNull
	@Min(value = 1, message = "Week class fund must be between 1 and 8")
	@Max(value = 8, message = "Week class fund must be between 1 and 8")
	private Integer weekClassFund;
	@Version
	private Integer version;

	
	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinTable(name = "Subject_Teacher",joinColumns = 
	{@JoinColumn(name = "subject_id", nullable=false, updatable=false)},
	inverseJoinColumns = {@JoinColumn(name = "teacher_id", nullable = false, updatable = false)})
	protected List<TeacherEntity> teachers = new ArrayList<>();
	
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinTable(name = "Subject_Pupil",joinColumns = 
	{@JoinColumn(name = "subject_id", nullable=false, updatable=false)},
	inverseJoinColumns = {@JoinColumn(name = "pupil_id", nullable = false, updatable = false)})
	protected List<PupilEntity> pupils = new ArrayList<>();


	@JsonIgnore
	@OneToMany(mappedBy = "subject", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	protected List<MarkEntity> marks = new ArrayList<>();

	public SubjectEntity() {
		super();
	}

	public SubjectEntity(Integer id, Integer weekClassFund,
			@NotNull(message = "Name must be provided") @Size(min = 2, max = 30, message = "Name must be between {min} and {max} characters long") String name,
			List<TeacherEntity> teachers, List<PupilEntity> pupils, List<MarkEntity> marks) {
		super();
		this.id = id;
		this.weekClassFund = weekClassFund;
		this.name = name;
		this.teachers = teachers;
		this.pupils = pupils;
		this.marks = marks;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getWeekClassFund() {
		return weekClassFund;
	}

	public void setWeekClassFund(Integer weekClassFund) {
		this.weekClassFund = weekClassFund;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<TeacherEntity> getTeachers() {
		return teachers;
	}

	public void setTeachers(List<TeacherEntity> teachers) {
		this.teachers = teachers;
	}

	public List<PupilEntity> getPupils() {
		return pupils;
	}

	public void setPupils(List<PupilEntity> pupils) {
		this.pupils = pupils;
	}

	public List<MarkEntity> getMarks() {
		return marks;
	}

	public void setMarks(List<MarkEntity> marks) {
		this.marks = marks;
	}


}
