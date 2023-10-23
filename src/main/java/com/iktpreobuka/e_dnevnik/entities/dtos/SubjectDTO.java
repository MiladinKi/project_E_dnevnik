package com.iktpreobuka.e_dnevnik.entities.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SubjectDTO {
	@Column(name = "Subject name")
	@NotBlank(message = "Subject name has to be providede.")
	@Size(min = 2, max = 30, message = "Subject name must be between {min} and {max} characters long.")
	private String name;
	@NotNull
	@Min(value = 1, message = "Week class fund must be between 1 and 8")
	@Max(value = 8, message = "Week class fund must be between 1 and 8")
	private Integer weekClassFund;

	public SubjectDTO() {
		super();
	}

	public SubjectDTO(
			@NotBlank(message = "Subject name has to be providede.") @Size(min = 2, max = 30, message = "Subject name must be between {min} and {max} characters long.") String name,
			@NotNull @Min(value = 1, message = "Week class fund must be between 1 and 8") @Max(value = 8, message = "Week class fund must be between 1 and 8") Integer weekClassFund) {
		super();
		this.name = name;
		this.weekClassFund = weekClassFund;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getWeekClassFund() {
		return weekClassFund;
	}

	public void setWeekClassFund(Integer weekClassFund) {
		this.weekClassFund = weekClassFund;
	}

}
