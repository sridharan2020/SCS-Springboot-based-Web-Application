package com.example.scs.model;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Venue {

    private Integer venueID;
    private String venueName;
    private Long capacity;
    private String contactperson;
    private Long contactnumber;
    public Integer getVenueID() {
		return venueID;
	}
	public void setVenueID(Integer venueID) {
		this.venueID = venueID;
	}
	public String getVenueName() {
		return venueName;
	}
	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}
	public Long getCapacity() {
		return capacity;
	}
	public void setCapacity(Long capacity) {
		this.capacity = capacity;
	}
	public String getContactperson() {
		return contactperson;
	}
	public void setContactperson(String contactperson) {
		this.contactperson = contactperson;
	}
	public Long getContactnumber() {
		return contactnumber;
	}
	public void setContactnumber(Long contactnumber) {
		this.contactnumber = contactnumber;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	private String location;
}
