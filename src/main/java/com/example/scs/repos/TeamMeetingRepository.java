package com.example.scs.repos;

import com.example.scs.model.Bill;
import com.example.scs.model.TeamMeeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class TeamMeetingRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<TeamMeeting> findAll() {
        String sql = "SELECT * FROM team_meetings";

        return jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(TeamMeeting.class));
    }

    public void create(TeamMeeting teamMeeting) {
        String sql = "INSERT INTO team_meetings(meeting_id, date, from_time, meeting_title, meeting_description, location, to_time, organiser_id) VALUES (?, ?, ?, ?, ?, ? ,?, ?)";
        jdbcTemplate.update(sql, teamMeeting.getMeeting_id(),teamMeeting.getDate(),teamMeeting.getFrom_time(),teamMeeting.getMeeting_title(),teamMeeting.getMeeting_description(),teamMeeting.getLocation()
        ,teamMeeting.getTo_time(),teamMeeting.getOrganiser_id()
        );
    }


    public TeamMeeting get(Integer meetingID) {
        String sql = "SELECT * FROM team_meetings WHERE meeting_id = ?";
        Object[] args = {meetingID};
        return jdbcTemplate.queryForObject(sql, args,
                BeanPropertyRowMapper.newInstance(TeamMeeting.class));
    }

    public void update(Integer meetingID, TeamMeeting meeting) {
        String sql = "UPDATE team_meetings SET  date=?, from_time=?, meeting_title=?, meeting_description=?, location=?, to_time=? WHERE (meeting_id=?)";
        jdbcTemplate.update(sql,meeting.getDate(),meeting.getFrom_time(),meeting.getMeeting_title(),meeting.getMeeting_description(),meeting.getLocation(),meeting.getTo_time(), meetingID);
    }
//    public TeamMeeting getLatest(){
//        try {
//            String sql = "SELECT * FROM team_meetings order by meeting_id desc limit 1";
//            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(TeamMeeting.class), new Object[] {  });
//        } catch (EmptyResultDataAccessException e) {
//            return null;
//        }
//    }
    public TeamMeeting getLatest(){
        try {
            String sql = "SELECT * FROM team_meetings order by meeting_id desc limit 1";
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(TeamMeeting.class), new Object[] {  });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    public String getReferencedWarning(Integer meetingID) {
        return null;
    }

    public void delete(Integer meetingID) {
        String sql = "DELETE FROM team_meetings WHERE (meeting_id = ?)";
        jdbcTemplate.update(sql, meetingID);
    }
}
