package com.example.scs.controller;

import javax.validation.Valid;

import com.example.scs.model.*;
import com.example.scs.repos.CounsellingSessionsRepository;
import com.example.scs.repos.CounselorRepository;
import com.example.scs.repos.StudentRepository;
import com.example.scs.repos.UserRepository;
import com.example.scs.services.SecurityService;
import com.example.scs.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Controller
@RequestMapping("/counselors")
public class CounselorController {

    @Autowired
    SecurityService securityService;
    @Autowired
    CounselorRepository counselorRepository;
    @Autowired
    CounsellingSessionsRepository counsellingSessionsRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    UserRepository userRepository;

    public CounselorController(final CounselorRepository counselorRepository) {
        this.counselorRepository = counselorRepository;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("counselors", counselorRepository.findAll());
        return "counselor/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("counselor") final Counselor counselor) {
        return "counselor/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("counselor") @Valid final Counselor counselor,
                      final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "counselor/add";
        }
        counselorRepository.create(counselor);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("counselor.create.success"));
        return "redirect:/counselors";
    }

    @GetMapping("/{counselorID}")
    public String initiative_page(@PathVariable final Integer counselorID, final Model model) {
        User user = securityService.findLoggedInUser();

        if (user == null)
            return "redirect:/counselors/default/{counselorID}";
//            System.out.println(user.toString());

        return switch (user.getRole()) {
            case "ADMIN" -> "redirect:/counselors/admin/{counselorID}";
            case "STUDENT" -> "redirect:/counselors/student/{counselorID}";
            case "SCS_MEMBER" -> "redirect:/counselors/member/{counselorID}";
            default -> "redirect:/counselors/default/{counselorID}";
        };
    }

    @GetMapping("/admin/{counselorID}")
    public String admin_initiative(@PathVariable final Integer counselorID, final Model model) {
        model.addAttribute("counselor", counselorRepository.get(counselorID));
        return "counselor/admin_counselor_page";
    }

    @GetMapping("/member/{counselorID}")
    public String member_initiative(@PathVariable final Integer counselorID, final Model model) throws ParseException {
        User user = securityService.findLoggedInUser();
        Counselor counselor = counselorRepository.get(counselorID);

        model.addAttribute("current_user", user);

        SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
        String now = String.valueOf(LocalDate.now());

        int flag = 1;
        CounsellingSessions temp_sessions = counsellingSessionsRepository.getWithCounselorStudent(counselorID, Integer.valueOf(user.getStud_id()), now);
        if(temp_sessions != null)
        {
            flag = 0;
            model.addAttribute("from_time", temp_sessions.getFromTime());
            model.addAttribute("to_time", temp_sessions.getToTime());
        }

        List<CounsellingSessions> sessions = counsellingSessionsRepository.getWithCounselor(counselorID, now);
        int session_count;
        if(sessions == null)
        {
            session_count = 0;
        }
        else
        {
            session_count = sessions.size();
        }

        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");

        System.out.println(counselor.getStart_time());

        Date start = df.parse(counselor.getStart_time());
        Calendar cal = Calendar.getInstance();
        cal.setTime(start);
        cal.add(Calendar.MINUTE, 30*session_count);
        counselor.setStart_time(df.format(cal.getTime()));

        cal.setTime(df.parse(counselor.getStart_time()));
        cal.add(Calendar.MINUTE, 30);
        String session_end = df.format(cal.getTime());
        model.addAttribute("session_end", session_end);

        System.out.println(">>>>");
        System.out.println(session_count);
        System.out.println(counselor.getStart_time());

        if(Objects.equals(counselor.getStart_time(), counselor.getEnd_time()))
        {
            flag = 2;
        }

        model.addAttribute("flag", flag);
        model.addAttribute("counselor", counselor);
        return "counselor/member_counselor_page";
    }

    @GetMapping("/student/{counselorID}")
    public String student_initiative(@PathVariable final Integer counselorID, final Model model) throws ParseException {
        User user = securityService.findLoggedInUser();
        Counselor counselor = counselorRepository.get(counselorID);

        model.addAttribute("current_user", user);

        SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
        String now = String.valueOf(LocalDate.now());

        int flag = 1;
        CounsellingSessions temp_sessions = counsellingSessionsRepository.getWithCounselorStudent(counselorID, Integer.valueOf(user.getStud_id()), now);
        if(temp_sessions != null)
        {
            flag = 0;
            model.addAttribute("from_time", temp_sessions.getFromTime());
            model.addAttribute("to_time", temp_sessions.getToTime());
        }

        List<CounsellingSessions> sessions = counsellingSessionsRepository.getWithCounselor(counselorID, now);
        int session_count;
        if(sessions == null)
        {
            session_count = 0;
        }
        else
        {
            session_count = sessions.size();
        }

        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");

        System.out.println(counselor.getStart_time());

        Date start = df.parse(counselor.getStart_time());
        Calendar cal = Calendar.getInstance();
        cal.setTime(start);
        cal.add(Calendar.MINUTE, 30*session_count);
        counselor.setStart_time(df.format(cal.getTime()));

        cal.setTime(df.parse(counselor.getStart_time()));
        cal.add(Calendar.MINUTE, 30);
        String session_end = df.format(cal.getTime());
        model.addAttribute("session_end", session_end);

        System.out.println(">>>>");
        System.out.println(session_count);
        System.out.println(counselor.getStart_time());

        if(Objects.equals(counselor.getStart_time(), counselor.getEnd_time()))
        {
            flag = 2;
        }

        model.addAttribute("flag", flag);
        model.addAttribute("counselor", counselor);

        return "counselor/student_counselor_page";
    }

    @GetMapping("/default/{counselorID}")
    public String default_initiative(@PathVariable final Integer counselorID, final Model model) {
        model.addAttribute("counselor", counselorRepository.get(counselorID));
        return "counselor/default_counselor_page";
    }

    @GetMapping("/edit/{counselorId}")
    public String edit(@PathVariable final Integer counselorId, final Model model) {
        model.addAttribute("counselor", counselorRepository.get(counselorId));
        return "counselor/edit";
    }

    @PostMapping("/edit/{counselorId}")
    public String edit(@PathVariable final Integer counselorId,
                       @ModelAttribute("counselor") @Valid final Counselor counselor,
                       final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "counselor/edit";
        }
        counselorRepository.update(counselorId, counselor);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("counselor.update.success"));
        return "redirect:/counselors";
    }

    @PostMapping("/delete/{counselorId}")
    public String delete(@PathVariable final Integer counselorId,
                         final RedirectAttributes redirectAttributes) {
        final String referencedWarning = counselorRepository.getReferencedWarning(counselorId);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            counselorRepository.delete(counselorId);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("counselor.delete.success"));
        }
        return "redirect:/counselors";
    }

    @PostMapping("/{counselorID}/register/{userID}")
    public String register(@PathVariable final Integer counselorID, @PathVariable final Integer userID,
                           final RedirectAttributes redirectAttributes) throws ParseException {
        CounsellingSessions counsellingSessions = new CounsellingSessions();

        Counselor counselor = counselorRepository.get(counselorID);
        User user = userRepository.get(userID);
        Student student = studentRepository.get(Integer.parseInt(user.getStud_id()));

//        System.out.println(user.toString());
//        System.out.println(user.getStud_id());
//        System.out.println(student.getStudentRollNo());

        SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
        String now = String.valueOf(LocalDate.now());
        counsellingSessions.setDate(now);

        counsellingSessions.setCounselsTo(student);
        counsellingSessions.setCounseledBy(counselor);

        List<CounsellingSessions> sessions = counsellingSessionsRepository.getWithCounselor(counselorID, now);
        int session_count;
        if(sessions == null)
        {
            session_count = 0;
        }
        else
        {
            session_count = sessions.size();
        }

        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");

        System.out.println(counselor.getStart_time());

        Date start = df.parse(counselor.getStart_time());
        Calendar cal = Calendar.getInstance();
        cal.setTime(start);
        cal.add(Calendar.MINUTE, 30*session_count);
        counselor.setStart_time(df.format(cal.getTime()));

        cal.setTime(df.parse(counselor.getStart_time()));
        cal.add(Calendar.MINUTE, 30);
        String session_end = df.format(cal.getTime());

        counsellingSessions.setFromTime(counselor.getStart_time());
        counsellingSessions.setToTime(session_end);

        counsellingSessionsRepository.create(counsellingSessions);

        return "redirect:/counselors/{counselorID}";
    }

}
