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
public class IMGroupsRepository  {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<IMGroups> findAll() {
        String sql = "SELECT * FROM imgroups img, induction_program ip, faculty f " +
                "where (img.supervised_by = f.faculty_id) and (img.induction_year = ip.year)";

        return jdbcTemplate.query(sql, new RowMapper<IMGroups>() {
            @Override
            public IMGroups mapRow(ResultSet rs, int rowNum) throws SQLException {
                InductionProgram inductionProgram = (new BeanPropertyRowMapper<>(InductionProgram.class)).mapRow(rs, rowNum);
                Faculty faculty = (new BeanPropertyRowMapper<>(Faculty.class)).mapRow(rs, rowNum);
                IMGroups imGroup = (new BeanPropertyRowMapper<>(IMGroups.class)).mapRow(rs, rowNum);

                assert imGroup != null;
                imGroup.setFaculty(faculty);
                assert faculty != null;
                imGroup.setSupervised_by(String.valueOf(faculty.getFacultyId()));
                imGroup.setInductionProgram(inductionProgram);
                assert inductionProgram != null;
                imGroup.setInduction_year(String.valueOf(inductionProgram.getYear()));
                return imGroup;
            }
        }, new Object[] {  });
    }

    public List<IMGroups> findAllYear(String year) {
        String sql = "SELECT * FROM imgroups img, induction_program ip, faculty f " +
                "where (img.supervised_by = f.faculty_id) and (img.induction_year = ip.year) and(img.induction_year = ?)";

        return jdbcTemplate.query(sql, new RowMapper<IMGroups>() {
            @Override
            public IMGroups mapRow(ResultSet rs, int rowNum) throws SQLException {
                InductionProgram inductionProgram = (new BeanPropertyRowMapper<>(InductionProgram.class)).mapRow(rs, rowNum);
                Faculty faculty = (new BeanPropertyRowMapper<>(Faculty.class)).mapRow(rs, rowNum);
                IMGroups imGroup = (new BeanPropertyRowMapper<>(IMGroups.class)).mapRow(rs, rowNum);

                assert imGroup != null;
                imGroup.setFaculty(faculty);
                assert faculty != null;
                imGroup.setSupervised_by(String.valueOf(faculty.getFacultyId()));
                imGroup.setInductionProgram(inductionProgram);
                assert inductionProgram != null;
                imGroup.setInduction_year(String.valueOf(inductionProgram.getYear()));
                return imGroup;
            }
        }, new Object[] { year });
    }

    public void create(IMGroups imGroup) {

        String sql = "INSERT INTO imgroups(groupid, induction_year, supervised_by) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, imGroup.getGroupID(), imGroup.getInduction_year(), imGroup.getSupervised_by());
    }

    public IMGroups get(Integer groupID){
        try {
            String sql = "SELECT * FROM imgroups img, induction_program ip, faculty f " +
                    "where (img.supervised_by = f.faculty_id) and (img.induction_year = ip.year)" +
                    " and (groupid = ?)";

            return jdbcTemplate.queryForObject(sql, new RowMapper<IMGroups>() {
                @Override
                public IMGroups mapRow(ResultSet rs, int rowNum) throws SQLException {
                    InductionProgram inductionProgram = (new BeanPropertyRowMapper<>(InductionProgram.class)).mapRow(rs, rowNum);
                    Faculty faculty = (new BeanPropertyRowMapper<>(Faculty.class)).mapRow(rs, rowNum);
                    IMGroups imGroup = (new BeanPropertyRowMapper<>(IMGroups.class)).mapRow(rs, rowNum);

                    assert imGroup != null;
                    imGroup.setFaculty(faculty);
                    assert faculty != null;
                    imGroup.setSupervised_by(String.valueOf(faculty.getFacultyId()));
                    imGroup.setInductionProgram(inductionProgram);
                    assert inductionProgram != null;
                    imGroup.setInduction_year(String.valueOf(inductionProgram.getYear()));
                    return imGroup;
                }
            }, new Object[] { groupID });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    public void update(Integer groupID, IMGroups imGroup) {
        String sql = "update  imgroups set supervised_by = ?, induction_year = ? " +
                "where groupid = ?";
        jdbcTemplate.update(sql, imGroup.getSupervised_by(), imGroup.getInduction_year(), groupID);
    }

    public String getReferencedWarning(Integer groupID) {
        return null;
    }

    public void delete(Integer groupID) {
        String sql = "DELETE FROM imgroups WHERE groupid= ?";
        jdbcTemplate.update(sql, groupID);
    }
}
