package com.example.scs;

import com.example.scs.model.*;
import com.example.scs.repos.*;
import com.example.scs.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.model.IModel;

import javax.validation.Valid;
import javax.xml.ws.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Controller
public class HomeController {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private StudentRepository studentRepo;
    @Autowired
    private FacultyRepository facultyRepo;
    @Autowired
    private ScsMembersRepository scsMembersRepo;
    @Autowired
    private SecurityService securityService;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UnreadRepository unreadRepository;
    @Autowired
    InitiativeParticipationRepository initiativeParticipationRepository;
    @Autowired
    EventParticipationRepository eventParticipationRepository;

    @GetMapping("/")
    public String index(Model model) {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(final Model model) {
    	boolean log = Boolean.FALSE;
    	 User user = securityService.findLoggedInUser();
        if(user!=null) {log=Boolean.TRUE;}
        model.addAttribute("log", log);
        boolean admin = Boolean.FALSE;
        boolean member = Boolean.FALSE;
        boolean student = Boolean.FALSE;
        boolean oadmin = Boolean.FALSE;
    	if(user!=null) {if((Objects.equals(user.getRole(), "ADMIN"))||(Objects.equals(user.getRole(), "SCS_MEMBER"))) {
    		admin = Boolean.TRUE;
    	}
    	if(Objects.equals(user.getRole(), "SCS_MEMBER")) {
    		member = Boolean.TRUE;
    	}
    	if(Objects.equals(user.getRole(), "STUDENT")) {
    		student = Boolean.TRUE;
    	}
    	if(Objects.equals(user.getRole(), "ADMIN")) {
    		oadmin = Boolean.TRUE;
    	}
    	
    	}
    	 model.addAttribute("admin", admin);
    	 model.addAttribute("member", member);
    	 model.addAttribute("student", student);
    	 model.addAttribute("oadmin", oadmin);
         
        return "login/home";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        List<String> options = new ArrayList<String>();
//        options.add(0, "SCS_MEMBER");
        options.add(0, "STUDENT");
        options.add(1, "FACULTY");
        model.addAttribute("options", options);
        return "login/signup";
    }


    @PostMapping("/process_register")
    public String processRegister(@RequestParam String name, User user, Model model) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        String temp_role = user.getRole();

        if(Objects.equals(temp_role, "STUDENT"))
        {
            System.out.println((int) Long.parseLong(user.getStud_id()));
            Student check = studentRepo.get((int) Long.parseLong(user.getStud_id()));

            if(check == null)
            {
                Student stud = new Student();
                stud.setStudentRollNo(Integer.parseInt(user.getStud_id()));

                stud.setName(name);

//                if(Objects.equals(temp_role, "SCS_MEMBER"))
//                {
//
//                    ScsMembers scsMember = new ScsMembers();
//                    scsMember.setCurrentPosition("Member");
//                    scsMember.setFromDate(LocalDate.now());
//
//                    scsMembersRepo.create(scsMember);
//
//                    scsMember = scsMembersRepo.getLatest();
//                    stud.setScsmembership_id(scsMember.getMemberId());
//                }
                studentRepo.create(stud);
//                System.out.println(user.getVertical());
                user.setStud_id(String.valueOf(stud.getStudentRollNo()));
                userRepo.save(user);



            }
//            else if(Objects.equals(temp_role, "SCS_MEMBER"))
//            {
//                ScsMembers scsMember = new ScsMembers();
//                scsMember.setCurrentPosition("Member");
//                scsMember.setFromDate(LocalDate.now());
//
//                scsMembersRepo.create(scsMember);
//
//                scsMember = scsMembersRepo.getLatest();
////                check.setScsMember(scsMember);
//                check.setScsmembership_id(scsMember.getMemberId());
//                user.setRole("SCS_MEMBER");
//
//                studentRepo.update(check);
//                userRepo.update(user);
//            }
            else
            {
                System.out.println("The user with the roll already exists!!");
                System.out.println(check.toString());
                String msg = "The user with the roll already exists!!";
                model.addAttribute("msg", msg);

                User temp_user = new User();
                temp_user.setEmail(user.getEmail());

                model.addAttribute("user", temp_user);
                List<String> options = new ArrayList<String>();
                options.add(0, "STUDENT");
                options.add(1, "FACULTY");
                model.addAttribute("options", options);
                model.addAttribute("name1", name);
                return "login/signup";
            }
        }
        else if (Objects.equals(temp_role, "FACULTY"))
        {
            System.out.println("faculty");
            Faculty fac = new Faculty();
            fac.setName(name);

            facultyRepo.create(fac);
            fac = facultyRepo.getLatest();

            user.setFac_id(String.valueOf(fac.getFacultyId()));
            System.out.println(user.getFac_id());
            userRepo.save(user);
        }

        return "login/register_success";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> listUsers = userRepo.findAll();
        model.addAttribute("listUsers", listUsers);

        return "login/users";
    }

    @GetMapping("/login")
    public String custom_login(Model model)
    {
        return "login/login";
    }

