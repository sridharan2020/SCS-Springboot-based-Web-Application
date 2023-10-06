package com.example.scs;

import com.example.scs.model.InitiativeParticipation;
import com.example.scs.model.Inititatives;
import com.example.scs.repos.InitiativeParticipationRepository;
import com.example.scs.repos.InititativesRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
//public class InititativesTests {
//
//}
//package com.example.scs;


//import com.example.scs.model.CounsellingSessions;
//import com.example.scs.repos.CounsellingSessionsRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class InititativesTests {

    @Autowired
    InititativesRepository inititativesRepository;
    @Autowired
    InitiativeParticipationRepository initiativeParticipationRepository;

    @Test
    void createInititatives(){
        Inititatives inititatives = new Inititatives();
        inititatives.setInitiativesId(1);
        inititatives.setTitle("kichpiv");
        inititativesRepository.create(inititatives);
    }
    @Test
    void testGetinititatives(){
        System.out.println(initiativeParticipationRepository.getByStudent(101));
    }
    @Test
    void testUpdateinititatives(){
        Inititatives inititatives = new Inititatives();
        inititatives.setInitiativesId(1);
        inititatives.setTitle("jiangly");
        inititativesRepository.update(inititatives.getInitiativesId(),inititatives);
    }
    @Test
    void testDeleteinititatives(){
//        studentRepository.delete(2);
        inititativesRepository.delete(1);
    }

    @Test
    void testInitiativeParticipation() {
        for(InitiativeParticipation initiativeParticipation : initiativeParticipationRepository.getByStudent(101))
        {
            System.out.println(initiativeParticipation.toString());
        }
    }

}
