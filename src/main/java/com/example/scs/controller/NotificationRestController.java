package com.example.scs.controller;

import com.example.scs.model.Unread;
import com.example.scs.model.User;
import com.example.scs.repos.NotificationRepository;
import com.example.scs.repos.UnreadRepository;
import com.example.scs.repos.UserRepository;
import com.example.scs.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
public class NotificationRestController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    SecurityService securityService;

    @Autowired
    UnreadRepository unreadRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @PostMapping("/notification/markasread")
    public void markasread(@RequestParam Map<String,String>body){
        Integer notification_id = Integer.parseInt(body.get("notification_id"));
        User user =  securityService.findLoggedInUser();
        unreadRepository.mark_as_read(user.getUser_id().intValue(),notification_id);
        List<Unread> unreadList = unreadRepository.getUnreadWithType(Integer.parseInt(body.get("notification_id")));
        if(unreadList.isEmpty()){
            notificationRepository.delete(Integer.parseInt(body.get("notification_id")));
        }
    }
}
