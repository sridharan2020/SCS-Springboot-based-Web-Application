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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Controller
@RequestMapping("/team_meeting")
public class TeamMeetingController {

    @Autowired
    SecurityService securityService;

    @Autowired
    ScsMembersRepository scsMembersRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    UnreadRepository unreadRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    VerticalRepository verticalRepository;

    @Autowired
    private final TeamMeetingRepository teamMeetingRepository;

    public TeamMeetingController(final TeamMeetingRepository teamMeetingRepository) {
        this.teamMeetingRepository = teamMeetingRepository;
    }


    @GetMapping
    public String list(final Model model) {
        model.addAttribute("team_meetings", teamMeetingRepository.findAll());
        User user = securityService.findLoggedInUser();
        Student student = studentRepository.get(Integer.parseInt(user.getStud_id()));
        Integer member_id = student.getScsmembership_id();
        model.addAttribute("member_id",member_id);
        Boolean isAdmin = user.getRole().equals("ADMIN");
        model.addAttribute("isAdmin",isAdmin);
        return "team_meeting/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("team_meeting") final TeamMeeting teamMeeting) {
        return "team_meeting/add";
    }

    @PostMapping("/add")
    public String add(@RequestParam Map<String,String> body) {
//        System.out.println(body.size());

        TeamMeeting teamMeeting = new TeamMeeting();
        teamMeeting.setDate(body.get("date"));
        teamMeeting.setFrom_time(body.get("from_time"));
        teamMeeting.setMeeting_title(body.get("meeting_title"));
        teamMeeting.setMeeting_description(body.get("meeting_description"));
        teamMeeting.setLocation(body.get("location"));
        teamMeeting.setTo_time(body.get("to_time"));
//        System.out.println(teamMeeting.toString());
        User user = securityService.findLoggedInUser();
        Student student = studentRepository.get(Integer.parseInt(user.getStud_id()));
        teamMeeting.setOrganiser_id(String.valueOf(student.getScsmembership_id()));
        teamMeetingRepository.create(teamMeeting);

        ScsMembers curr_member = scsMembersRepository.get(student.getScsmembership_id());

        Notification notification = new Notification();
        notification.setType("team_meeting");
        notification.setTime(LocalDateTime.now());
        notification.setUrl_id(String.valueOf(teamMeetingRepository.getLatest().getMeeting_id()));
        notificationRepository.create(notification);


        List<ScsMembers> scsMembersList = scsMembersRepository.findAll();
        for (ScsMembers  member: scsMembersList) {
            if(!Objects.equals(curr_member.getVerticalmemberbelongs(), member.getVerticalmemberbelongs()))continue;
            Unread unread = new Unread();
            unread.setUser_id(String.valueOf(scsMembersRepository.getScsUserId(member.getMemberId()).getUser_id()));
            unread.setNotification_id(notificationRepository.getLatest().getNotification_id());
            unreadRepository.create(unread);
        }

        return "redirect:/team_meeting";
    }
    @GetMapping("/{meetingId}")
    public String get(@PathVariable Integer meetingId, final Model model){
        model.addAttribute("team_meeting",teamMeetingRepository.get(meetingId));
        User user = securityService.findLoggedInUser();
        Student student = studentRepository.get(Integer.parseInt(user.getStud_id()));
        ScsMembers scsMembers = scsMembersRepository.get(student.getScsmembership_id());
        model.addAttribute("organiser",student.getName());
        System.out.println(scsMembers.toString());
        Vertical vertical = verticalRepository.get(Integer.parseInt(scsMembers.getVerticalmemberbelongs()));
        model.addAttribute("vertical",vertical.getName_of_vertical());
        return "team_meeting/all_team_meeting";
    }


    @GetMapping("/edit/{meetingID}")
    public String edit(@PathVariable final Integer meetingID, final Model model) {
        model.addAttribute("team_meeting", teamMeetingRepository.get(meetingID));
        return "team_meeting/edit";
    }

    @PostMapping("/edit/{meetingID}")
    public String edit(@PathVariable final Integer meetingID,
                       @ModelAttribute("team_meeting") @Valid final TeamMeeting teamMeeting,
                       final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "team_meeting/edit";
        }
        teamMeetingRepository.update(meetingID, teamMeeting);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("team_meeting.update.success"));
        return "redirect:/team_meeting";
    }

    @PostMapping("/delete/{meetingID}")
    public String delete(@PathVariable final Integer meetingID,
                         final RedirectAttributes redirectAttributes) {
        final String referencedWarning = teamMeetingRepository.getReferencedWarning(meetingID);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            teamMeetingRepository.delete(meetingID);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("team_meeting.delete.success"));
        }
        return "redirect:/team_meeting";
    }

}
