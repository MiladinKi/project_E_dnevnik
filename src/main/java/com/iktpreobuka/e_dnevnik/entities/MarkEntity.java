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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
public class MarkEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "mark_id")
	private Integer id;
	@NotNull(message = "Mark must be provided")
	@Min(value = 1, message = "Mark must be between 1 and 5")
	@Max(value = 5, message = "Mark must be between 1 and 5")
	private Integer mark;
	@Version
	private Integer version;

	@JsonIgnore
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "pupil")
	protected PupilEntity pupil;

	@JsonIgnore
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "subject")
	protected SubjectEntity subject;

	public MarkEntity() {
		super();
	}

	public MarkEntity(Integer id,
			@NotNull(message = "Mark must be provided") @Min(value = 1, message = "Mark must be between 1 and 5") @Max(value = 5, message = "Mark must be between 1 and 5") Integer mark,
			Integer version, PupilEntity pupil, SubjectEntity subject) {
		super();
		this.id = id;
		this.mark = mark;
		this.version = version;
		this.pupil = pupil;
		this.subject = subject;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMark() {
		return mark;
	}

	public void setMark(Integer mark) {
		this.mark = mark;
	}

	public PupilEntity getPupil() {
		return pupil;
	}

	public void setPupil(PupilEntity pupil) {
		this.pupil = pupil;
	}

	public SubjectEntity getSubject() {
		return subject;
	}

	public void setSubject(SubjectEntity subject) {
		this.subject = subject;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}
