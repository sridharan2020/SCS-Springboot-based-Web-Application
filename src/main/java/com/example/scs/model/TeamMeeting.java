package com.example.scs.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class TeamMeeting {
    private Integer meeting_id;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String date ;

    private String meeting_title;

    private String meeting_description;

    @Override
    public String toString() {
        return "TeamMeeting{" +
                "meeting_id=" + meeting_id +
                ", date='" + date + '\'' +
                ", meeting_title='" + meeting_title + '\'' +
                ", meeting_description='" + meeting_description + '\'' +
                ", location='" + location + '\'' +
                ", from_time='" + from_time + '\'' +
                ", to_time='" + to_time + '\'' +
                ", organiser_id='" + organiser_id + '\'' +
                '}';
    }

    private String location;

    @DateTimeFormat(pattern = "hh/mm")
    private String from_time;

    @DateTimeFormat(pattern = "hh/mm")
    private String to_time;

    private String organiser_id;

}
