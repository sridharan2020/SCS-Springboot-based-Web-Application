package com.example.scs.controller;

import com.example.scs.model.*;
import com.example.scs.repos.*;
import com.example.scs.services.SecurityService;
import com.example.scs.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Controller
@RequestMapping("/events")
public class EventController {

    @Autowired
    EventRepository eventRepo;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EventParticipationRepository eventParticipationRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    SecurityService securityService;
    @Autowired
    NotificationRepository notificationRepository;

    public EventController(final EventRepository eventRepo) {
        this.eventRepo = eventRepo;
    }

//    private final VenueRepository venueRepository;
//    private final EventsPermissionRepository eventsPermissionRepository;

//    public EventController(final VenueRepository venueRepository,
//            final EventsPermissionRepository eventsPermissionRepository) {
//        this.venueRepository = venueRepository;
//        this.eventsPermissionRepository = eventsPermissionRepository;
//    }

//    @ModelAttribute
//    public void prepareContext(final Model model) {
//        model.addAttribute("venuedAtValues", venueRepository.findAll().stream().collect(
//                Collectors.toMap(Venue::getVenueID, Venue::getVenueName)));
//        model.addAttribute("seekpermissionValues", eventsPermissionRepository.findAll().stream().collect(
//                Collectors.toMap(EventsPermission::getPermissionID, EventsPermission::getAttestedBy)));
//    }

