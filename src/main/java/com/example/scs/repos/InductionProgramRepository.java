package com.example.scs.repos;

import com.example.scs.model.Event;
import com.example.scs.model.InductionProgram;
import com.example.scs.model.Inductionmentor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InductionProgramRepository  {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<InductionProgram> findAll() {
        String sql = "SELECT * FROM induction_program";

        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(InductionProgram.class));
    }

    public void create(InductionProgram inductionProgram) {
        String sql = "INSERT INTO induction_program(year, chairman, duration, no_of_attendees)" +
                " VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, inductionProgram.getYear(), inductionProgram.getChairman(), inductionProgram.getDuration(),
                inductionProgram.getNoOfAttendees());
    }


    public InductionProgram get(Integer year) {
        String sql = "SELECT * FROM induction_program WHERE year = ?";
        Object[] args = {year};
        return jdbcTemplate.queryForObject(sql,
                BeanPropertyRowMapper.newInstance(InductionProgram.class), args);
    }

    public void update(Integer year, InductionProgram inductionProgram) {
        String sql = "UPDATE induction_program SET chairman = ?, duration = ?, no_of_attendees = ? WHERE (year=?)";
        jdbcTemplate.update(sql, inductionProgram.getChairman(), inductionProgram.getDuration(),
                inductionProgram.getNoOfAttendees(), year);
    }

    public String getReferencedWarning(Integer year) {
        return null;
    }

    public void delete(Integer year) {
        String sql = "DELETE FROM induction_program WHERE (year = ?)";
        jdbcTemplate.update(sql, year);
    }
}
