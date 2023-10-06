package com.example.scs;

import com.example.scs.model.Counselor;
import com.example.scs.model.Inductionmentor;
import com.example.scs.model.Student;
import com.example.scs.repos.CounselorRepository;
import com.example.scs.repos.InductionmentorRepository;
import com.example.scs.repos.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomTests {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    CounselorRepository counselorRepository;
    @Autowired
    InductionmentorRepository inductionmentorRepository;
    @Test
    void testGetStudent(){
        System.out.println(studentRepository.getByGrpID(3));
    }

    @Test
    void testGetByMemberIDSimple() {
        for(Inductionmentor inductionmentor : inductionmentorRepository.findAllSimple())
        {
            System.out.println(inductionmentor.toString());
        }
    }

    @Test
    void testGetSimple() {
        System.out.println(inductionmentorRepository.getByGrpId(3));
    }

    @Test
    void testUpdateStudent(){
        Student student = new Student();
        student.setStudentRollNo(1);
        student.setName("Poorna");
        studentRepository.update(student.getStudentRollNo(),student);
    }
    @Test
    void testDeleteStudent(){
        studentRepository.delete(2);
    }
    //counselor

    @Test
    void testGetCounselor(){
        counselorRepository.get(1);
    }
    @Test
    void testUpdateCounselor(){
        Counselor counselor = new Counselor();
        counselor.setCounselorId(1);
        counselor.setName("tourist");
        counselorRepository.update(counselor.getCounselorId(),counselor);
    }
    @Test
    void testDeleteCounselor(){
//        studentRepository.delete(2);
        counselorRepository.delete(1);
    }
    @Test
    void createCounselor(){
        Counselor counselor = new Counselor();
        counselor.setCounselorId(2);
        counselor.setName("neal wu");
        counselorRepository.create(counselor);
    }
}
