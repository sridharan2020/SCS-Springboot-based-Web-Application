package com.example.scs.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class Bill {
    private int billid;

    private String title;

    @Override
    public String toString() {
        return "Bill{" +
                "billid=" + billid +
                "title=" + title +
                ", date_of_billing=" + date_of_billing +
                ", purpose='" + purpose + '\'' +
                ", status=" + status +
                ", member_id='" + member_id + '\'' +
                ", amount=" + amount +
                '}';
    }

	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private String date_of_billing;

    private String purpose;

    private Boolean status;

    public int getBillid() {
		return billid;
	}

	public void setBillid(int billid) {
		this.billid = billid;
	}

	public String getDate_of_billing() {
		return date_of_billing;
	}

	public void setDate_of_billing(String date_of_billing) {
		this.date_of_billing = date_of_billing;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	private String member_id;

    private int amount;

}
