package com.example.scs;


import com.example.scs.model.CounsellingSessions;
import com.example.scs.repos.CounsellingSessionsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CounsellingSessionsTests {

    @Autowired
    CounsellingSessionsRepository counsellingSessionsRepository;

    @Test
    void createcounsellingSessions(){
        CounsellingSessions counsellingSessions = new CounsellingSessions();
        counsellingSessions.setSessionID(2);
        counsellingSessions.setFeedback("kichpiv");
        counsellingSessionsRepository.create(counsellingSessions);
    }
    @Test
    void testGetcounsellingSessions(){
        System.out.println(counsellingSessionsRepository.get(4)) ;
    }
    @Test
    void testUpdatecounsellingSessions(){
        CounsellingSessions counsellingSessions = new CounsellingSessions();
        counsellingSessions.setSessionID(1);
        counsellingSessions.setFeedback("abcd");
        counsellingSessionsRepository.update(counsellingSessions.getSessionID(),counsellingSessions);
    }

    @Test
    void testFindAllSimple()
    {
        List<CounsellingSessions> sessions = counsellingSessionsRepository.findAllByStudent(4);
        for(CounsellingSessions counsellingSessions1 : sessions)
        {
            counsellingSessions1.setCounselsToString(counsellingSessionsRepository.findStudentBySession(counsellingSessions1.getSessionID()));
            System.out.println(counsellingSessions1.toString());
        }
    }

    @Test
    void testDeletecounsellingSessions(){
//        studentRepository.delete(2);
        counsellingSessionsRepository.delete(1);
    }

}
