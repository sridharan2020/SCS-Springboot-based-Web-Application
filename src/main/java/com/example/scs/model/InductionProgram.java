package com.example.scs.model;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class InductionProgram {
    private Integer year;
    private String duration;
    public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getChairman() {
		return chairman;
	}
	public void setChairman(String chairman) {
		this.chairman = chairman;
	}
	public Integer getNoOfAttendees() {
		return noOfAttendees;
	}
	public void setNoOfAttendees(Integer noOfAttendees) {
		this.noOfAttendees = noOfAttendees;
	}
	private String chairman;
    private Integer noOfAttendees;
}
