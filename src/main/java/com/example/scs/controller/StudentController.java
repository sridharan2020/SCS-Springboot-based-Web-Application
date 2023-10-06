package com.example.scs.controller;

import com.example.scs.model.Student;
import com.example.scs.model.User;
import com.example.scs.repos.StudentRepository;

import com.example.scs.services.SecurityService;
import com.example.scs.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
public class StudentController {
    @Autowired
    private final StudentRepository studentRepo;
    @Autowired
    private final SecurityService securityService;

    public StudentController(final StudentRepository studentRepo, SecurityService securityService) {
        this.studentRepo = studentRepo;
        this.securityService = securityService;
    }

//    @ModelAttribute
//    public void prepareContext(final Model model) {
//        model.addAttribute("studentPassedOutValues", alumniAdvisorsRepository.findAll().stream().collect(
//                Collectors.toMap(AlumniAdvisors::getAlumniID, AlumniAdvisors::getAlumniID)));
//        model.addAttribute("sCSMembershipValues", sCSMembersRepository.findAll().stream().collect(
//                Collectors.toMap(SCSMembers::getMemberId, SCSMembers::getCurrentPosition)));
//        model.addAttribute("studparticipateeventsValues", eventRepository.findAll().stream().collect(
//                Collectors.toMap(Event::getEventID, Event::getResources)));
//        model.addAttribute("studentawardedsValues", awardRepository.findAll().stream().collect(
//                Collectors.toMap(Award::getAwardID, Award::getAwardType)));
//        model.addAttribute("studinitiativepartsValues", inititativesRepository.findAll().stream().collect(
//                Collectors.toMap(Inititatives::getInitiativesId, Inititatives::getTitle)));
//        model.addAttribute("studspartofIMgpValues", iMGroupsRepository.findAll().stream().collect(
//                Collectors.toMap(IMGroups::getGroupID, IMGroups::getGroupID)));
//    }

    @GetMapping("/students")
    public String list() {
        User user = securityService.findLoggedInUser();

        if (user == null)
            return "redirect:/default/students";
        else
            System.out.println(user.toString());

        return switch (user.getRole()) {
            case "ADMIN" -> "redirect:/admin/students";
            case "STUDENT" -> "redirect:/student/students";
            case "SCS_MEMBER" -> "redirect:/member/students";
            default -> "redirect:/default/students";
        };
    }

    @GetMapping("/admin/students")
    public String admin_list(final Model model) {
        model.addAttribute("students", studentRepo.findAll());
        return "student/admin_list";
    }

    @GetMapping("/member/students")
    public String member_list(final Model model) {
        model.addAttribute("students", studentRepo.findAll());
        return "student/member_list";
    }

    @GetMapping("/student/students")
    public String student_list(final Model model) {
        model.addAttribute("students", studentRepo.findAll());
        return "student/student_list";
    }

    @GetMapping("/default/students")
    public String default_list(final Model model) {
        model.addAttribute("students", studentRepo.findAll());
        return "student/default_list";
    }

    @GetMapping("/students/add")
    public String add(@ModelAttribute("student") final Student student) {
        return "student/add";
    }

    @PostMapping("/students/add")
    public String add(@ModelAttribute("student") @Valid final Student student,
                      final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "student/add";
        }
        studentRepo.create(student);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("student.create.success"));
        return "redirect:/students";
    }

    @GetMapping("/students/edit/{studentRollNo}")
    public String edit(@PathVariable final Integer studentRollNo, final Model model) {
        model.addAttribute("student", studentRepo.get(studentRollNo));
        return "student/edit";
    }

    @PostMapping("/students/edit/{studentRollNo}")
    public String edit(@PathVariable final Integer studentRollNo,
            @ModelAttribute("student") @Valid final Student student,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "student/edit";
        }
        studentRepo.update(studentRollNo, student);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("student.update.success"));
        return "redirect:/students";
    }

    @PostMapping("/students/delete/{studentRollNo}")
    public String delete(@PathVariable final Integer studentRollNo,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = studentRepo.getReferencedWarning(studentRollNo);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            studentRepo.delete(studentRollNo);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("student.delete.success"));
        }
        return "redirect:/students";
    }

}
