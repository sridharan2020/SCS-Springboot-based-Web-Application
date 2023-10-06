package com.example.scs.model;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.ModelAttribute;

@Getter
@Setter
public class Student {

    private Integer studentRollNo;

    private String name;

    private Integer yearOfjoin;

    private String department;

    private String program;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
    private String dateOfBirth;

	private Long phoneNumber;

	private String address;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	private String email;

    public Integer getStudentRollNo() {
		return studentRollNo;
	}
	public void setStudentRollNo(Integer studentRollNo) {
		this.studentRollNo = studentRollNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getYearOfjoin() {
		return yearOfjoin;
	}
	public void setYearOfjoin(Integer yearOfjoin) {
		this.yearOfjoin = yearOfjoin;
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
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public Long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Integer getScsmembership_id() {
		return scsmembership_id;
	}
	public void setScsmembership_id(Integer scsmembership_id) {
		this.scsmembership_id = scsmembership_id;
	}
	public Integer getGroup_id() {
		return group_id;
	}
	public void setGroup_id(Integer group_id) {
		this.group_id = group_id;
	}
//    private ScsMembers scsMember;
    private Integer scsmembership_id;
    private Integer group_id;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}


	@Override
    public String toString() {
        return "Student{" +
                "studentRollNo=" + studentRollNo +
                ", name='" + name + '\'' +
                ", yearOfjoin=" + yearOfjoin +
                ", department='" + department + '\'' +
                ", program='" + program + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", phoneNumber=" + phoneNumber +
                ", scsmembershipid=" + scsmembership_id +
                ", group_id=" + group_id +
                '}';
    }

    //    private Set<CounsellingSessions> counselsToCounsellingSessionss;
//
//    @OneToOne
//    @JoinColumn(name = "student_passed_out_id")
//    private AlumniAdvisors studentPassedOut;
//
//    @OneToOne
//    @JoinColumn(name = "scsmembership_id")
//    private SCSMembers sCSMembership;
//
//    @OneToMany(mappedBy = "studntcompilesreports")
//    private Set<MonthlyReports> studntcompilesreportsMonthlyReportss;
//
//    @OneToMany(mappedBy = "studentCompilesrepos")
//    private Set<Repositories> studentCompilesreposRepositoriess;
  //    
//    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinTable(
//            name = "studparticipateevent",
//            joinColumns = @JoinColumn(name = "student_student_roll_no"),
//            inverseJoinColumns = @JoinColumn(name = "event_eventid")
//    )
//    private Set<Event> studparticipateeventEvents;
//
//    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinTable(
//            name = "studentawarded",
//            joinColumns = @JoinColumn(name = "student_student_roll_no"),
//            inverseJoinColumns = @JoinColumn(name = "award_awardid")
//    )
//    private Set<Award> studentawardedAwards;
//
//    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinTable(
//            name = "studinitiativepart",
//            joinColumns = @JoinColumn(name = "student_student_roll_no"),
//            inverseJoinColumns = @JoinColumn(name = "inititatives_initiatives_id")
//    )
//    private Set<Inititatives> studinitiativepartInititativess;
//
//    @OneToMany(mappedBy = "studorderbooking")
//    private Set<BookBookings> studorderbookingBookBookingss;
//
//    @OneToMany(mappedBy = "studapplies")
//    private Set<Application> studappliesApplications;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "studspartofimgp_id")
//    private IMGroups studspartofIMgp;

}
