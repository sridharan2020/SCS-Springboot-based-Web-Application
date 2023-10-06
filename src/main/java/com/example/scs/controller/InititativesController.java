package com.example.scs.controller;

import javax.validation.Valid;

import com.example.scs.model.*;
import com.example.scs.repos.*;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Controller
@RequestMapping("/initiatives")
public class InititativesController {

    @Autowired
    SecurityService securityService;
    @Autowired
    InitiativeParticipationRepository initiativeParticipationRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    NotificationRepository notificationRepository;
    private final InititativesRepository inititativesRepository;
    public InititativesController(final InititativesRepository inititativesRepository){
        this.inititativesRepository = inititativesRepository;
    }
//    private final InitiativesPermissionRepository initiativesPermissionRepository;
//    private final VenueRepository venueRepository;

//    public InititativesController(final InititativesService inititativesService,
//                                  final InitiativesPermissionRepository initiativesPermissionRepository,
//                                  final VenueRepository venueRepository) {
//        this.inititativesService = inititativesService;
//        this.initiativesPermissionRepository = initiativesPermissionRepository;
//        this.venueRepository = venueRepository;
//    }

//    @ModelAttribute
//    public void prepareContext(final Model model) {
//        model.addAttribute("permissionforinitiativeValues", initiativesPermissionRepository.findAll().stream().collect(
//                Collectors.toMap(InitiativesPermission::getPermissionID, InitiativesPermission::getAttestedBy)));
//        model.addAttribute("initiatvehostedatValues", venueRepository.findAll().stream().collect(
//                Collectors.toMap(Venue::getVenueID, Venue::getVenueName)));
//    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("inititatives", inititativesRepository.findAll());

