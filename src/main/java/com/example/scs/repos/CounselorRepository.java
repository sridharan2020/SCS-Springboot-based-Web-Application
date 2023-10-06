package com.example.scs.repos;

import com.example.scs.model.Counselor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CounselorRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public List<Counselor> findAll() {
        String sql = "SELECT * FROM counselor";

        return jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Counselor.class));
    }

    public void create(Counselor counselor) {

        String sql = "INSERT INTO counselor(counselor_id, current_status, date_of_joining,designation, " +
                "email_id,job_type,name,phone_no,qualification, start_time, end_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?)";
        jdbcTemplate.update(sql, counselor.getCounselorId(), counselor.getCurrentStatus(), counselor.getDateOfJoining(),
                counselor.getDesignation(),
                counselor.getEmailId(), counselor.getJobType(), counselor.getName(), counselor.getPhoneNo(),
                counselor.getQualification(), counselor.getStart_time(), counselor.getEnd_time());
    }


    public Counselor get(Integer counselor_id){
        try {
            String sql = "SELECT * FROM counselor WHERE counselor_id = ?";
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Counselor.class), new Object[] { counselor_id });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void update(Integer counselor_id, Counselor counselor) {
        String sql = "update counselor set  current_status=?, date_of_joining=?,designation=?, " +
                "email_id=?,job_type=?,name=?,phone_no=?,qualification=?,start_time=?, end_time=? where (counselor_id = ?)";

        jdbcTemplate.update(sql, counselor.getCurrentStatus(), counselor.getDateOfJoining(),
                counselor.getDesignation(), counselor.getEmailId(), counselor.getJobType(), counselor.getName(),
                counselor.getPhoneNo(), counselor.getQualification(),counselor.getStart_time(), counselor.getEnd_time(),counselor_id);
    }

    public String getReferencedWarning(Integer counselor_id) {
                return null;
    }

    public void delete(Integer counselor_id) {
        String sql = "DELETE FROM counselor WHERE counselor_id= ?";
        jdbcTemplate.update(sql, counselor_id);
    }
}
