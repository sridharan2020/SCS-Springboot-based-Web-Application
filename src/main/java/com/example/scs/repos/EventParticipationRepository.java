package com.example.scs.repos;

import com.example.scs.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class EventParticipationRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public List<EventParticipation> findAll() {
        String sql = "SELECT * " +
                "FROM stud_participate_event ep, event e, student s " +
                "where (ep.student_student_roll_no = s.student_roll_no) and (ep.event_eventid = e.eventid)";

        return jdbcTemplate.query(sql, new RowMapper<EventParticipation>() {
            @Override
            public EventParticipation mapRow(ResultSet rs, int rowNum) throws SQLException {
                Event event = (new BeanPropertyRowMapper<>(Event.class)).mapRow(rs, rowNum);
                Student student = (new BeanPropertyRowMapper<>(Student.class)).mapRow(rs, rowNum);
                EventParticipation eventParticipation = (new BeanPropertyRowMapper<>(EventParticipation.class)).mapRow(rs, rowNum);

                assert eventParticipation != null;
                eventParticipation.setEvent(event);
                eventParticipation.setStudent(student);
                return eventParticipation;
            }
        }, new Object[] {  });
    }

    public void create(EventParticipation eventParticipation) {
        String sql = "INSERT INTO stud_participate_event(student_student_roll_no, event_eventid, " +
                "role, feedback) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, eventParticipation.getStudent_id(), eventParticipation.getEvent_id(),
                eventParticipation.getRole(), eventParticipation.getFeedback());
    }

    public List<EventParticipation> getByStudent(Integer stud_id) {
        try {
            String sql = "SELECT * " +
                    "FROM stud_participate_event ep, event e, student s " +
                    "where (ep.student_student_roll_no = s.student_roll_no) and (ep.event_eventid = e.eventid)" +
                    " and (s.student_roll_no = ?)";

            return jdbcTemplate.query(sql, new RowMapper<EventParticipation>() {
                @Override
                public EventParticipation mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Event event = (new BeanPropertyRowMapper<>(Event.class)).mapRow(rs, rowNum);
                    Student student = (new BeanPropertyRowMapper<>(Student.class)).mapRow(rs, rowNum);
                    EventParticipation eventParticipation = (new BeanPropertyRowMapper<>(EventParticipation.class)).mapRow(rs, rowNum);

                    assert eventParticipation != null;
                    eventParticipation.setEvent(event);
                    eventParticipation.setStudent(student);
                    assert event != null;
                    eventParticipation.setEvent_id(String.valueOf(event.getEventID()));
                    assert student != null;
                    eventParticipation.setStudent_id(String.valueOf(student.getStudentRollNo()));
                    return eventParticipation;
                }
            }, new Object[] { stud_id });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<EventParticipation> getByEvent(Integer eventID) {
        try {
            String sql = "SELECT * " +
                    "FROM stud_participate_event ep, event e, student s " +
                    "where (ep.student_student_roll_no = s.student_roll_no) and (ep.event_eventid = e.eventid)" +
                    " and (ep.event_eventid = ?)";

            return jdbcTemplate.query(sql, new RowMapper<EventParticipation>() {
                @Override
                public EventParticipation mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Event event = (new BeanPropertyRowMapper<>(Event.class)).mapRow(rs, rowNum);
                    Student student = (new BeanPropertyRowMapper<>(Student.class)).mapRow(rs, rowNum);
                    EventParticipation eventParticipation = (new BeanPropertyRowMapper<>(EventParticipation.class)).mapRow(rs, rowNum);

                    assert eventParticipation != null;
                    eventParticipation.setEvent(event);
                    eventParticipation.setStudent(student);
                    assert event != null;
                    eventParticipation.setEvent_id(String.valueOf(event.getEventID()));
                    assert student != null;
                    eventParticipation.setStudent_id(String.valueOf(student.getStudentRollNo()));
                    return eventParticipation;
                }
            }, new Object[] { eventID });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public EventParticipation get(Integer eventID, Integer stud_id) {
        try {
        String sql = "SELECT * FROM stud_participate_event WHERE event_eventid = ?" +
                " and (student_student_roll_no = ?)";
        Object[] args = {eventID, stud_id};
        return jdbcTemplate.queryForObject(sql,
                BeanPropertyRowMapper.newInstance(EventParticipation.class), args);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void update(EventParticipation eventParticipation) {
        String sql = "UPDATE stud_participate_event SET role=?, feedback=?, is_notified = ? WHERE (event_eventid=?) " +
                "and (student_student_roll_no = ?)";
        jdbcTemplate.update(sql, eventParticipation.getRole(), eventParticipation.getFeedback(), eventParticipation.getIs_notified(),
                eventParticipation.getEvent_id(), eventParticipation.getStudent_id());
    }

    public String getReferencedWarning(Integer eventID) {
        return null;
    }

    public void delete(Integer eventID, Integer stud_id) {
        String sql = "DELETE FROM stud_participate_event WHERE (event_eventid = ?) and (student_student_roll_no = ?)";
        Object[] args = {eventID, stud_id};
        jdbcTemplate.update(sql, args);
    }
}
