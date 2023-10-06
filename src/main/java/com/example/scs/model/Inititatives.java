package com.example.scs.model;


import java.time.LocalDate;
import java.time.LocalTime;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;


@Getter
@Setter
public class Inititatives {

    private Integer initiativesId;

    @Size(max = 255)
    private String title;
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String startDate;

    @DateTimeFormat(pattern = "HH:mm:ss")
    private String timings;

    private Long noOfAttendees;

    @Size(max = 255)
    private String resources;

    @Size(max = 255)
    private String minutesOfMeeting;

	public Integer getInitiativesId() {
		return initiativesId;
	}

	public void setInitiativesId(Integer initiativesId) {
		this.initiativesId = initiativesId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getTimings() {
		return timings;
	}

	public void setTimings(String timings) {
		this.timings = timings;
	}

	public Long getNoOfAttendees() {
		return noOfAttendees;
	}

	public void setNoOfAttendees(Long noOfAttendees) {
		this.noOfAttendees = noOfAttendees;
	}

	public String getResources() {
		return resources;
	}

	public void setResources(String resources) {
		this.resources = resources;
	}

	public String getMinutesOfMeeting() {
		return minutesOfMeeting;
	}

	public void setMinutesOfMeeting(String minutesOfMeeting) {
		this.minutesOfMeeting = minutesOfMeeting;
	}

	@Override
	public String toString() {
		return "Inititatives [initiativesId=" + initiativesId + ", title=" + title + ", description=" + description
				+ ", startDate=" + startDate + ", timings=" + timings + ", noOfAttendees=" + noOfAttendees
				+ ", resources=" + resources + ", minutesOfMeeting=" + minutesOfMeeting + "]";
	}
	
	

//    private Integer permissionforinitiative;

//    private Integer initiatvehostedat;

}
