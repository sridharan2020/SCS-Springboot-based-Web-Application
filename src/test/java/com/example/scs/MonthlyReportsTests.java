package com.example.scs;

import com.example.scs.model.Bill;
import com.example.scs.model.MonthlyReports;
import com.example.scs.repos.BillRepository;
import com.example.scs.repos.MonthlyReportsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class MonthlyReportsTests {

    @Autowired
    MonthlyReportsRepository monthlyReportsRepository;

    @Test
    void createmonthlyReports(){
        MonthlyReports monthlyReports = new MonthlyReports();
        monthlyReports.setReport_id(1);
        monthlyReports.setReport_description("jiangly");
        monthlyReportsRepository.create(monthlyReports);
    }
    @Test
    void testGetmonthlyReports(){
        monthlyReportsRepository.get(1);
    }
    @Test
    void testUpdatemonthlyReports(){
        MonthlyReports monthlyReports = new MonthlyReports();
        monthlyReports.setReport_id(1);
        monthlyReports.setReport_description("tourist");
        monthlyReportsRepository.update(monthlyReports.getReport_id(),monthlyReports);
    }
    @Test
    void testDeletemonthlyReports(){
        monthlyReportsRepository.delete(1);
    }

    @Test
    void testGetLatest(){
        System.out.println(monthlyReportsRepository.getLatest());
    }
}
