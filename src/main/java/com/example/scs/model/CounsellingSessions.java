package com.example.scs.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;


@Getter
@Setter
public class CounsellingSessions {
    private Integer sessionID;
    private String feedback;
    @DateTimeFormat(pattern = "hh/mm")
    private String toTime;
    @DateTimeFormat(pattern = "hh/mm")
    private String fromTime;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String date;
    private Counselor counseledBy;
    private Student counselsTo;

    public Integer getSessionID() {
		return sessionID;
	}

	public void setSessionID(Integer sessionID) {
		this.sessionID = sessionID;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public String getToTime() {
		return toTime;
	}

	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

	public String getFromTime() {
		return fromTime;
	}

	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Counselor getCounseledBy() {
		return counseledBy;
	}

	public void setCounseledBy(Counselor counseledBy) {
		this.counseledBy = counseledBy;
	}

	public Student getCounselsTo() {
		return counselsTo;
	}

	public void setCounselsTo(Student counselsTo) {
		this.counselsTo = counselsTo;
	}

	public String getCounseledByString() {
		return counseledByString;
	}

	public void setCounseledByString(String counseledByString) {
		this.counseledByString = counseledByString;
	}

	public String getCounselsToString() {
		return counselsToString;
	}

	public void setCounselsToString(String counselsToString) {
		this.counselsToString = counselsToString;
	}

	private String counseledByString;
    private String counselsToString;

    @Override
    public String toString() {
        return "CounsellingSessions{" +
                "sessionID=" + sessionID +
                ", feedback='" + feedback + '\'' +
                ", toTime=" + toTime +
                ", fromTime=" + fromTime +
                ", date=" + date +
                ", counseledBy=" + counseledByString +
                ", counselsTo=" + counselsToString +
                '}';
    }
}
