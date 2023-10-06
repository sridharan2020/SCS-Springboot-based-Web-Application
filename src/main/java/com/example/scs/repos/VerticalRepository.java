package com.example.scs.repos;

import com.example.scs.model.Vertical;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class VerticalRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public Vertical getVertical(String vertical_name){
        String sql = "SELECT * FROM vertical WHERE name_of_vertical = ?";
        Object[] args = {vertical_name};
        return jdbcTemplate.queryForObject(sql, args,
                BeanPropertyRowMapper.newInstance(Vertical.class));
    }
    public Vertical get(Integer vertical_id){
        String sql = "SELECT * FROM vertical WHERE vertical_id = ?";
        Object[] args = {vertical_id};
        return jdbcTemplate.queryForObject(sql, args,
                BeanPropertyRowMapper.newInstance(Vertical.class));
    }
}
