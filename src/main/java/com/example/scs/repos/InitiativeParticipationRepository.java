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
public class InitiativeParticipationRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public List<InitiativeParticipation> findAll() {
        String sql = "SELECT * FROM stud_participate_initiative";

        return jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(InitiativeParticipation.class));
    }

    public void create(InitiativeParticipation initiativeParticipation) {
        String sql = "INSERT INTO stud_participate_initiative(student_student_roll_no, " +
                "inititatives_initiatives_id, role, feedback) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, initiativeParticipation.getStudent_id(), initiativeParticipation.getInitiative_id(),
                initiativeParticipation.getRole(), initiativeParticipation.getFeedback());
    }


    public InitiativeParticipation get(Integer initiative_id, Integer stud_id) {
        try {
        String sql = "SELECT * FROM stud_participate_initiative WHERE (inititatives_initiatives_id = ?)" +
                " and (student_student_roll_no = ?)";
        Object[] args = {initiative_id, stud_id};
        return jdbcTemplate.queryForObject(sql, args,
                BeanPropertyRowMapper.newInstance(InitiativeParticipation.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<InitiativeParticipation> getByStudent(Integer stud_id) {
        try {
            String sql = "SELECT * " +
                    "FROM stud_participate_initiative ip, inititatives i, student s " +
                    "where (ip.student_student_roll_no = s.student_roll_no) and (ip.inititatives_initiatives_id = i.initiatives_id)" +
                    " and (s.student_roll_no = ?)";

            return jdbcTemplate.query(sql, new RowMapper<InitiativeParticipation>() {
                @Override
                public InitiativeParticipation mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Inititatives inititative = (new BeanPropertyRowMapper<>(Inititatives.class)).mapRow(rs, rowNum);
                    Student student = (new BeanPropertyRowMapper<>(Student.class)).mapRow(rs, rowNum);
                    InitiativeParticipation initiativeParticipation = (new BeanPropertyRowMapper<>(InitiativeParticipation.class)).mapRow(rs, rowNum);

                    assert initiativeParticipation != null;
                    initiativeParticipation.setInititative(inititative);
                    initiativeParticipation.setStudent(student);
                    assert inititative != null;
                    initiativeParticipation.setInitiative_id(inititative.getInitiativesId());
                    assert student != null;
                    initiativeParticipation.setStudent_id(student.getStudentRollNo());
                    return initiativeParticipation;
                }
            }, new Object[] { stud_id });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<InitiativeParticipation> getByInitiative(Integer initiativeID) {
        try {
            String sql = "SELECT * " +
                    "FROM stud_participate_initiative ip, inititatives i, student s " +
                    "where (ip.student_student_roll_no = s.student_roll_no) and (ip.inititatives_initiatives_id = i.initiatives_id)" +
                    " and (i.initiatives_id = ?)";

            return jdbcTemplate.query(sql, new RowMapper<InitiativeParticipation>() {
                @Override
                public InitiativeParticipation mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Inititatives inititative = (new BeanPropertyRowMapper<>(Inititatives.class)).mapRow(rs, rowNum);
                    Student student = (new BeanPropertyRowMapper<>(Student.class)).mapRow(rs, rowNum);
                    InitiativeParticipation initiativeParticipation = (new BeanPropertyRowMapper<>(InitiativeParticipation.class)).mapRow(rs, rowNum);

                    assert initiativeParticipation != null;
                    initiativeParticipation.setInititative(inititative);
                    initiativeParticipation.setStudent(student);
                    assert inititative != null;
                    initiativeParticipation.setInitiative_id(inititative.getInitiativesId());
                    assert student != null;
                    initiativeParticipation.setStudent_id(student.getStudentRollNo());
                    return initiativeParticipation;
                }
            }, new Object[] { initiativeID });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void update(InitiativeParticipation initiativeParticipation) {
        String sql = "UPDATE stud_participate_initiative SET role=?, feedback=?, is_notified = ? WHERE " +
                "(inititatives_initiatives_id=?) and (student_student_roll_no = ?)";
        jdbcTemplate.update(sql, initiativeParticipation.getRole(), initiativeParticipation.getFeedback(), initiativeParticipation.getIs_notified(),
                initiativeParticipation.getInitiative_id(), initiativeParticipation.getStudent_id());
    }

    public String getReferencedWarning(Integer eventID) {
        return null;
    }

    public void delete(Integer initiative_id, Integer stud_id) {
        String sql = "DELETE FROM stud_participate_initiative WHERE (inititatives_initiatives_id = ?) and (student_student_roll_no = ?)";
        Object[] args = {initiative_id, stud_id};
        jdbcTemplate.update(sql, args);
    }
}