    @GetMapping
    public String list(final Model model) throws ParseException {
        List<Event> all_events = eventRepo.findAll();
        User user = securityService.findLoggedInUser();
        Boolean admin = Boolean.FALSE;
        if(user != null)
        {if((Objects.equals(user.getRole(), "ADMIN"))||(Objects.equals(user.getRole(), "SCS_MEMBER"))) {
            admin=true;
        }}
        model.addAttribute("admin", admin);

        List<Event> past_events = new ArrayList<>();
        List<Event> ongoing_events = new ArrayList<>();

        SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
        String now = String.valueOf(LocalDate.now());
//        System.out.println(now);
//        System.out.println(dtf.parse(now));

        for(Event event : all_events)
        {
//            System.out.println(event.toString());
//            System.out.println(dtf.parse(event.getDate()));
//            System.out.println(event.getDate());
//            System.out.println(dtf.parse(now).before(dtf.parse(event.getDate())));

            if(dtf.parse(event.getDate()).before(dtf.parse(now)))
            {
                past_events.add(event);
            }
            else
            {
                ongoing_events.add(event);
            }
        }

        model.addAttribute("events", all_events);
        model.addAttribute("past_events", past_events);
        model.addAttribute("ongoing_events", ongoing_events);
        return "event/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("event") final Event event) {
        return "event/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("event") @Valid final Event event,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "event/add";
        }
        eventRepo.create(event);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("event.create.success"));
        return "redirect:/events";
    }

    @GetMapping("/{eventID}")
    public String event_page(@PathVariable final Integer eventID, final Model model) {
        User user = securityService.findLoggedInUser();

        if (user == null)
            return "redirect:/events/default/{eventID}";
        else
            System.out.println(user.toString());

        return switch (user.getRole()) {
            case "ADMIN" -> "redirect:/events/admin/{eventID}";
            case "STUDENT" -> "redirect:/events/student/{eventID}";
            case "SCS_MEMBER" -> "redirect:/events/member/{eventID}";
            default -> "redirect:/events/default/{eventID}";
        };
    }

    @GetMapping("/admin/{eventID}")
    public String admin_event(@PathVariable final Integer eventID, final Model model) {
        model.addAttribute("event", eventRepo.get(eventID));

        List<EventParticipation> student_parts = eventParticipationRepository.getByEvent(eventID);
        List<Student> student = new ArrayList<>();
        for(EventParticipation stud_part : student_parts)
        {
            student.add(stud_part.getStudent());
        }

        model.addAttribute("students", student);
        return "event/admin_event_page";
    }

    @GetMapping("/member/{eventID}")
    public String member_event(@PathVariable final Integer eventID, final Model model) throws ParseException {
        User user = securityService.findLoggedInUser();
        Event event1 = eventRepo.get(eventID);
        model.addAttribute("event", event1);
        model.addAttribute("current_user", user);

        int flag = 1;
        if(eventParticipationRepository.get(eventID, Integer.valueOf(user.getStud_id())) != null)
        {
            flag = 0;
        }

        String date1 = event1.getDate();
        String time1 = event1.getFromTime();
        String date_time1 = date1 + " " + time1;

        String fmt = "yyyy-MM-dd HH:mm:ss";
        DateFormat df = new SimpleDateFormat(fmt);

        Date dt = df.parse(date_time1);
        Date current_date = df.parse(df.format(new Date()));

        if(dt.before(current_date))
        {
            flag = 2;
        }

        model.addAttribute("flag", flag);

        List<EventParticipation> student_parts = eventParticipationRepository.getByEvent(eventID);
        List<Student> student = new ArrayList<>();
        for(EventParticipation stud_part : student_parts)
        {
            student.add(stud_part.getStudent());
        }

        model.addAttribute("students", student);

        return "event/member_event_page";
    }

    @GetMapping("/student/{eventID}")
    public String student_event(@PathVariable final Integer eventID, final Model model) throws ParseException {
        User user = securityService.findLoggedInUser();
        Event event1 = eventRepo.get(eventID);
        model.addAttribute("event", event1);
        model.addAttribute("current_user", user);

        int flag = 1;
        if(eventParticipationRepository.get(eventID, Integer.valueOf(user.getStud_id())) != null)
        {
            flag = 0;
        }

        String date1 = event1.getDate();
        String time1 = event1.getFromTime();
        String date_time1 = date1 + " " + time1;

        String fmt = "yyyy-MM-dd HH:mm:ss";
        DateFormat df = new SimpleDateFormat(fmt);

        Date dt = df.parse(date_time1);
        Date current_date = df.parse(df.format(new Date()));
        System.out.println(dt);
        System.out.println(current_date);

        if(dt.before(current_date))
        {
            flag = 2;
        }

        model.addAttribute("flag", flag);

        List<EventParticipation> student_parts = eventParticipationRepository.getByEvent(eventID);
        List<Student> student = new ArrayList<>();
        for(EventParticipation stud_part : student_parts)
        {
            student.add(stud_part.getStudent());
        }

        model.addAttribute("students", student);

        return "event/student_event_page";
    }

    @GetMapping("/default/{eventID}")
    public String default_event(@PathVariable final Integer eventID, final Model model) {
        model.addAttribute("event", eventRepo.get(eventID));

        List<EventParticipation> student_parts = eventParticipationRepository.getByEvent(eventID);
        List<Student> student = new ArrayList<>();
        for(EventParticipation stud_part : student_parts)
        {
            student.add(stud_part.getStudent());
        }

        model.addAttribute("students", student);
        return "event/default_event_page";
    }

    @GetMapping("/{studentID}/registered")
    public String registered_events(@PathVariable final Integer studentID, Model model) {
        User user = securityService.findLoggedInUser();
        List<Event> events = new ArrayList<>();
        for(EventParticipation eventParticipation : eventParticipationRepository.getByStudent(Integer.parseInt(user.getStud_id())))
        {
            events.add(eventParticipation.getEvent());
        }
        model.addAttribute("events", events);
        return "event/registered_list";
    }

    @GetMapping("/{eventID}/edit")
    public String edit(@PathVariable final Integer eventID, final Model model) {
        model.addAttribute("event", eventRepo.get(eventID));
        return "event/edit";
    }

    @PostMapping("/{eventID}/edit")
    public String edit(@PathVariable final Integer eventID,
            @ModelAttribute("event") @Valid final Event event,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "event/edit";
        }
        eventRepo.update(eventID, event);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("event.update.success"));
        return "redirect:/events";
    }

    @PostMapping("/{eventID}/delete")
    public String delete(@PathVariable final Integer eventID,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = eventRepo.getReferencedWarning(eventID);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            eventRepo.delete(eventID);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("event.delete.success"));
        }
        return "redirect:/events";
    }

    @PostMapping("/{eventID}/register/{userID}")
    public String register(@PathVariable final Integer eventID, @PathVariable final Integer userID,
                         final RedirectAttributes redirectAttributes) throws ParseException {
        EventParticipation eventParticipation = new EventParticipation();

        Event event = eventRepo.get(eventID);
        User user = userRepository.get(userID);
        Student student = studentRepository.get(Integer.parseInt(user.getStud_id()));

        System.out.println(user.toString());
        System.out.println(user.getStud_id());
        System.out.println(student.getStudentRollNo());

        eventParticipation.setStudent(student);
        eventParticipation.setEvent(event);

        eventParticipation.setStudent_id(String.valueOf(student.getStudentRollNo()));
        eventParticipation.setEvent_id(String.valueOf(event.getEventID()));
        eventParticipation.setRole(user.getRole());

        eventParticipationRepository.create(eventParticipation);

        if(notificationRepository.getEvent(eventID) == null)
        {
            Notification notification = new Notification();
            notification.setType("events/student");
            notification.setUrl_id(eventID.toString());

            String date1 = event.getDate();
            String time1 = event.getFromTime();
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

        return "redirect:/events/{eventID}";
    }

    @PostMapping("/{eventID}/unregister/{userID}")
    public String unregister(@PathVariable final Integer eventID, @PathVariable final Integer userID,
                           final RedirectAttributes redirectAttributes) {
        EventParticipation eventParticipation = new EventParticipation();

        User user = userRepository.get(userID);

        eventParticipationRepository.delete(eventID, Integer.parseInt(user.getStud_id()));

        return "redirect:/events/{eventID}";
    }

}
