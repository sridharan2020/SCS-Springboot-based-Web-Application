package com.example.scs.repos;

import com.example.scs.model.Application;
import com.example.scs.model.Bill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class ApplicationRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<Application> findAll() {
        String sql = "SELECT * FROM application";

        return jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Application.class));
    }

    public void create(Application application) {
        String sql = "INSERT INTO application(application_id,position, date_of_application, status, vertical_id, stud_id, program, department, list_of_pors, commitment, skills) VALUES (?, ?, ?, ?, ?, ? ,?, ?, ?, ?,?)";
        jdbcTemplate.update(sql,application.getApplication_id(),application.getPosition(),application.getDate_of_application(),application.getStatus(),application.getVertical_id(),application.getStud_id(),application.getProgram(),application.getDepartment(),application.getList_of_pors(),application.getCommitment(),application.getSkills() );
    }


    public Application get(Integer applicationID) {
        String sql = "SELECT * FROM application WHERE application_id = ?";
        Object[] args = {applicationID};
        return jdbcTemplate.queryForObject(sql, args,
                BeanPropertyRowMapper.newInstance(Application.class));
    }

    public void update(Integer applicationID, Application application) {
        String sql = "UPDATE application SET  date_of_application=?,position=?, status=?, vertical_id=?, program=?, department=?, list_of_pors=?, commitment=?, skills=? WHERE (application_id=?)";
        jdbcTemplate.update(sql,application.getDate_of_application(),application.getPosition(),application.getStatus(),application.getVertical_id(),application.getProgram(),application.getDepartment(),application.getList_of_pors(),application.getCommitment(),application.getSkills(), applicationID);
    }
    public Application getLatest(){
        try {
            String sql = "SELECT * FROM application order by application_id desc limit 1";
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Application.class), new Object[] {  });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    public String getReferencedWarning(Integer applicationID) {
        return null;
    }

    public void delete(Integer applicationID) {
        String sql = "DELETE FROM application WHERE (application_id = ?)";
        jdbcTemplate.update(sql, applicationID);
    }
}
