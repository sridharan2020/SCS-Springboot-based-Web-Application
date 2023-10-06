package com.example.scs.repos;

import com.example.scs.model.ScsMembers;
import com.example.scs.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentRepository  {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public List<Student> findAll() {
        String sql = "SELECT * FROM student";

        return jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Student.class));
    }

    public List<Student> getByGrpID(Integer groupID) {
        String sql = "SELECT * FROM student where group_id = ?";

        return jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Student.class), groupID);
    }

    public void create(Student student) {
        if(student.getScsmembership_id() != null)
        {
            if(student.getGroup_id() != null)
            {
                String sql = "INSERT INTO student(student_roll_no, date_of_birth, department,  " +
                        "name, phone_number, program,  year_ofjoin, scsmembership_id, group_id, address) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                jdbcTemplate.update(sql, student.getStudentRollNo(), student.getDateOfBirth(), student.getDepartment(),
                        student.getName(), student.getPhoneNumber(), student.getProgram(),
                        student.getYearOfjoin(), student.getScsmembership_id(), student.getGroup_id(), student.getAddress());
            }
            else
            {
                String sql = "INSERT INTO student(student_roll_no, date_of_birth, department,  " +
                        "name, phone_number, program, year_ofjoin, scsmembership_id, address) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                jdbcTemplate.update(sql, student.getStudentRollNo(), student.getDateOfBirth(), student.getDepartment(),
                        student.getName(), student.getPhoneNumber(), student.getProgram(),
                        student.getYearOfjoin(), student.getScsmembership_id(), student.getAddress());

            }
        }
        else
        {
            if(student.getGroup_id() != null)
            {
                String sql = "INSERT INTO student(student_roll_no, date_of_birth, department, " +
                        "name, phone_number, program, year_ofjoin, group_id, address) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                jdbcTemplate.update(sql, student.getStudentRollNo(), student.getDateOfBirth(), student.getDepartment(),
                        student.getName(), student.getPhoneNumber(), student.getProgram(),
                        student.getYearOfjoin(), student.getGroup_id(), student.getAddress());
            }
            else
            {
                String sql = "INSERT INTO student(student_roll_no, date_of_birth, department, " +
                        "name, phone_number, program, year_ofjoin, address) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                jdbcTemplate.update(sql, student.getStudentRollNo(), student.getDateOfBirth(), student.getDepartment(),
                        student.getName(), student.getPhoneNumber(), student.getProgram(),
                        student.getYearOfjoin(), student.getAddress());

            }
        }
    }

    public Student get(Integer studentRollno){
        try {
            String sql = "SELECT * FROM student WHERE student_roll_no = ?";
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Student.class), new Object[] { studentRollno });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void update(Integer studentRollNo,Student student) {
        String sql = "update student set date_of_birth= ? , address = ?, " +
                "department = ?, name = ?, phone_number = ?, program = ?, " +
                "year_ofjoin = ?, scsmembership_id = ?, group_id = ? where (student_roll_no = ?)";

        if(student.getScsmembership_id()!=null){
            if(student.getGroup_id() != null)
            {
                jdbcTemplate.update(sql, student.getDateOfBirth(), student.getAddress(), student.getDepartment(),
                        student.getName(), student.getPhoneNumber(), student.getProgram(),
                        student.getYearOfjoin(), student.getScsmembership_id(), student.getGroup_id(),studentRollNo);
            }
            else
            {
                jdbcTemplate.update(sql, student.getDateOfBirth(), student.getAddress(), student.getDepartment(),
                        student.getName(), student.getPhoneNumber(), student.getProgram(),
                        student.getYearOfjoin(), student.getScsmembership_id(), null,studentRollNo);
            }

        }
        else{
            if(student.getGroup_id() != null)
            {
                jdbcTemplate.update(sql, student.getDateOfBirth(), student.getAddress(), student.getDepartment(),
                        student.getName(), student.getPhoneNumber(), student.getProgram(),
                        student.getYearOfjoin(), null, student.getGroup_id(),studentRollNo);
            }
            else
            {
                jdbcTemplate.update(sql, student.getDateOfBirth(), student.getAddress(), student.getDepartment(),
                        student.getName(), student.getPhoneNumber(), student.getProgram(),
                        student.getYearOfjoin(), null, null,studentRollNo);
            }
        }

    }

    public void update(Student student) {
        String sql = "update student set date_of_birth= ?, address = ? ," +
                "department = ?, name = ?, phone_number = ?, program = ?, " +
                "year_ofjoin = ?, scsmembership_id = ? where (student_roll_no = ?)";
        if(student.getScsmembership_id()!=null){
            jdbcTemplate.update(sql, student.getDateOfBirth(), student.getAddress(), student.getDepartment(),
                     student.getName(), student.getPhoneNumber(), student.getProgram(),
                    student.getYearOfjoin(), student.getScsmembership_id(),student.getStudentRollNo());
        }else{
            jdbcTemplate.update(sql, student.getDateOfBirth(), student.getAddress(), student.getDepartment(),
                student.getName(), student.getPhoneNumber(), student.getProgram(),
                student.getYearOfjoin(),null ,student.getStudentRollNo());
        }

    }

    public Student getScsMembers(Integer member_id){
        try {
            String sql = "SELECT * FROM student WHERE scsmembership_id = ?";
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Student.class), new Object[] { member_id });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public String getReferencedWarning(Integer studentRollNo) {
                return null;
    }

    public void delete(Integer studentRollNo) {
        String sql = "DELETE FROM student WHERE student_roll_no = ?";
        jdbcTemplate.update(sql, studentRollNo);
    }
}