    @GetMapping("/dashboard")
//<<<<<<< Updated upstream
    @Secured({"STUDENT","ADMIN","SCS_MEMBER","FACULTY"})
    public String dashboard_student(Model model){
        User user = securityService.findLoggedInUser();
        model.addAttribute("user",user);

        List<Inititatives> inititatives = new ArrayList<>();
        List<Event> events = new ArrayList<>();

        List<Unread>unreadList = unreadRepository.getUnread(user.getUser_id().intValue());
        Boolean isRed = unreadList.isEmpty();
        model.addAttribute("isRed",isRed);
        if(user.getRole().equals("STUDENT")){
            Student student = studentRepo.get(Integer.parseInt(user.getStud_id()));
            student.setEmail(userRepo.getStudentEmail(student.getStudentRollNo()));
            model.addAttribute("student",student);

            for(InitiativeParticipation initiativeParticipation : initiativeParticipationRepository.getByStudent(Integer.parseInt(user.getStud_id())))
            {
                inititatives.add(initiativeParticipation.getInititative());
            }
            model.addAttribute("inititatives", inititatives);

            for(EventParticipation eventParticipation : eventParticipationRepository.getByStudent(Integer.parseInt(user.getStud_id())))
            {
                events.add(eventParticipation.getEvent());
            }
            model.addAttribute("events", events);

            return "dashboard/student_dashboard";
        }

        else if(user.getRole().equals("SCS_MEMBER")){
//            System.out.println(user.toString());
            Student student = studentRepo.get(Integer.parseInt(user.getStud_id()));
            student.setEmail(userRepo.getStudentEmail(student.getStudentRollNo()));
//            System.out.println(student.toString());
//            ScsMembers scsMembers = student.getScsMember();
            ScsMembers scsMembers = scsMembersRepo.get(student.getScsmembership_id());
//            System.out.println(scsMembers.toString());
            model.addAttribute("scsmember",scsMembers);
            model.addAttribute("student",student);

            for(InitiativeParticipation initiativeParticipation : initiativeParticipationRepository.getByStudent(Integer.parseInt(user.getStud_id())))
            {
                inititatives.add(initiativeParticipation.getInititative());
            }
            model.addAttribute("inititatives", inititatives);

            for(EventParticipation eventParticipation : eventParticipationRepository.getByStudent(Integer.parseInt(user.getStud_id())))
            {
                events.add(eventParticipation.getEvent());
            }
            model.addAttribute("events", events);

            return "dashboard/scsmember_dashboard";
        }

        else if(user.getRole().equals("FACULTY")){
            Faculty faculty = facultyRepo.get(Integer.parseInt(user.getFac_id()));
            faculty.setEmail(userRepo.getFacultyEmail(faculty.getFacultyId()));
            model.addAttribute("faculty",faculty);
            return "dashboard/faculty_dashboard";
        }
        return "home/index";
    }

    @GetMapping("/profile/{userID}")
//<<<<<<< Updated upstream
    @Secured({"STUDENT","ADMIN","SCS_MEMBER","FACULTY"})
    public String profile(@PathVariable String userID ,Model model){
//        User user = securityService.findLoggedInUser();
        User user = userRepo.get(Integer.parseInt(userID));
        model.addAttribute("user",user);

        List<Inititatives> inititatives = new ArrayList<>();
        List<Event> events = new ArrayList<>();

        switch (user.getRole()) {
            case "STUDENT" -> {
                Student student = studentRepo.get(Integer.parseInt(user.getStud_id()));
                student.setEmail(userRepo.getStudentEmail(student.getStudentRollNo()));
                model.addAttribute("student", student);

                for (InitiativeParticipation initiativeParticipation : initiativeParticipationRepository.getByStudent(Integer.parseInt(user.getStud_id()))) {
                    inititatives.add(initiativeParticipation.getInititative());
                }
                model.addAttribute("inititatives", inititatives);

                for (EventParticipation eventParticipation : eventParticipationRepository.getByStudent(Integer.parseInt(user.getStud_id()))) {
                    events.add(eventParticipation.getEvent());
                }
                model.addAttribute("events", events);

                return "dashboard/student_dashboard";
            }
            case "SCS_MEMBER" -> {
//            System.out.println(user.toString());
                Student student = studentRepo.get(Integer.parseInt(user.getStud_id()));
                student.setEmail(userRepo.getStudentEmail(student.getStudentRollNo()));
//            System.out.println(student.toString());
//            ScsMembers scsMembers = student.getScsMember();
                ScsMembers scsMembers = scsMembersRepo.get(student.getScsmembership_id());
//            System.out.println(scsMembers.toString());
                model.addAttribute("scsmember", scsMembers);
                model.addAttribute("student", student);

                for (InitiativeParticipation initiativeParticipation : initiativeParticipationRepository.getByStudent(Integer.parseInt(user.getStud_id()))) {
                    inititatives.add(initiativeParticipation.getInititative());
                }
                model.addAttribute("inititatives", inititatives);

                for (EventParticipation eventParticipation : eventParticipationRepository.getByStudent(Integer.parseInt(user.getStud_id()))) {
                    events.add(eventParticipation.getEvent());
                }
                model.addAttribute("events", events);

                return "dashboard/scsmember_dashboard";
            }
            case "FACULTY" -> {
                Faculty faculty = facultyRepo.get(Integer.parseInt(user.getFac_id()));
                faculty.setEmail(userRepo.getFacultyEmail(faculty.getFacultyId()));
                model.addAttribute("faculty", faculty);
                return "dashboard/faculty_dashboard";
            }
        }
        return "home/index";
    }

//    @GetMapping("/js/**")
//    public String handle_js()
//    {
//        return "redirect:/home";
//    }
//
//    @GetMapping("/images/**")
//    public String handle_images()
//    {
//        return "redirect:/home";
//    }
//
//    @GetMapping("/css/**")
//    public String handle_css()
//    {
//        return "redirect:/home";
//    }
}


