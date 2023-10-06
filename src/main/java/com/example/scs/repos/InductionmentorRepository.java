package com.example.scs.repos;

import com.example.scs.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.relational.core.sql.In;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class InductionmentorRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Inductionmentor> findAll() {
        String sql = "SELECT * FROM induction_mentor im, induction_program ip, scsmembers scs, imgroups img " +
                "where (im.year = ip.year) and (im.member_id = scs.member_id) and (im.im_grp_id = img.groupid)";

        return jdbcTemplate.query(sql, new RowMapper<Inductionmentor>() {
            @Override
            public Inductionmentor mapRow(ResultSet rs, int rowNum) throws SQLException {
                InductionProgram inductionProgram = (new BeanPropertyRowMapper<>(InductionProgram.class)).mapRow(rs, rowNum);
                IMGroups imGroup = (new BeanPropertyRowMapper<>(IMGroups.class)).mapRow(rs, rowNum);
                ScsMembers scsMember = (new BeanPropertyRowMapper<>(ScsMembers.class)).mapRow(rs, rowNum);
                Inductionmentor inductionmentor = (new BeanPropertyRowMapper<>(Inductionmentor.class)).mapRow(rs, rowNum);

                assert inductionmentor != null;
                inductionmentor.setInductionProgram(inductionProgram);
                assert inductionProgram != null;
                inductionmentor.setYear(String.valueOf(inductionProgram.getYear()));

                inductionmentor.setScsMember(scsMember);
                assert scsMember != null;
                inductionmentor.setMember_id(String.valueOf(scsMember.getMemberId()));

                inductionmentor.setImGroup(imGroup);
                assert imGroup != null;
                inductionmentor.setIm_grp_id(String.valueOf(imGroup.getGroupID()));

                return inductionmentor;
            }
        }, new Object[] {  });
    }

    public List<Inductionmentor> findAllSimple() {
        String sql = "SELECT * FROM induction_mentor";

        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Inductionmentor.class));
    }

    public List<Inductionmentor> getByGrpId(Integer groupID) {
        String sql = "SELECT * FROM induction_mentor im, induction_program ip, scsmembers scs, imgroups img " +
                "where (im.year = ip.year) and (im.member_id = scs.member_id) and (im.im_grp_id = img.groupid)" +
                " and (im.im_grp_id = ?)";

        return jdbcTemplate.query(sql, new RowMapper<Inductionmentor>() {
            @Override
            public Inductionmentor mapRow(ResultSet rs, int rowNum) throws SQLException {
                InductionProgram inductionProgram = (new BeanPropertyRowMapper<>(InductionProgram.class)).mapRow(rs, rowNum);
                IMGroups imGroup = (new BeanPropertyRowMapper<>(IMGroups.class)).mapRow(rs, rowNum);
                ScsMembers scsMember = (new BeanPropertyRowMapper<>(ScsMembers.class)).mapRow(rs, rowNum);
                Inductionmentor inductionmentor = (new BeanPropertyRowMapper<>(Inductionmentor.class)).mapRow(rs, rowNum);

                assert inductionmentor != null;
                inductionmentor.setInductionProgram(inductionProgram);
                assert inductionProgram != null;
                inductionmentor.setYear(String.valueOf(inductionProgram.getYear()));

                inductionmentor.setScsMember(scsMember);
                assert scsMember != null;
                inductionmentor.setMember_id(String.valueOf(scsMember.getMemberId()));

                inductionmentor.setImGroup(imGroup);
                assert imGroup != null;
                inductionmentor.setIm_grp_id(String.valueOf(imGroup.getGroupID()));

                return inductionmentor;
            }
        }, new Object[] { groupID });
    }

    public void create(Inductionmentor inductionmentor) {

        String sql = "INSERT INTO induction_mentor(mentorid, im_grp_id, year, member_id) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, inductionmentor.getMentorID(), inductionmentor.getIm_grp_id(), inductionmentor.getYear(),
        inductionmentor.getMember_id());
    }

    public Inductionmentor getSimple(Integer mentorID) {
        String sql = "SELECT * FROM induction_mentor WHERE (mentorid = ?)";
        Object[] args = {mentorID};
        return jdbcTemplate.queryForObject(sql,
                BeanPropertyRowMapper.newInstance(Inductionmentor.class), args);
    }

    public Inductionmentor get(Integer mentorID){
        try {
            String sql = "SELECT * FROM induction_mentor im, induction_program ip, scsmembers scs, imgroups img " +
                    "where (im.year = ip.year) and (im.member_id = scs.member_id) and (im.im_grp_id = img.groupid)" +
                    " and (im.mentorid = ?)";

            return jdbcTemplate.queryForObject(sql, new RowMapper<Inductionmentor>() {
                @Override
                public Inductionmentor mapRow(ResultSet rs, int rowNum) throws SQLException {
                    InductionProgram inductionProgram = (new BeanPropertyRowMapper<>(InductionProgram.class)).mapRow(rs, rowNum);
                    IMGroups imGroup = (new BeanPropertyRowMapper<>(IMGroups.class)).mapRow(rs, rowNum);
                    ScsMembers scsMember = (new BeanPropertyRowMapper<>(ScsMembers.class)).mapRow(rs, rowNum);
                    Inductionmentor inductionmentor = (new BeanPropertyRowMapper<>(Inductionmentor.class)).mapRow(rs, rowNum);

                    assert inductionmentor != null;
                    inductionmentor.setInductionProgram(inductionProgram);
                    assert inductionProgram != null;
                    inductionmentor.setYear(String.valueOf(inductionProgram.getYear()));

                    inductionmentor.setScsMember(scsMember);
                    assert scsMember != null;
                    inductionmentor.setMember_id(String.valueOf(scsMember.getMemberId()));

                    inductionmentor.setImGroup(imGroup);
                    assert imGroup != null;
                    inductionmentor.setIm_grp_id(String.valueOf(imGroup.getGroupID()));

                    return inductionmentor;
                }
            }, new Object[] { mentorID });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    public void update(Integer mentorID, Inductionmentor inductionmentor) {
        String sql = "update  induction_mentor set year = ?, member_id = ?, im_grp_id = ? " +
                "where mentorid = ?";
        jdbcTemplate.update(sql, inductionmentor.getYear(), inductionmentor.getMember_id(),
                inductionmentor.getIm_grp_id(), mentorID);
    }

    public String getReferencedWarning(Integer mentorID) {
        return null;
    }

    public void delete(Integer mentorID) {
        String sql = "DELETE FROM induction_mentor WHERE mentorid = ?";
        jdbcTemplate.update(sql, mentorID);
    }

    public List<Inductionmentor> getByGrpID(Integer groupID) {
        try {
            String sql = "SELECT * FROM induction_mentor im, induction_program ip, scsmembers scs, imgroups img " +
                    "where (im.year = ip.year) and (im.member_id = scs.member_id) and (im.im_grp_id = img.groupid)" +
                    " and (im.im_grp_id = ?)";

            return jdbcTemplate.query(sql, new RowMapper<Inductionmentor>() {
                @Override
                public Inductionmentor mapRow(ResultSet rs, int rowNum) throws SQLException {
                    InductionProgram inductionProgram = (new BeanPropertyRowMapper<>(InductionProgram.class)).mapRow(rs, rowNum);
                    IMGroups imGroup = (new BeanPropertyRowMapper<>(IMGroups.class)).mapRow(rs, rowNum);
                    ScsMembers scsMember = (new BeanPropertyRowMapper<>(ScsMembers.class)).mapRow(rs, rowNum);
                    Inductionmentor inductionmentor = (new BeanPropertyRowMapper<>(Inductionmentor.class)).mapRow(rs, rowNum);

                    assert inductionmentor != null;
                    inductionmentor.setInductionProgram(inductionProgram);
                    assert inductionProgram != null;
                    inductionmentor.setYear(String.valueOf(inductionProgram.getYear()));

                    inductionmentor.setScsMember(scsMember);
                    assert scsMember != null;
                    inductionmentor.setMember_id(String.valueOf(scsMember.getMemberId()));

                    inductionmentor.setImGroup(imGroup);
                    assert imGroup != null;
                    inductionmentor.setIm_grp_id(String.valueOf(imGroup.getGroupID()));

                    return inductionmentor;
                }
            }, new Object[] { groupID });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Inductionmentor getByMemberID(Integer memberID) {
        try {
            String sql = "SELECT * FROM induction_mentor im, induction_program ip, scsmembers scs, imgroups img " +
                    "where (im.year = ip.year) and (im.member_id = scs.member_id) and (im.im_grp_id = img.groupid)" +
                    " and (im.member_id = ?)";

            return jdbcTemplate.queryForObject(sql, new RowMapper<Inductionmentor>() {
                @Override
                public Inductionmentor mapRow(ResultSet rs, int rowNum) throws SQLException {
                    InductionProgram inductionProgram = (new BeanPropertyRowMapper<>(InductionProgram.class)).mapRow(rs, rowNum);
                    IMGroups imGroup = (new BeanPropertyRowMapper<>(IMGroups.class)).mapRow(rs, rowNum);
                    ScsMembers scsMember = (new BeanPropertyRowMapper<>(ScsMembers.class)).mapRow(rs, rowNum);
                    Inductionmentor inductionmentor = (new BeanPropertyRowMapper<>(Inductionmentor.class)).mapRow(rs, rowNum);

                    assert inductionmentor != null;
                    inductionmentor.setInductionProgram(inductionProgram);
                    assert inductionProgram != null;
                    inductionmentor.setYear(String.valueOf(inductionProgram.getYear()));

                    inductionmentor.setScsMember(scsMember);
                    assert scsMember != null;
                    inductionmentor.setMember_id(String.valueOf(scsMember.getMemberId()));

                    inductionmentor.setImGroup(imGroup);
                    assert imGroup != null;
                    inductionmentor.setIm_grp_id(String.valueOf(imGroup.getGroupID()));

                    return inductionmentor;
                }
            }, new Object[] { memberID });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Inductionmentor getByMemberIDSimple(Integer memberID) {
        try {
            String sql = "SELECT * FROM induction_mentor im where (im.member_id = ?)";

            return jdbcTemplate.queryForObject(sql,  new BeanPropertyRowMapper<>(Inductionmentor.class), new Object[] { memberID });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Inductionmentor getByMemberIDYearSimple(Integer memberID, String year) {
        try {
            String sql = "SELECT * FROM induction_mentor im where (im.member_id = ?) and (im.year = ?)";

            return jdbcTemplate.queryForObject(sql,  new BeanPropertyRowMapper<>(Inductionmentor.class), new Object[] { memberID, year });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Inductionmentor> getByGrpYear(Integer grpID, String year) {
        try {
            String sql = "SELECT * FROM induction_mentor im where (im.im_grp_id = ?) and (im.year = ?)";

            return jdbcTemplate.query(sql,  new BeanPropertyRowMapper<>(Inductionmentor.class), new Object[] { grpID, year });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
