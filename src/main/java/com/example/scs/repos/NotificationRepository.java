package com.example.scs.repos;


import com.example.scs.model.Event;
import com.example.scs.model.Notification;
import com.example.scs.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.relational.core.sql.Not;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NotificationRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public List<Notification> findAll() {
        String sql = "SELECT * FROM notifications";

        return jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Notification.class));
    }

    public void create(Notification notification) {
        String sql = "INSERT INTO notifications(notification_id, type, url_id, time) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, notification.getNotification_id(),notification.getType(),notification.getUrl_id(),notification.getTime());
    }


    public Notification get(Integer notificationID) {
        String sql = "SELECT * FROM notifications WHERE notification_id = ?";
        Object[] args = {notificationID};
        return jdbcTemplate.queryForObject(sql, args,
                BeanPropertyRowMapper.newInstance(Notification.class));
    }

    public Notification getEvent(Integer eventID) {
        try {
            String sql = "SELECT * FROM notifications WHERE (type = 'events/student') and (url_id = ?)";
            Object[] args = {eventID};
            return jdbcTemplate.queryForObject(sql, args,
                    BeanPropertyRowMapper.newInstance(Notification.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Notification getInitiative(Integer initiativeID) {
        try {
            String sql = "SELECT * FROM notifications WHERE (type = 'initiatives/student') and (url_id = ?)";
            Object[] args = {initiativeID};
            return jdbcTemplate.queryForObject(sql, args,
                    BeanPropertyRowMapper.newInstance(Notification.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

//    public List<Notification>getNotifications(String role){
//        String sql = "SELECT * FROM notifications WHERE role = ?";
//        Object[] args = {role};
//        return jdbcTemplate.query(sql,args,BeanPropertyRowMapper.newInstance(Notification.class));
//    }
    public Notification getLatest(){
        try {
            String sql = "SELECT * FROM notifications order by notification_id desc limit 1";
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Notification.class), new Object[] {  });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void update(Integer notificationID, Notification notification) {
        String sql = "UPDATE notifications SET type=?, url_id=?, time=? WHERE (notification_id=?)";
        jdbcTemplate.update(sql,notification.getType(),notification.getUrl_id(),notification.getTime(), notificationID);
    }

    public String getReferencedWarning(Integer notificationID) {
        return null;
    }

    public void delete(Integer notificationID) {
        String sql = "DELETE FROM notifications WHERE (notification_id = ?)";
        jdbcTemplate.update(sql, notificationID);
    }
}
