package com.example.scs.controller;
import com.example.scs.model.Student;
import com.example.scs.model.User;
import com.example.scs.repos.StudentRepository;
import com.example.scs.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.scs.services.SecurityService;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


//@Controller

@RestController
//@RequestMapping("/dashboard")
public class StudentDashboardController {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/student_dashboard")
    public String dashboard_post(@RequestParam Map<String,String> body){
        User user = securityService.findLoggedInUser();
        Student student = studentRepository.get(Integer.parseInt(user.getStud_id()));
        student.setEmail(userRepository.getStudentEmail(student.getStudentRollNo()));
        String regex = "[0-9]+";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(body.get("attr_value"));
        if(body.get("attr_change").equals("dept")){
            student.setDepartment(body.get("attr_value"));
        }
        if(body.get("attr_change").equals("email_id")){
            student.setEmail(body.get("attr_value"));
        }
        if(body.get("attr_change").equals("name")){
            student.setName(body.get("attr_value"));
            user.setFullName(body.get("attr_value"));
        }
        if(body.get("attr_change").equals("phone")){
            if(m.matches())student.setPhoneNumber(Long.parseLong(body.get("attr_value")));
        }
        if(body.get("attr_change").equals("program")){
            student.setProgram(body.get("attr_value"));
        }
//        if(body.get("attr_change").equals("year_of_graduation")){
//            if(m.matches())student.setYearOfGradn(Integer.parseInt(body.get("attr_value")));
//        }
        if(body.get("attr_change").equals("year_of_join")){
            if(m.matches())student.setYearOfjoin(Integer.parseInt(body.get("attr_value")));
        }
        studentRepository.update(student);
        userRepository.update2(user.getUser_id().intValue(),user);
        return "success";
    }
}