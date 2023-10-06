package com.example.scs.repos;

import com.example.scs.model.CounsellingSessions;
import com.example.scs.model.Student;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

//@Repository
//public class CounsellingSessionsRepository {
//}

import com.example.scs.model.Counselor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CounsellingSessionsRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public List<CounsellingSessions> findAll() {
        String sql = "SELECT * " +
                "FROM counselling_sessions cs, counselor c, student s " +
                "where (cs.counsels_to_id = s.student_roll_no) and (cs.counseled_by_id = c.counselor_id)";

        return jdbcTemplate.query(sql, new RowMapper<CounsellingSessions>() {
            @Override
            public CounsellingSessions mapRow(ResultSet rs, int rowNum) throws SQLException {
                Counselor counselor = (new BeanPropertyRowMapper<>(Counselor.class)).mapRow(rs, rowNum);
                Student student = (new BeanPropertyRowMapper<>(Student.class)).mapRow(rs, rowNum);
                CounsellingSessions counsellingSessions = (new BeanPropertyRowMapper<>(CounsellingSessions.class)).mapRow(rs, rowNum);

                assert counsellingSessions != null;
                counsellingSessions.setCounseledBy(counselor);
                counsellingSessions.setCounselsTo(student);
                assert counselor != null;
                counsellingSessions.setCounseledByString(counselor.getName());
                assert student != null;
                counsellingSessions.setCounselsToString(student.getName());
                return counsellingSessions;
            }
        }, new Object[] {  });
    }

    public List<CounsellingSessions> findAllByStudent(Integer studentID) {
        String sql = "SELECT * " +
                "FROM counselling_sessions cs, student s, counselor c " +
                "where (cs.counsels_to_id = s.student_roll_no) and (cs.counseled_by_id = c.counselor_id) " +
                "and (s.student_roll_no = ?)";

        return jdbcTemplate.query(sql, new RowMapper<CounsellingSessions>() {
            @Override
            public CounsellingSessions mapRow(ResultSet rs, int rowNum) throws SQLException {
                Counselor counselor = (new BeanPropertyRowMapper<>(Counselor.class)).mapRow(rs, rowNum);
                Student student = (new BeanPropertyRowMapper<>(Student.class)).mapRow(rs, rowNum);
                CounsellingSessions counsellingSessions = (new BeanPropertyRowMapper<>(CounsellingSessions.class)).mapRow(rs, rowNum);

                assert counsellingSessions != null;
                counsellingSessions.setCounseledBy(counselor);
                counsellingSessions.setCounselsTo(student);
                assert counselor != null;
                counsellingSessions.setCounseledByString(counselor.getName());
                assert student != null;
                counsellingSessions.setCounselsToString(student.getName());
                return counsellingSessions;
            }
        }, new Object[] { studentID });
    }

    public String findStudentBySession(Integer sessionID) {
        String sql = "SELECT * " +
                "FROM counselling_sessions cs, student s " +
                "where (cs.sessionid = ?) and (cs.counsels_to_id = s.student_roll_no)";

        CounsellingSessions cs = jdbcTemplate.queryForObject(sql, new RowMapper<CounsellingSessions>() {
            @Override
            public CounsellingSessions mapRow(ResultSet rs, int rowNum) throws SQLException {
                Student student = (new BeanPropertyRowMapper<>(Student.class)).mapRow(rs, rowNum);
                CounsellingSessions counsellingSessions = (new BeanPropertyRowMapper<>(CounsellingSessions.class)).mapRow(rs, rowNum);

                assert counsellingSessions != null;
                counsellingSessions.setCounselsTo(student);
                assert student != null;
                counsellingSessions.setCounselsToString(student.getName());
                return counsellingSessions;
            }
        }, new Object[] { sessionID });

        assert cs != null;
        return cs.getCounselsToString();
    }

    public void create(CounsellingSessions counsellingSessions) {

        String sql = "INSERT INTO counselling_sessions(sessionid, date, feedback,from_time, " +
                "to_time,counseled_by_id,counsels_to_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, counsellingSessions.getSessionID(), counsellingSessions.getDate(), counsellingSessions.getFeedback(),
                counsellingSessions.getFromTime(), counsellingSessions.getToTime(),
                counsellingSessions.getCounseledBy().getCounselorId(), counsellingSessions.getCounselsTo().getStudentRollNo()
                );
    }

    public CounsellingSessions get(Integer counsellingSessionsId){
        try {
            String sql = "SELECT * FROM counselling_sessions " +
                    "JOIN counselor ON counselling_sessions.counseled_by_id = counselor.counselor_id " +
                    "JOIN student ON counselling_sessions.counsels_to_id = student.student_roll_no " +
                    "WHERE sessionid = ?";
            return jdbcTemplate.queryForObject(sql, new RowMapper<CounsellingSessions>() {
                @Override
                public CounsellingSessions mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Counselor counselor = (new BeanPropertyRowMapper<>(Counselor.class)).mapRow(rs, rowNum);
                    Student student = (new BeanPropertyRowMapper<>(Student.class)).mapRow(rs, rowNum);
                    CounsellingSessions counsellingSessions = (new BeanPropertyRowMapper<>(CounsellingSessions.class)).mapRow(rs, rowNum);

                    assert counsellingSessions != null;
                    counsellingSessions.setCounseledBy(counselor);
                    counsellingSessions.setCounselsTo(student);
                    return counsellingSessions;
                }
            }, new Object[] { counsellingSessionsId });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public CounsellingSessions getWithCounselorStudent(Integer counselorID, Integer studentID, String now){
        try {
            String sql = "SELECT * FROM counselling_sessions WHERE (counseled_by_id = ?) and (counsels_to_id = ?) and (date = ?)";
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(CounsellingSessions.class), new Object[] { counselorID, studentID, now });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<CounsellingSessions> getWithCounselor(Integer counselorID, String now){
        try {
            String sql = "SELECT * FROM counselling_sessions WHERE (counseled_by_id = ?) and (date = ?)";
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CounsellingSessions.class), new Object[] { counselorID, now });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void update(Integer sessionid, CounsellingSessions counsellingSessions) {
        String sql = "update  counselling_sessions set date=?, feedback=?,from_time=?, " +
                "to_time=?,counseled_by_id=?,counsels_to_id=? where (sessionid=?)";
    jdbcTemplate.update(sql, counsellingSessions.getDate(), counsellingSessions.getFeedback(),
                counsellingSessions.getFromTime(), counsellingSessions.getToTime(),
            counsellingSessions.getCounseledBy().getCounselorId(), counsellingSessions.getCounselsTo().getStudentRollNo(),
            counsellingSessions.getSessionID(),sessionid);
    }

    public String getReferencedWarning(Integer counselor_id) {
        return null;
    }

    public void delete(Integer sessionId) {
        String sql = "DELETE FROM counselling_sessions WHERE sessionid= ?";
        jdbcTemplate.update(sql, sessionId);
    }
}
