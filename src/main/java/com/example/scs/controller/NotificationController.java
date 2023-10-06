package com.example.scs.controller;

import com.example.scs.model.*;
import com.example.scs.repos.*;
import com.example.scs.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Controller
public class NotificationController {

    @Autowired
    SecurityService securityService;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    UnreadRepository unreadRepository;
    @Autowired
    EventParticipationRepository eventParticipationRepository;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    InitiativeParticipationRepository initiativeParticipationRepository;
    @Autowired
    InititativesRepository inititativesRepository;

    @GetMapping("/notification")
    public String notification_getmapping(Model model) throws ParseException {
        User user = securityService.findLoggedInUser();
        List<EventParticipation> eventParticipations = eventParticipationRepository.getByStudent(Integer.parseInt(user.getStud_id()));
        for(EventParticipation eventParticipation : eventParticipations)
        {
            if(eventParticipation.getIs_notified() == 0)
            {
                Event event = eventRepository.get(Integer.parseInt(eventParticipation.getEvent_id()));
                Notification notification = notificationRepository.getEvent(event.getEventID());

                System.out.println(event.toString());

                LocalDateTime current_time = LocalDateTime.now();
                LocalDateTime event_time = notification.getTime();
                LocalDateTime temp_time = event_time.minusMinutes(30);
                if((current_time.isAfter(temp_time)) & (current_time.isBefore(event_time)))
                {
                    Unread unread = new Unread();
                    unread.setUser_id(user.getUser_id().toString());
                    unread.setNotification_id(notification.getNotification_id());
                    unreadRepository.create(unread);
                }
                eventParticipation.setIs_notified(1);
                eventParticipationRepository.update(eventParticipation);
            }
        }

        List<InitiativeParticipation> initiativeParticipations = initiativeParticipationRepository.getByStudent(Integer.parseInt(user.getStud_id()));
        for(InitiativeParticipation initiativeParticipation1 : initiativeParticipations)
        {
            System.out.println(initiativeParticipation1.toString());
            if(initiativeParticipation1.getIs_notified() == 0)
            {
                Inititatives inititative = inititativesRepository.get(initiativeParticipation1.getInitiative_id());
                Notification notification = notificationRepository.getInitiative(inititative.getInitiativesId());
                System.out.println(notification.toString());
                LocalDateTime current_time = LocalDateTime.now();
                LocalDateTime event_time = notification.getTime();
//                System.out.println(event_time);
//                System.out.println(DAYS.between(current_time, event_time));
                event_time = event_time.plusDays(DAYS.between(event_time, current_time)+1);
//                System.out.println(event_time);
                LocalDateTime temp_time = event_time.minusMinutes(30);

//                String fmt = "hh:mm";
//                DateFormat df = new SimpleDateFormat();
//                Date temp_time1 = df.parse(df.format(temp_time));
//                Date event_time1 = df.parse(df.format(event_time));
//                Date current_time1 = df.parse(df.format(current_time));

//                System.out.println(temp_time);
//                System.out.println(event_time);
//                System.out.println(current_time);
//
//                System.out.println(current_time.isAfter(temp_time));
//                System.out.println(current_time.isBefore(event_time));

                if((current_time.isAfter(temp_time)) & (current_time.isBefore(event_time)))
                {
                    Unread unread = new Unread();
                    unread.setUser_id(user.getUser_id().toString());
                    unread.setNotification_id(notification.getNotification_id());
                    unreadRepository.create(unread);
                }
                initiativeParticipation1.setIs_notified(1);
                initiativeParticipationRepository.update(initiativeParticipation1);
            }
        }

        //        if(user.getRole().equals("ADMIN")){

        List<Unread>unreadList = unreadRepository.getUnread(user.getUser_id().intValue());
        List<Notification>notificationList = new ArrayList<>();
        for(Unread unread:unreadList){
            notificationList.add(notificationRepository.get(unread.getNotification_id()));
        }
         model.addAttribute("notificationList",notificationList);
        return "notification/notification";
//        }
//        else if(user.getRole().equals("SCS_MEMBER")){
//            ;
//        }
//        return "L for u";
    }
}
