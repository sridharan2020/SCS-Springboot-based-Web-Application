package com.example.scs.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class MonthlyReports {
    private Integer report_id;

    private String title;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
    private String compiled_on;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
    private String from_date;

    private String report_description;

    public Integer getReport_id() {
		return report_id;
	}

	public void setReport_id(Integer report_id) {
		this.report_id = report_id;
	}

	public String getCompiled_on() {
		return compiled_on;
	}

	public void setCompiled_on(String compiled_on) {
		this.compiled_on = compiled_on;
	}

	public String getFrom_date() {
		return from_date;
	}

	public void setFrom_date(String from_date) {
		this.from_date = from_date;
	}

	public String getReport_description() {
		return report_description;
	}

	public void setReport_description(String report_description) {
		this.report_description = report_description;
	}

	public String getTo_date() {
		return to_date;
	}

	public void setTo_date(String to_date) {
		this.to_date = to_date;
	}

	public String getStudent_compiles_reports_id() {
		return student_compiles_reports_id;
	}

	public void setStudent_compiles_reports_id(String student_compiles_reports_id) {
		this.student_compiles_reports_id = student_compiles_reports_id;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	private boolean status;


    @Override
    public String toString() {
        return "MonthlyReports{" +
                "report_id=" + report_id +
                ", compiled_on=" + compiled_on +
                ", from_date='" + from_date + '\'' +
                ", report_description='" + report_description + '\'' +
                ", status=" + status +
                ", to_date='" + to_date + '\'' +
                ", student_compiles_reports_id='" + student_compiles_reports_id + '\'' +
                '}';
    }

    private String to_date;

    private String student_compiles_reports_id;

    public Boolean getStatus(){
        return this.status;
    }

    public void setStatus(Boolean status){
        this.status = status;
    }

}
