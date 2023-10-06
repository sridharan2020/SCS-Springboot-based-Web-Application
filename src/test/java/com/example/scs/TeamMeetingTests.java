package com.example.scs;

import com.example.scs.model.Bill;
import com.example.scs.model.TeamMeeting;
import com.example.scs.repos.BillRepository;
import com.example.scs.repos.TeamMeetingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class TeamMeetingTests {

    @Autowired
    TeamMeetingRepository teamMeetingRepository;

    @Test
    void createTeamMeeting(){
        TeamMeeting teamMeeting = new TeamMeeting();
//        teamMeeting.setTeamMeetingid(1);
//        teamMeeting.setPurpose("tourist");
        teamMeeting.setMeeting_id(1);
        teamMeeting.setLocation("tourist");
        teamMeetingRepository.create(teamMeeting);
    }
    @Test
    void testGetTeamMeeting(){
        teamMeetingRepository.get(1);
    }
    @Test
    void testUpdateTeamMeeting(){
        TeamMeeting teamMeeting = new TeamMeeting();
//        teamMeeting.setTeamMeetingid(1);
//        teamMeeting.setPurpose("jiangly");
        teamMeeting.setMeeting_id(1);
        teamMeeting.setLocation("jiangly");
        teamMeetingRepository.update(teamMeeting.getMeeting_id(),teamMeeting);
    }
    @Test
    void testDeleteTeamMeeting(){
        teamMeetingRepository.delete(1);
    }

//    @Test
//    void testGetLatest(){
//        System.out.println(teamMeetingRepository.getLatest());
//    }
}
