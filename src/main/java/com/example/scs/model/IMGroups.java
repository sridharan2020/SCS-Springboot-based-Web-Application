package com.example.scs.model;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class IMGroups {

    private Integer groupID;

    private InductionProgram inductionProgram;
    private String induction_year;

    private Faculty faculty;
    private String supervised_by;
	public Integer getGroupID() {
		return groupID;
	}
	public void setGroupID(Integer groupID) {
		this.groupID = groupID;
	}
	public InductionProgram getInductionProgram() {
		return inductionProgram;
	}
	public void setInductionProgram(InductionProgram inductionProgram) {
		this.inductionProgram = inductionProgram;
	}
	public String getInduction_year() {
		return induction_year;
	}
	public void setInduction_year(String induction_year) {
		this.induction_year = induction_year;
	}
	public Faculty getFaculty() {
		return faculty;
	}
	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}
	public String getSupervised_by() {
		return supervised_by;
	}
	public void setSupervised_by(String supervised_by) {
		this.supervised_by = supervised_by;
	}


}
