package com.example.scs;


import com.example.scs.model.ScsMembers;
import com.example.scs.repos.ScsMembersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
public class ScsMembersTests {

    @Autowired
    ScsMembersRepository scsMembersRepository;
    @Test
    void createScsMembers(){
        ScsMembers ScsMembers = new ScsMembers();
        ScsMembers.setMemberId(1);
        ScsMembers.setCurrentPosition("tourist");
        scsMembersRepository.create(ScsMembers);
    }

    @Test
    void testFindAll()
    {
        for(ScsMembers scsMember : scsMembersRepository.findAll())
        {
            System.out.println(scsMember.toString());
        }
    }

    @Test
    void testGetScsMembers(){
        for(ScsMembers scsMember : scsMembersRepository.getByVertical(1))
        {
            System.out.println(scsMember.toString());
        }
    }
    @Test
    void testUpdateScsMembers(){
        ScsMembers ScsMembers = new ScsMembers();
        ScsMembers.setMemberId(1);
        ScsMembers.setCurrentPosition("jiangly");
        scsMembersRepository.update(ScsMembers.getMemberId(),ScsMembers);
    }
    @Test
    void testDeleteScsMembers(){
        System.out.println(scsMembersRepository.get(16));
    }

    @Test
    void testGetScsUserId(){
        Integer member_id = 8;
        System.out.println(scsMembersRepository.getScsUserId(member_id).toString());

    }
}
