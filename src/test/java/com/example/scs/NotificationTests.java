package com.example.scs;

//public class NotificationTests {
//}


import com.example.scs.model.Faculty;
import com.example.scs.model.Notification;
import com.example.scs.repos.FacultyRepository;
import com.example.scs.repos.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.relational.core.sql.Not;

import java.util.List;


@SpringBootTest
public class  NotificationTests{

    @Autowired

    NotificationRepository notificationRepository;
    @Test
    void createnotification(){
        Notification notification = new Notification();
        notification.setNotification_id(1);
        notificationRepository.create(notification);
    }
    @Test
    void testGetnotification(){
        notificationRepository.get(1);
    }
    @Test
    void testUpdatenotification(){
        Notification notification = new Notification();
        notification.setNotification_id(1);
        notification.setType("tourist");
        notificationRepository.update(notification.getNotification_id(),notification);
    }
    @Test
    void testDeletenotification(){
        notificationRepository.delete(1);
    }


//    @Test
//    void getNotifications(){
//        List<Notification>notificationList = notificationRepository.getNotifications("ADMIN");
//        System.out.println(notificationList.size());
//    }
}
