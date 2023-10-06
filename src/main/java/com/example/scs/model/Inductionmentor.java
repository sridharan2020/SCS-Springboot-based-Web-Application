package com.example.scs.model;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Inductionmentor {
    private Integer mentorID;

    private IMGroups imGroup;
    private String im_grp_id;

    private ScsMembers scsMember;
    private String member_id;

    public Integer getMentorID() {
		return mentorID;
	}

	public void setMentorID(Integer mentorID) {
		this.mentorID = mentorID;
	}

	public IMGroups getImGroup() {
		return imGroup;
	}

	public void setImGroup(IMGroups imGroup) {
		this.imGroup = imGroup;
	}

	public String getIm_grp_id() {
		return im_grp_id;
	}

	public void setIm_grp_id(String im_grp_id) {
		this.im_grp_id = im_grp_id;
	}

	public ScsMembers getScsMember() {
		return scsMember;
	}

	public void setScsMember(ScsMembers scsMember) {
		this.scsMember = scsMember;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public InductionProgram getInductionProgram() {
		return inductionProgram;
	}

	public void setInductionProgram(InductionProgram inductionProgram) {
		this.inductionProgram = inductionProgram;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	private InductionProgram inductionProgram;
    private String year;

    @Override
    public String toString() {
        return "Inductionmentor{" +
                "mentorID=" + mentorID +
                ", im_grp_id='" + im_grp_id + '\'' +
                ", member_id='" + member_id + '\'' +
                ", year='" + year + '\'' +
                '}';
    }
}
