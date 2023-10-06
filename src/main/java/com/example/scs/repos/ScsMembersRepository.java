package com.example.scs.repos;

import com.example.scs.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ScsMembersRepository  {
    @Autowired
    private JdbcTemplate jdbcTemplate;
//    public List<ScsMembers> findAll() {
//        String sql = "SELECT * FROM scsmembers";
//
//        return jdbcTemplate.query(sql,
//                BeanPropertyRowMapper.newInstance(ScsMembers.class));
//    }

    public List<ScsMembers> findAll() {
        String sql = "SELECT * FROM scsmembers scs, vertical v " +
                "where (scs.verticalmemberbelongs_id = v.vertical_id)";

        return jdbcTemplate.query(sql, new RowMapper<ScsMembers>() {
            @Override
            public ScsMembers mapRow(ResultSet rs, int rowNum) throws SQLException {
                ScsMembers scsMembers = (new BeanPropertyRowMapper<>(ScsMembers.class).mapRow(rs, rowNum));
                Vertical vertical = (new BeanPropertyRowMapper<>(Vertical.class).mapRow(rs, rowNum));

                assert scsMembers != null;
                assert vertical != null;
                scsMembers.setVerticalmemberbelongs(vertical.getVertical_id().toString());

                return scsMembers;
            }
        }, new Object[] {  });
    }

    public void create(ScsMembers scs_members) {
        String sql = "INSERT INTO scsmembers(member_id,current_position,from_date,to_date, " +
                "verticalmemberbelongs_id) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, scs_members.getMemberId(), scs_members.getCurrentPosition(), scs_members.getFromDate(),
                scs_members.getToDate(), scs_members.getVerticalmemberbelongs());
    }

    public ScsMembers get(Integer memberid){
        try {
            String sql = "SELECT * FROM scsmembers scs, vertical v " +
                    "where (scs.verticalmemberbelongs_id = v.vertical_id) and (scs.member_id = ?)";

            return jdbcTemplate.queryForObject(sql, new RowMapper<ScsMembers>() {
                @Override
                public ScsMembers mapRow(ResultSet rs, int rowNum) throws SQLException {
                    ScsMembers scsMembers = (new BeanPropertyRowMapper<>(ScsMembers.class).mapRow(rs, rowNum));
                    Vertical vertical = (new BeanPropertyRowMapper<>(Vertical.class).mapRow(rs, rowNum));

                    assert scsMembers != null;
                    assert vertical != null;
                    scsMembers.setVerticalmemberbelongs(vertical.getVertical_id().toString());

                    return scsMembers;
                }
            }, new Object[] { memberid });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<ScsMembers> getByVertical(Integer verticalID) {
        String sql = "SELECT * FROM scsmembers where verticalmemberbelongs_id = ?";

        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(ScsMembers.class), verticalID);
    }


    public ScsMembers getLatest(){
        try {
            String sql = "SELECT * FROM scsmembers order by member_id desc limit 1";
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(ScsMembers.class), new Object[] {  });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public User getScsUserId(Integer member_id){
        try {
            String sql = "SELECT * FROM user WHERE stud_id IN ( SELECT student_roll_no FROM student WHERE scsmembership_id = ?)";
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), new Object[] { member_id });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void update(Integer memberid, ScsMembers scs_members) {
        String sql = "update scsmembers set " +
                " current_position= ?, from_date = ?, to_date = ?, " +
                " verticalmemberbelongs_id = ? where (member_id = ?)";

//        jdbcTemplate.update(sql, scs_members.getDateOfBirth(), scs_members.getDepartment(),
//                scs_members.getEMailId(), scs_members.getName(), scs_members.getPhoneNumber(), scs_members.getProgram(),
//                scs_members.getYearOfGradn(), scs_members.getYearOfjoin(),studentRollNo);
         jdbcTemplate.update(sql, scs_members.getCurrentPosition(), scs_members.getFromDate(),
                scs_members.getToDate(), scs_members.getVerticalmemberbelongs()
                ,memberid);
    }

    public String getReferencedWarning(Integer memberid) {
        return null;
    }

    public void delete(Integer memberid) {
        String sql = "DELETE FROM scsmembers WHERE (member_id = ?)";
        jdbcTemplate.update(sql, memberid);
    }

	@Override
	public String toString() {
		return "ScsMembersRepository [jdbcTemplate=" + jdbcTemplate + ", findAll()=" + findAll() + ", getLatest()="
				+ getLatest() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
    
}
