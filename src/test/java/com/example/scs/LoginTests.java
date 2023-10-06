package com.example.scs;

import com.example.scs.model.Faculty;
import com.example.scs.model.Student;
import com.example.scs.model.User;
import com.example.scs.repos.FacultyRepository;
import com.example.scs.repos.StudentRepository;
import com.example.scs.repos.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Objects;

@SpringBootTest
public class LoginTests {
    @Autowired
    UserRepository userRepository;
    @Autowired
    StudentRepository studentRepo;
    @Autowired
    FacultyRepository facultyRepo;

//    @Autowired
//    RoleRepository roleRepository;

    @Test
    void testUsers() {
        for(User user : userRepository.findAll())
        {
            System.out.println(user);
            System.out.println(user.toString());
        }
    }

    @Test
    void testCreateUser() {
        User user = new User();
        user.setEmail("user8@gmail");
        user.setFullName("user8");
        user.setStud_id("20075075");
        user.setRole("FACULTY");

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode("user3");
        user.setPassword(encodedPassword);

        String temp_role = user.getRole();

        if(Objects.equals(temp_role, "STUDENT") | Objects.equals(temp_role, "SCS_MEMBER"))
        {
            Student check = studentRepo.get(Integer.parseInt(user.getStud_id()));
            if(check == null)
            {
                Student stud = new Student();
                stud.setStudentRollNo(Integer.parseInt(user.getStud_id()));
                stud.setName(user.getFullName());
                stud.setEmail(user.getEmail());

                studentRepo.create(stud);

                user.setStud_id(String.valueOf(stud.getStudentRollNo()));
                userRepository.save(user);
            }
            else
            {
                System.out.println("The user with the roll already exists!!");
            }
        }
        else if (Objects.equals(temp_role, "FACULTY"))
        {
            Faculty fac = new Faculty();
            fac.setName(user.getFullName());
//            fac.setEmailId(user.getEmail());

            facultyRepo.create(fac);
//            fac = facultyRepo.getByEmail(fac.getEmailId());

            user.setFac_id(String.valueOf(fac.getFacultyId()));
            userRepository.save(user);
        }
    }
}
