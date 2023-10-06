package com.example.scs.repos;

import com.example.scs.model.Notification;
import com.example.scs.model.Unread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class UnreadRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public List<Unread> findAll() {
        String sql = "SELECT * FROM unread";

        return jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Unread.class));
    }

    public void create(Unread unread) {
        String sql = "INSERT INTO unread(unread_id,user_id,notification_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql,unread.getUnread_id(),unread.getUser_id(),unread.getNotification_id());
    }


    public Unread get(Integer unreadID) {
        String sql = "SELECT * FROM unread WHERE unread_id = ?";
        Object[] args = {unreadID};
        return jdbcTemplate.queryForObject(sql, args,
                BeanPropertyRowMapper.newInstance(Unread.class));
    }

    public List<Unread>getUnread(Integer user_id){
        String sql = "SELECT * FROM unread WHERE user_id=?";
        Object[] args = {user_id};
        return jdbcTemplate.query(sql,args,BeanPropertyRowMapper.newInstance(Unread.class));
    }

    public List<Unread>getUnreadWithType(Integer type_id){
         String sql = "SELECT * FROM unread WHERE notification_id=?";
        Object[] args = {type_id};
        return jdbcTemplate.query(sql,args,BeanPropertyRowMapper.newInstance(Unread.class));
    }

    public void update(Integer unreadID, Unread unread) {
        String sql = "UPDATE unread SET user_id=?, notification_id=? WHERE (unread_id=?)";
        jdbcTemplate.update(sql,unread.getUser_id(),unread.getNotification_id(), unreadID);
    }

    public String getReferencedWarning(Integer unreadID) {
        return null;
    }

    public void delete(Integer unreadID) {
        String sql = "DELETE FROM unread WHERE (unread_id = ?)";
        jdbcTemplate.update(sql, unreadID);
    }

    public void mark_as_read(Integer user_id, Integer notification_id){
        String sql = "DELETE  FROM unread WHERE (user_id=?) AND (notification_id=?)";
//        Object[] args = {user_id,notification_id};
        jdbcTemplate.update(sql,user_id,notification_id);
    }

}
