package com.example.scs;

import com.example.scs.model.ScsMembers;
import com.example.scs.model.Student;
import com.example.scs.repos.ScsMembersRepository;
import com.example.scs.repos.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ScsApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    StudentRepository studentRepo;
    @Autowired
    ScsMembersRepository scsMembersRepository;

    @Test
    void testFindALl()
    {
        for(ScsMembers scsMember : scsMembersRepository.findAll())
        {
            System.out.println(scsMember.toString());
        }
    }

    @Test
    void testAddStudent()
    {
        Student stud = new Student();
        stud.setName("Poorna");
        stud.setStudentRollNo(2);

        studentRepo.create(stud);
    }


}
