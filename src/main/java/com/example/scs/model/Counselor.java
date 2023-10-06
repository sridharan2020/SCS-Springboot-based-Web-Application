package com.example.scs.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Set;


@Getter
@Setter
public class Counselor {
    private Integer counselorId;

    private String name;

    private String qualification;

    private String designation;

    private String dateOfJoining;

    @DateTimeFormat(pattern = "hh/mm")
    private String start_time;

    @DateTimeFormat(pattern = "hh/mm")
    private String end_time;

    private String jobType;

    private Long phoneNo;

    private String emailId;

    private String currentStatus;

    public Integer getCounselorId() {
		return counselorId;
	}

	public void setCounselorId(Integer counselorId) {
		this.counselorId = counselorId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(String dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public Long getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(Long phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}

	@Override
    public String toString() {
        return "Counselor{" +
                "counselorId=" + counselorId +
                ", name='" + name + '\'' +
                ", qualification='" + qualification + '\'' +
                ", designation='" + designation + '\'' +
                ", dateOfJoining='" + dateOfJoining + '\'' +
                ", jobType='" + jobType + '\'' +
                ", phoneNo=" + phoneNo +
                ", emailId='" + emailId + '\'' +
                ", currentStatus='" + currentStatus + '\'' +
                '}';
    }

    //    private Set<CounsellingSessions> counseledByCounsellingSessionss;

}
