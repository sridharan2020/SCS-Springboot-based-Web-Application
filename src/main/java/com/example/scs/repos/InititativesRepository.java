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
public class InititativesRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public List<Inititatives> findAll() {
        String sql = "SELECT * FROM inititatives";

        return jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Inititatives.class));
    }

    public void create(Inititatives inititatives) {

        String sql = "INSERT INTO inititatives(initiatives_id, minutes_of_meeting, no_of_attendees,resources, " +
                "start_date,timings,title) VALUES (?, ?, ?, ?, ?, ?,  ?)";
        jdbcTemplate.update(sql, inititatives.getInitiativesId(), inititatives.getMinutesOfMeeting(), inititatives.getNoOfAttendees(),
                inititatives.getResources(),
                inititatives.getStartDate(), inititatives.getTimings(), inititatives.getTitle()
                );
    }


    public Inititatives get(Integer inititatives_id){
        try {
            String sql = "SELECT * FROM inititatives WHERE initiatives_id = ?";
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Inititatives.class), new Object[] { inititatives_id });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Inititatives getLatest(){
        try {
            String sql = "SELECT * FROM inititatives order by initiatives_id desc limit 1";
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Inititatives.class), new Object[] {  });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void update(Integer inititatives_id, Inititatives inititatives) {
        String sql = "update inititatives set   minutes_of_meeting=?,  no_of_attendees=?,resources=?, " +
                "start_date=?,timings=?,title=? where (initiatives_id = ?)";
            jdbcTemplate.update(sql,inititatives.getMinutesOfMeeting(), inititatives.getNoOfAttendees(),
                    inititatives.getResources(),
                    inititatives.getStartDate(), inititatives.getTimings(), inititatives.getTitle()
                    , inititatives_id
            );
    }

    public String getReferencedWarning(Integer inititatives_id) {
        return null;
    }

    public void delete(Integer inititatives_id) {
        String sql = "DELETE FROM inititatives WHERE initiatives_id= ?";
        jdbcTemplate.update(sql, inititatives_id);
    }
}
