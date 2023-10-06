package com.example.scs.model;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EventParticipation {
    private String student_id;
    private String event_id;

    private Student student;
    private Event event;

	public Integer getIs_notified() {
		return is_notified;
	}

	public void setIs_notified(Integer is_notified) {
		this.is_notified = is_notified;
	}

	private Integer is_notified;

    public String getStudent_id() {
		return student_id;
	}

	public void setStudent_id(String student_id) {
		this.student_id = student_id;
	}

	public String getEvent_id() {
		return event_id;
	}

	public void setEvent_id(String event_id) {
		this.event_id = event_id;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
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

	private String role;
    private String feedback;

    @Override
    public String toString() {
        return "EventParticipation{" +
                "student_id='" + student_id + '\'' +
                ", event_id='" + event_id + '\'' +
                ", student=" + student.getStudentRollNo() +
                ", event=" + event.getEventID() +
                ", role='" + role + '\'' +
                ", feedback='" + feedback + '\'' +
                '}';
    }
}


