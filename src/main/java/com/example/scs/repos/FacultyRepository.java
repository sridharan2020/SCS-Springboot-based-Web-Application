package com.example.scs.repos;


import com.example.scs.model.Faculty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FacultyRepository  {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public List<Faculty> findAll() {
        String sql = "SELECT * FROM faculty";

        return jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Faculty.class));
    }

    public void create(Faculty faculty) {

        String sql = "INSERT INTO faculty(department, " +
                "name, phone_no) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, faculty.getDepartment(),
                faculty.getName(),faculty.getPhoneNo());
    }

    public Faculty get(Integer faculty_id){
        try {
            String sql = "SELECT * FROM faculty WHERE faculty_id = ?";
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Faculty.class), faculty_id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

//    public Faculty getByEmail(String email){
//        try {
//            String sql = "SELECT * FROM faculty WHERE email_id = ?";
//            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Faculty.class), email);
//        } catch (EmptyResultDataAccessException e) {
//            return null;
//        }
//    }

    public Faculty getLatest(){
        try {
            String sql = "SELECT * FROM faculty order by faculty_id desc limit 1";
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Faculty.class), new Object[] {  });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void update(Integer faculty_id, Faculty faculty) {
        String sql = "update faculty set faculty_id= ? ," +
                "department = ?, name = ?, phone_no = ?, qualification = ? where (faculty_id = ?)";

        jdbcTemplate.update(sql, faculty.getFacultyId(), faculty.getDepartment(),
                faculty.getName(), faculty.getPhoneNo(),faculty.getQualification(),faculty_id);
    }

    public String getReferencedWarning(Integer faculty_id) {
        return null;
    }

    public void delete(Integer faculty_id) {
        String sql = "DELETE FROM faculty WHERE faculty_id = ?";
        jdbcTemplate.update(sql, faculty_id);
    }
}
