package com.example.scs;

import com.example.scs.model.Application;
import com.example.scs.model.Bill;
import com.example.scs.repos.ApplicationRepository;
import com.example.scs.repos.BillRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//public class ApplicationTests {
//}
@SpringBootTest
public class ApplicationTests {

    @Autowired

    ApplicationRepository applicationRepository;

//    @Test
//    void createApplication(){
//        Application application = new Application();
//        application.setApplication_id(1);
//        application.setProgram("jiangly");
//        applicationRepository.create(application);
//    }
    @Test
    void testGetApplication(){
        applicationRepository.get(1);
    }
//    @Test
//    void testUpdateApplication(){
//        Application application = new Application();
//        application.setApplication_id(1);
//        application.setProgram("tourist");
//        applicationRepository.update(application.getApplication_id(),application);
//    }
    @Test
    void testDeleteApplication(){
        applicationRepository.delete(1);
    }

    @Test
    void testGetLatest(){
        System.out.println(applicationRepository.getLatest());
    }
}