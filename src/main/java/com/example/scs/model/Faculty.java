package com.example.scs.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Faculty {


    public Integer getFacultyId() {
		return facultyId;
	}

	public void setFacultyId(Integer facultyId) {
		this.facultyId = facultyId;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Long getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(Long phoneNo) {
		this.phoneNo = phoneNo;
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

	private Integer facultyId;

    private String department;

    private Long phoneNo;

    @Override
    public String toString() {
        return "Faculty{" +
                "facultyId=" + facultyId +
                ", department='" + department + '\'' +
                ", phoneNo=" + phoneNo +
                ", name='" + name + '\'' +
                ", qualification='" + qualification + '\'' +
                '}';
    }

    private String name;
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	private String qualification;
//    private Set<EventsPermission> permissiongrantedbyEventsPermissions;
//
//    private Set<EventsPermission> permissionattestedbyEventsPermissions;
//
//    private Set<InitiativesPermission> initpermissgrantedInitiativesPermissions;
//
//    private Set<InitiativesPermission> initpermissattestedbyInitiativesPermissions;
//
//
//    private Set<Vertical> facultycounselsverticalsVerticals;
//
//    private IMGroups iMgrpssupervisedbyFac;

}