        User user = securityService.findLoggedInUser();
        Boolean admin = Boolean.FALSE;
        if(user != null)
        {if((Objects.equals(user.getRole(), "ADMIN"))||(Objects.equals(user.getRole(), "SCS_MEMBER"))) {
            admin=true;
        }}
        model.addAttribute("admin", admin);
        return "inititatives/list";

    }

    @GetMapping("/add")
    public String add(@ModelAttribute("inititatives") final Inititatives inititatives) {
        return "inititatives/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("inititatives") @Valid final Inititatives inititatives,
                      final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
        	System.out.println("!!!!!");
            return "inititatives/add";
        }
        System.out.println(inititatives.toString());
        inititativesRepository.create(inititatives);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("inititatives.create.success"));
        return "redirect:/initiatives";
    }

    @GetMapping("/{initiativeID}")
    public String initiative_page(@PathVariable final Integer initiativeID, final Model model) {
        User user = securityService.findLoggedInUser();

        if (user == null)
            return "redirect:/initiatives/default/{initiativeID}";
        else
            System.out.println(user.toString());

        return switch (user.getRole()) {
            case "ADMIN" -> "redirect:/initiatives/admin/{initiativeID}";
            case "STUDENT" -> "redirect:/initiatives/student/{initiativeID}";
            case "SCS_MEMBER" -> "redirect:/initiatives/member/{initiativeID}";
            default -> "redirect:/initiatives/default/{initiativeID}";
        };
    }

    @GetMapping("/admin/{initiativeID}")
    public String admin_initiative(@PathVariable final Integer initiativeID, final Model model) {
        model.addAttribute("initiative", inititativesRepository.get(initiativeID));
        return "inititatives/admin_initiative_page";
    }

    @GetMapping("/member/{initiativeID}")
    public String member_initiative(@PathVariable final Integer initiativeID, final Model model) {
        User user = securityService.findLoggedInUser();
        model.addAttribute("initiative", inititativesRepository.get(initiativeID));
        model.addAttribute("current_user", user);

        Boolean flag = Boolean.TRUE;
        if(initiativeParticipationRepository.get(initiativeID, Integer.valueOf(user.getStud_id())) != null)
        {
            flag = Boolean.FALSE;
        }
        model.addAttribute("flag", flag);

        model.addAttribute("flag", flag);

        List<InitiativeParticipation> student_parts = initiativeParticipationRepository.getByInitiative(initiativeID);
        List<Student> student = new ArrayList<>();
        for(InitiativeParticipation stud_part : student_parts)
        {
            student.add(stud_part.getStudent());
        }

        model.addAttribute("students", student);

        return "inititatives/member_initiative_page";
    }

    @GetMapping("/student/{initiativeID}")
    public String student_initiative(@PathVariable final Integer initiativeID, final Model model) {
        User user = securityService.findLoggedInUser();
        model.addAttribute("initiative", inititativesRepository.get(initiativeID));
        model.addAttribute("current_user", user);

        Boolean flag = Boolean.TRUE;
        if(initiativeParticipationRepository.get(initiativeID, Integer.valueOf(user.getStud_id())) != null)
        {
            flag = Boolean.FALSE;
        }
        model.addAttribute("flag", flag);

        model.addAttribute("flag", flag);

        List<InitiativeParticipation> student_parts = initiativeParticipationRepository.getByInitiative(initiativeID);
        List<Student> student = new ArrayList<>();
        for(InitiativeParticipation stud_part : student_parts)
        {
            student.add(stud_part.getStudent());
        }

        model.addAttribute("students", student);

        return "inititatives/student_initiative_page";
    }

    @GetMapping("/default/{initiativeID}")
    public String default_initiative(@PathVariable final Integer initiativeID, final Model model) {
        model.addAttribute("initiative", inititativesRepository.get(initiativeID));
        return "inititatives/default_initiative_page";
    }

    @GetMapping("/{studentID}/registered")
    public String registered_events(@PathVariable final Integer studentID, Model model) {
        User user = securityService.findLoggedInUser();
        List<Inititatives> inititatives = new ArrayList<>();
        for(InitiativeParticipation initiativeParticipation : initiativeParticipationRepository.getByStudent(Integer.parseInt(user.getStud_id())))
        {
            inititatives.add(initiativeParticipation.getInititative());
        }
        model.addAttribute("inititatives", inititatives);
        return "inititatives/list";
    }

    @GetMapping("/edit/{initiativesId}")
    public String edit(@PathVariable final Integer initiativesId, final Model model) {
        model.addAttribute("inititatives", inititativesRepository.get(initiativesId));
        return "inititatives/edit";
    }

    @PostMapping("/edit/{initiativesId}")
    public String edit(@PathVariable final Integer initiativesId,
                       @ModelAttribute("inititatives") @Valid final Inititatives inititatives,
                       final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "inititatives/edit";
        }
        inititativesRepository.update(initiativesId, inititatives);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("inititatives.update.success"));
        return "redirect:/initiatives";
    }

    @PostMapping("/delete/{initiativesId}")
    public String delete(@PathVariable final Integer initiativesId,
                         final RedirectAttributes redirectAttributes) {
        final String referencedWarning = inititativesRepository.getReferencedWarning(initiativesId);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            inititativesRepository.delete(initiativesId);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("inititatives.delete.success"));
        }
        return "redirect:/initiatives";
    }

    @PostMapping("/{initiativeID}/register/{userID}")
    public String register(@PathVariable final Integer initiativeID, @PathVariable final Integer userID,
                           final RedirectAttributes redirectAttributes) throws ParseException {
        InitiativeParticipation initiativeParticipation = new InitiativeParticipation();

        Inititatives inititatives = inititativesRepository.get(initiativeID);
        User user = userRepository.get(userID);
        Student student = studentRepository.get(Integer.parseInt(user.getStud_id()));

        System.out.println(user.toString());
        System.out.println(user.getStud_id());
        System.out.println(student.getStudentRollNo());

        initiativeParticipation.setStudent_id(student.getStudentRollNo());
        initiativeParticipation.setInitiative_id(inititatives.getInitiativesId());
        initiativeParticipation.setRole(user.getRole());

        initiativeParticipationRepository.create(initiativeParticipation);

        if(notificationRepository.getInitiative(initiativeID) == null)
        {
            Notification notification = new Notification();
            notification.setType("initiatives/student");
            notification.setUrl_id(initiativeID.toString());

            String date1 = inititatives.getStartDate();
            String time1 = inititatives.getTimings();
            String date_time1 = date1 + " " + time1;

            String fmt = "yyyy-MM-dd HH:mm";
            DateFormat df = new SimpleDateFormat(fmt);

            Date dt = df.parse(date_time1);

            Instant current = dt.toInstant();
            LocalDateTime ldt = LocalDateTime.ofInstant(current, ZoneId.systemDefault());

            notification.setTime(ldt);
            System.out.println(notification.toString());
            notificationRepository.create(notification);
        }

        return "redirect:/initiatives/{initiativeID}";
    }

}
