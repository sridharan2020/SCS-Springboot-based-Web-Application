package com.example.scs.model;
import lombok.AccessLevel;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@NoArgsConstructor
@Getter
@Setter
public class Event {

    private Integer eventID;
    private String event_name;
    private String description;
    private String resources;

    private String minutesofMeeting;

    private Integer noOfAttendees;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String date;

    @DateTimeFormat(pattern = "hh/mm")
    private String fromTime;

    @DateTimeFormat(pattern = "hh/mm")
    private String toTime;

    private Integer venue_id;

    @Override
    public String toString() {
        return "Event{" +
                "eventID=" + eventID +
                ", event_name='" + event_name + '\'' +
                ", resources='" + resources + '\'' +
                ", minutesofMeeting='" + minutesofMeeting + '\'' +
                ", noOfAttendees=" + noOfAttendees +
                ", date=" + date +
                ", fromTime=" + fromTime +
                ", toTime=" + toTime +
                '}';
    }

    //    public String getMinutesofMeeting() {
//        return minutesofMeeting;
//    }
//
//    public void setMinutesofMeeting(String minutesofMeeting) {
//        this.minutesofMeeting = minutesofMeeting;
    


//
//    private EventsPermission seekpermission;
//
//    private Set<Honorarium> honoredForHonorariums;
//
//    private Set<Student> studparticipateeventStudents;

//
    public Integer getEventID() {
    	return this.eventID;
    }
    
    public void setEventID(Integer eventID) {
    	this.eventID = eventID;
    }
    
    public String getEvent_name() {
    	return this.event_name;
    }
    
    public void setEvent_name(String event_name) {
    	this.event_name= event_name;
    }
    public String getDescription() {
    	return this.description;
    }
    
    public void setDescription(String description) {
    	this.description =description;
    	
    	
    } public String  getResources() {
    	return this.resources;
    }
    
    public void setResources(String resources) {
    	this.resources = resources;
    } 
    
    
    public String getMinutesofMeeting() {
    	return this.minutesofMeeting;
    }
    
    public void setEventID(String minutesofMeeting) {
    	this.minutesofMeeting = minutesofMeeting;
    } 
    
    
    public Integer getNoOfAttendees() {
    	return this.noOfAttendees;
    }
    
    public void setNoOfAttendees(Integer noOfAttendees) {
    	this.noOfAttendees = noOfAttendees;
    }
    
    public String  getDate() {
    	return this.date;
    }
    
    public void setDate(String date) {
    	this.date = date;
    } 
    
    
    public String  getFromTime() {
    	return this.fromTime;
    }
    
    public void setFromTime(String fromTime) {
    	this.fromTime = fromTime;
    } 
    public String  getToTime() {
    	return this.toTime;
    }
    
    public void setToTime(String toTime) {
    	this.toTime =toTime;
    } 
    
    
    
    
    
}