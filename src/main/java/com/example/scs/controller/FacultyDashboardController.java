package com.example.scs.controller;
import com.example.scs.model.Faculty;
import com.example.scs.model.Student;
import com.example.scs.model.User;
import com.example.scs.repos.FacultyRepository;
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
public class FacultyDashboardController {
    @Autowired
    private SecurityService securityService;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/faculty_dashboard")
    public String dashboard_post(@RequestParam Map<String,String> body){
        User user = securityService.findLoggedInUser();
        Faculty faculty = facultyRepository.get(Integer.parseInt(user.getFac_id()));
        faculty.setEmail(userRepository.getFacultyEmail(faculty.getFacultyId()));
        String regex = "[0-9]+";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(body.get("attr_value"));
        if(body.get("attr_change").equals("dept")){
            faculty.setDepartment(body.get("attr_value"));
        }
        if(body.get("attr_change").equals("email_id")){
            faculty.setEmail(body.get("attr_value"));
        }
        if(body.get("attr_change").equals("name")){
            faculty.setName(body.get("attr_value"));
            user.setFullName(body.get("attr_value"));
        }
        if(body.get("attr_change").equals("phone_no")){
            if(m.matches())faculty.setPhoneNo(Long.parseLong(body.get("attr_value")));
        }
        if(body.get("attr_change").equals("qualification")){
            faculty.setQualification(body.get("attr_value"));
        }
//        System.out.println(faculty.toString());
        facultyRepository.update(faculty.getFacultyId(),faculty);
        userRepository.update2(user.getUser_id().intValue(),user);
        return "success";
    }
}
