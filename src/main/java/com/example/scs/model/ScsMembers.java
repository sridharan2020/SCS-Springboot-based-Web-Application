package com.example.scs.model;

import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
public class ScsMembers {

    private Integer memberId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String fromDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String toDate;

    public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	
	

	public String getMemberIdString() {
		return getMemberIdString();
	}

	public void setMemberIdString(String MemberIdString) {
		this.memberId = Integer.parseInt(MemberIdString);
	}

	
	
	
	
	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(String currentPosition) {
		this.currentPosition = currentPosition;
	}

	public String getVerticalmemberbelongs() {
		return verticalmemberbelongs;
	}

	public void setVerticalmemberbelongs(String verticalmemberbelongs) {
		this.verticalmemberbelongs = verticalmemberbelongs;
	}

	public Integer getMembersresponsblebills() {
		return membersresponsblebills;
	}

	public void setMembersresponsblebills(Integer membersresponsblebills) {
		this.membersresponsblebills = membersresponsblebills;
	}

	public List<Integer> getMembersattendmeets() {
		return membersattendmeets;
	}

	public void setMembersattendmeets(List<Integer> membersattendmeets) {
		this.membersattendmeets = membersattendmeets;
	}

	public Integer getMemberbecomesIM() {
		return memberbecomesIM;
	}

	public void setMemberbecomesIM(Integer memberbecomesIM) {
		this.memberbecomesIM = memberbecomesIM;
	}

	@Override
	public String toString() {
		return "ScsMembers [memberId=" + memberId + ", fromDate=" + fromDate + ", toDate=" + toDate
				+ ", currentPosition=" + currentPosition + ", verticalmemberbelongs=" + verticalmemberbelongs
				+ ", membersresponsblebills=" + membersresponsblebills + ", membersattendmeets=" + membersattendmeets
				+ ", memberbecomesIM=" + memberbecomesIM + ", getMemberId()=" + getMemberId() + ", getFromDate()="
				+ getFromDate() + ", getToDate()=" + getToDate() + ", getCurrentPosition()=" + getCurrentPosition()
				+ ", getVerticalmemberbelongs()=" + getVerticalmemberbelongs() + ", getMembersresponsblebills()="
				+ getMembersresponsblebills() + ", getMembersattendmeets()=" + getMembersattendmeets()
				+ ", getMemberbecomesIM()=" + getMemberbecomesIM() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}

    @Size(max = 255)
    private String currentPosition;

    private String verticalmemberbelongs;

    private Integer membersresponsblebills;

    private List<Integer> membersattendmeets;

    private Integer memberbecomesIM;

}
