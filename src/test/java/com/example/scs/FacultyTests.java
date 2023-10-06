package com.example.scs;


import com.example.scs.model.Faculty;
import com.example.scs.repos.FacultyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
public class FacultyTests {

    @Autowired

    FacultyRepository facultyRepository;
    @Test
    void createFaculty(){
        Faculty faculty = new Faculty();
        faculty.setFacultyId(1);
        faculty.setName("tourist");
        facultyRepository.create(faculty);
    }
    @Test
    void testGetFaculty(){
        facultyRepository.get(1);
    }
    @Test
    void testUpdateFaculty(){
        Faculty faculty = new Faculty();
        faculty.setFacultyId(1);
        faculty.setName("jiangly");
        facultyRepository.update(faculty.getFacultyId(),faculty);
    }
    @Test
    void testDeleteFaculty(){
        facultyRepository.delete(1);
    }

}
