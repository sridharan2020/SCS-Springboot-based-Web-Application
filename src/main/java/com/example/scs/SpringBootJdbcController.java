package com.example.scs;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@Controller
public class SpringBootJdbcController {
    @Autowired
    JdbcTemplate jdbc;
    @RequestMapping("/insert")
    public String index(){
        jdbc.execute("insert into student(name,email)values('javatpoint','java@javatpoint.com')");
        return"data inserted Successfully";
    }
}