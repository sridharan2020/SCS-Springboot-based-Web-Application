package com.example.scs.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class Application {
    private Long application_id;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private String date_of_application;

    private Boolean status;

    @Override
    public String toString() {
        return "Application{" +
                "application_id=" + application_id +
                ", date_of_application=" + date_of_application +
                ", status=" + status +
                ", vertical_id=" + vertical_id +
                ", stud_id=" + stud_id +
                ", program='" + program + '\'' +
                ", list_of_pors='" + list_of_pors + '\'' +
                ", commitment=" + commitment +
                ", skills='" + skills + '\'' +
                ", department='" + department + '\'' +
                '}';
    }

    private String position;
    private String vertical_id;

    private String stud_id;


    private String department;
    private String program;

    private String list_of_pors;

    private Integer commitment;

    private String skills;

	public Long getApplication_id() {
		return application_id;
	}

	public void setApplication_id(Long application_id) {
		this.application_id = application_id;
	}

	public String getDate_of_application() {
		return date_of_application;
	}

	public void setDate_of_application(String date_of_application) {
		this.date_of_application = date_of_application;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getVertical_id() {
		return vertical_id;
	}

	public void setVertical_id(String vertical_id) {
		this.vertical_id = vertical_id;
	}

	public String getStud_id() {
		return stud_id;
	}

	public void setStud_id(String stud_id) {
		this.stud_id = stud_id;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
	}

	public String getList_of_pors() {
		return list_of_pors;
	}

	public void setList_of_pors(String list_of_pors) {
		this.list_of_pors = list_of_pors;
	}

	public Integer getCommitment() {
		return commitment;
	}

	public void setCommitment(Integer commitment) {
		this.commitment = commitment;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

}
