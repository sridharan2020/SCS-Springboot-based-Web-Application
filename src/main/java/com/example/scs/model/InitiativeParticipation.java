package com.example.scs.model;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class InitiativeParticipation {
    private Student student;
    private Integer student_id;

    private Inititatives inititative;
    private Integer initiative_id;

    private String role;
    private String feedback;

	private Integer is_notified;

	public Integer getIs_notified() {
		return is_notified;
	}

	public void setIs_notified(Integer is_notified) {
		this.is_notified = is_notified;
	}

	@Override
	public String toString() {
		return "InitiativeParticipation{" +
				"student=" + student.getName() +
				", student_id=" + student_id +
				", inititative=" + inititative.getTitle() +
				", initiative_id=" + initiative_id +
				", role='" + role + '\'' +
				", feedback='" + feedback + '\'' +
				", is_notified=" + is_notified +
				'}';
	}

	public Integer getStudent_id() {
		return student_id;
	}
	public void setStudent_id(Integer student_id) {
		this.student_id = student_id;
	}
	public Integer getInitiative_id() {
		return initiative_id;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public Inititatives getInititative() {
		return inititative;
	}
	public void setInititative(Inititatives inititative) {
		this.inititative = inititative;
	}
	public void setInitiative_id(Integer initiative_id) {
		this.initiative_id = initiative_id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
}
