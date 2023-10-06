package com.example.scs;
import com.example.scs.model.Vertical;
import com.example.scs.repos.VerticalRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class VerticalTests{

    @Autowired
    VerticalRepository verticalRepository;

    @Test
    void testGetApplication(){
        Vertical vertical = verticalRepository.getVertical("INTERNATIONAL_EXCHANGE");
        System.out.println(vertical.toString());
    }

    @Test
    void testGet(){
        Vertical vertical = verticalRepository.get(5);
        System.out.println(vertical.toString());
    }

}