package com.example.scs;

import com.example.scs.model.*;
import com.example.scs.repos.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
public class EventTests {

    @Autowired
    EventRepository eventRepository;
    @Autowired
    EventParticipationRepository eventParticipationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StudentRepository studentRepository;

//    @Test
//    void createFaculty(){
//        Faculty faculty = new Faculty();
//        faculty.setFacultyId(1);
//        faculty.setName("tourist");
//        facultyRepository.create(faculty);
//    }
    @Test
    void testGetEvent(){
        Event event = eventRepository.get(7);
        System.out.println(event.toString());
        System.out.println(event.getEvent_name());
    }

    @Test
    void testEventParticipation() {
//        EventParticipation eventParticipation  = new EventParticipation();
//        eventParticipation.setEvent_id(4);
//        eventParticipation.setStudent_id(1077);
//
//        eventParticipationRepository.create(eventParticipation);

        User user = userRepository.get(17);

        EventParticipation eventParticipation = eventParticipationRepository.get(4, 101);
        if(eventParticipation != null)
        {
            System.out.println(eventParticipation.toString());
        }
        else
        {
            System.out.println("Event participation is null !!");
        }

    }

    @Test
    void testEventParticipationByStudent() {
        for(EventParticipation eventParticipation : eventParticipationRepository.getByStudent(103))
        {
            System.out.println(eventParticipation.toString());
        }
    }

    @Test
    void testUserForeignKey() {
        for(User user : userRepository.findAll())
        {
            System.out.println(user.toString());
        }
    }

    @Test
    void testUser() {
        User user = userRepository.get(17);

        Student student = studentRepository.get(Integer.parseInt(user.getStud_id()));
//        user.setStudent(student);

        System.out.println(user.toString());
        if(user.getStud_id()!= null)
        {
            System.out.println(user.getStud_id());
            System.out.println(student.toString());
        }
        System.out.println(user.getUser_id());
    }

//    @Test
//    void testUpdateFaculty(){
//        Faculty faculty = new Faculty();
//        faculty.setFacultyId(1);
//        faculty.setName("jiangly");
//        facultyRepository.update(faculty.getFacultyId(),faculty);
    }
//    @Test
//    void testDeleteFaculty(){
//        facultyRepository.delete(1);
//    }

//}
