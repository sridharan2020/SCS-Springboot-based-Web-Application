package com.example.scs;

import com.example.scs.model.Bill;
import com.example.scs.repos.BillRepository;
//import org.junit.Test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class BillTests {

    @Autowired

    BillRepository billRepository;

    @Test
     void createBill(){
        Bill bill = new Bill();
        bill.setBillid(1);
        bill.setPurpose("tourist");
        billRepository.create(bill);
    }
    @Test
     void testGetbill(){
        billRepository.get(1);
    }
    @Test
     void testUpdateBill(){
        Bill bill = new Bill();
        bill.setBillid(1);
        bill.setPurpose("jiangly");
        billRepository.update(bill.getBillid(),bill);
    }
    @Test
     void testDeletebill(){
        billRepository.delete(1);
    }

    @Test
    void testGetLatest(){
        System.out.println(billRepository.getLatest());
    }
}
