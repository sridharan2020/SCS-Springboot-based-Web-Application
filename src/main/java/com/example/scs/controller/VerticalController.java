package com.example.scs.controller;

import com.example.scs.model.ScsMembers;
import com.example.scs.model.Student;
import com.example.scs.repos.ScsMembersRepository;
import com.example.scs.repos.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/verticals")
public class VerticalController {

    @Autowired
    ScsMembersRepository scsMembersRepository;
    @Autowired
    StudentRepository studentRepository;

    @GetMapping
    public String list(final Model model) {
        return "vertical/list";
    }

    @GetMapping("/{verticalID}")
    public String vertical_page(@PathVariable final Integer verticalID, final Model model) {
        List<ScsMembers> scsMembersList = scsMembersRepository.getByVertical(verticalID);
        Map<ScsMembers, Student> member_map = new HashMap<>();

        for(ScsMembers member1 : scsMembersList)
        {
            Student student = studentRepository.getScsMembers(member1.getMemberId());
            member_map.put(member1, student);
        }

        model.addAttribute("sCSMemberss", member_map);

        return "vertical/vertical" + String.valueOf(verticalID);
    }

}
