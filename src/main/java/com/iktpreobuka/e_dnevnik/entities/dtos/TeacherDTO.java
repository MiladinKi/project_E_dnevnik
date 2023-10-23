package com.iktpreobuka.e_dnevnik.entities.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TeacherDTO {
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

	public TeacherDTO() {
		super();
	}

	public TeacherDTO(
			@NotBlank(message = "Teacher firstname has to be providede.") @Size(min = 2, max = 30, message = "Teacher firstname name must be between {min} and {max} characters long.") String firstname,
			@NotBlank(message = "Teacher lastname has to be providede.") @Size(min = 2, max = 30, message = "Teacher lastname name must be between {min} and {max} characters long.") String lastname,
			@NotNull @Min(value = 22, message = "Week class fund must be between 22 and 65") @Max(value = 65, message = "Week class fund must be between 22 and 65") Integer age) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.age = age;
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

}
