package com.example.scs.controller;

import com.example.scs.model.CounsellingSessions;
import com.example.scs.model.Counselor;
import com.example.scs.model.Student;
import com.example.scs.model.User;
import com.example.scs.repos.CounsellingSessionsRepository;
import com.example.scs.repos.CounselorRepository;
import com.example.scs.repos.StudentRepository;
import com.example.scs.services.SecurityService;
import com.example.scs.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


import java.util.*;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/counsellingSessionss")
public class CounsellingSessionsController {

    private final CounsellingSessionsRepository counsellingSessionsRepository;
    private final CounselorRepository counselorRepository;
    private final StudentRepository studentRepository;

    @Autowired
    SecurityService securityService;

    public CounsellingSessionsController(
            final CounsellingSessionsRepository counsellingSessionsRepository,
            final CounselorRepository counselorRepository,
            final StudentRepository studentRepository) {
        this.counsellingSessionsRepository = counsellingSessionsRepository;
        this.counselorRepository = counselorRepository;
        this.studentRepository = studentRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        Map<Integer, String> counseledByStringValues = new HashMap<>();
        for(Counselor counselor1 : counselorRepository.findAll())
        {
//            System.out.println(counselor1.toString());
            counseledByStringValues.put(counselor1.getCounselorId(), counselor1.getName());
        }

        Map<Integer, String> counselsToStringValues = new HashMap<>();
        for(Student student1 : studentRepository.findAll())
        {
//            System.out.println(student1.toString());
            counselsToStringValues.put(student1.getStudentRollNo(), student1.getName());
        }
        model.addAttribute("counseledByStringValues", counseledByStringValues);
        model.addAttribute("counselsToStringValues", counselsToStringValues);
    }

    @GetMapping
    public String list(final Model model) {
        User user = securityService.findLoggedInUser();

        if (user == null)
            return "redirect:/counselors";

        List<CounsellingSessions> sessions = counsellingSessionsRepository.findAllByStudent(Integer.parseInt(user.getStud_id()));
        if(Objects.equals(user.getRole(), "ADMIN"))
        {
            sessions = counsellingSessionsRepository.findAll();
        }
        for(CounsellingSessions counsellingSessions1 : sessions)
        {
            counsellingSessions1.setCounselsToString(counsellingSessionsRepository.findStudentBySession(counsellingSessions1.getSessionID()));
        }
        model.addAttribute("counsellingSessionss", sessions);
        return "counsellingSessions/list";
    }

    @GetMapping("/add")
    public String get_add(@ModelAttribute("counsellingSessions") final CounsellingSessions counsellingSessions, Model model) {
        Map<Integer, String> counseledByValues = new HashMap<>();
        for(Counselor counselor1 : counselorRepository.findAll())
        {
            counseledByValues.put(counselor1.getCounselorId(), counselor1.getName());
        }
        model.addAttribute("counseledByValues", counseledByValues);

        Map<Integer, String> counseledToValues = new HashMap<>();
        for(Student student : studentRepository.findAll())
        {
            counseledToValues.put(student.getStudentRollNo(), student.getName());
        }
        model.addAttribute("counseledToValues", counseledToValues);

        return "counsellingSessions/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute CounsellingSessions counsellingSessions, Model model) {
        model.addAttribute("counsellingSessions", counsellingSessions);
        System.out.println(">>>>>>>>>>>");
        System.out.println(counsellingSessions.getCounseledByString());
        System.out.println(counsellingSessions.getCounselsToString());
        System.out.println(counsellingSessions.getDate());
        System.out.println(counsellingSessions.getToTime());

        Counselor counselor = counselorRepository.get(Integer.parseInt(counsellingSessions.getCounseledByString()));
        counsellingSessions.setCounseledBy(counselor);

        Student student = studentRepository.get(Integer.parseInt(counsellingSessions.getCounselsToString()));
        counsellingSessions.setCounselsTo(student);

        counsellingSessionsRepository.create(counsellingSessions);
        return "redirect:/counsellingSessionss";
    }

    @GetMapping("/edit/{sessionID}")
    public String edit(@PathVariable final Integer sessionID, final Model model) {
        model.addAttribute("counsellingSessions", counsellingSessionsRepository.get(sessionID));
        return "counsellingSessions/edit";
    }

    @PostMapping("/edit/{sessionID}")
    public String edit(@PathVariable final Integer sessionID,
                       @ModelAttribute("counsellingSessions") @Valid final CounsellingSessions counsellingSessions,
                       final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "counsellingSessions/edit";
        }
        counsellingSessionsRepository.update(sessionID, counsellingSessions);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("counsellingSessions.update.success"));
        return "redirect:/counsellingSessionss";
    }

    @PostMapping("/delete/{sessionID}")
    public String delete(@PathVariable final Integer sessionID,
                         final RedirectAttributes redirectAttributes) {
        counsellingSessionsRepository.delete(sessionID);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("counsellingSessions.delete.success"));
        return "redirect:/counsellingSessionss";
    }

}
