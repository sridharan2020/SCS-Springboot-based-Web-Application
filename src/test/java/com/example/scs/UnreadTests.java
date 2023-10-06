package com.example.scs;

import com.example.scs.model.Unread;

import com.example.scs.repos.UnreadRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
public class  UnreadTests{

    @Autowired
    UnreadRepository unreadRepository;

    @Test
    void createUnread(){
        Unread unread = new Unread();
        unread.setUnread_id("1");
        unread.setNotification_id(1);
        unread.setUser_id("1");
        unreadRepository.create(unread);
    }
    @Test
    void testGetUnread(){
        unreadRepository.get(1);
    }
    @Test
    void testUpdateUnread(){
        Unread unread = new Unread();
        unread.setUnread_id("1");
        unread.setNotification_id(2);
        unread.setUser_id("1");
        unreadRepository.update(Integer.parseInt(unread.getUnread_id()),unread);
    }
    @Test
    void testDeleteUnread(){
        unreadRepository.delete(1);
    }

    @Test
    void testMarkAsRead(){
        unreadRepository.mark_as_read(21,3);
    }
//    @Test
//    void getUnreads(){
//        List<Unread>unreadList = unreadRepository.getUnreads("ADMIN");
//        System.out.println(UnreadList.size());
//    }
}
