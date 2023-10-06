package com.example.scs.repos;

import com.example.scs.model.Bill;
import com.example.scs.model.Event;
import com.example.scs.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EventRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public List<Event> findAll() {
        String sql = "SELECT * FROM event";

        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Event.class));
    }

    public void create(Event event) {
        String sql = "INSERT INTO event(eventid, event_name, description, date, from_time, " +
                "minutesof_meeting, no_of_attendees, resources, to_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, event.getEventID(), event.getEvent_name(), event.getDescription(), event.getDate(), event.getFromTime(),
                event.getMinutesofMeeting(), event.getNoOfAttendees(), event.getResources(), event.getToTime());
    }


    public Event get(Integer eventID) {
        String sql = "SELECT * FROM event WHERE eventid = ?";
        Object[] args = {eventID};
        return jdbcTemplate.queryForObject(sql, args,BeanPropertyRowMapper.newInstance(Event.class));
    }

    public Event getLatest(){
        try {
            String sql = "SELECT * FROM event order by eventid desc limit 1";
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Event.class), new Object[] {  });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void update(Integer eventID, Event event) {
        String sql = "UPDATE event SET event_name = ?, description = ?, date=?, minutesof_meeting=?, from_time=?, " +
                "no_of_attendees=?, resources=?, to_time=? WHERE (eventid=?)";
        jdbcTemplate.update(sql, event.getEvent_name(), event.getDescription(), event.getDate(), event.getMinutesofMeeting(),
                event.getFromTime(), event.getNoOfAttendees(), event.getResources(), event.getToTime(), eventID);
    }

    public String getReferencedWarning(Integer eventID) {
        return null;
    }

    public void delete(Integer eventID) {
        String sql = "DELETE FROM event WHERE (eventid = ?)";
        jdbcTemplate.update(sql, eventID);
    }
}
