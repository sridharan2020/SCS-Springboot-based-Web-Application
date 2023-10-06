package com.example.scs.repos;
import com.example.scs.model.EventParticipation;
import com.example.scs.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User findByEmail(String email) {
        try {
            String sql = "SELECT * FROM user WHERE email = ?";
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), email);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public User findByResetPasswordToken(String reset_token) {
        try {
            String sql = "SELECT * FROM user WHERE reset_password_token = ?";
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), reset_token);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public User findByRollno(Integer rollno) {
        try {
            String sql = "SELECT * FROM user WHERE stud_id = ?";
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), rollno);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<User> findAll() {
        String sql = "SELECT * FROM user";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(User.class));
    }

    public void save(User user) {
        if (user.getStud_id() != null) {
            String sql = "INSERT INTO user(email,  role, password, " +
                    "stud_id) VALUES (?, ?, ?, ?)";
            jdbcTemplate.update(sql, user.getEmail(), user.getRole(), user.getPassword(),
                    user.getStud_id());
        } else if (user.getFac_id() != null) {
            String sql = "INSERT INTO user(email, role, password, " +
                    "fac_id) VALUES (?, ?, ?, ?)";
            jdbcTemplate.update(sql, user.getEmail(), user.getRole(), user.getPassword(),
                    user.getFac_id());
        }
    }

    public User get(Integer user_id) {
        String sql = "SELECT * FROM user WHERE (user_id = ?)";
        Object[] args = {user_id};
        return jdbcTemplate.queryForObject(sql, args,
                BeanPropertyRowMapper.newInstance(User.class));
    }

    public String getFacultyEmail(Integer facID) {
        String sql = "SELECT * FROM user WHERE (fac_id = ?)";
        Object[] args = {facID};
        return Objects.requireNonNull(jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(User.class), args)).getEmail();
    }

    public String getStudentEmail(Integer studID) {
        String sql = "SELECT * FROM user WHERE (stud_id = ?)";
        Object[] args = {studID};
        return Objects.requireNonNull(jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(User.class), args)).getEmail();
    }

    public User getByStudent(Integer stud_id) {
        String sql = "SELECT * FROM user WHERE (stud_id = ?)";
        Object[] args = {stud_id};
        return jdbcTemplate.queryForObject(sql, args,
                BeanPropertyRowMapper.newInstance(User.class));
    }

    public User getAdmin(String admin) {
        String sql = "SELECT * FROM user WHERE (role=?)";
        Object[] args = {admin};
        return jdbcTemplate.queryForObject(sql, args, BeanPropertyRowMapper.newInstance(User.class));
    }

    public User getLatest() {
        try {
            String sql = "SELECT * FROM user order by user_id desc limit 1";
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), new Object[]{});
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    public void update(User user) {
        String sql = "update user set email= ? ," +
                " role = ?, password = ? where (user_id = ?)";
        jdbcTemplate.update(sql, user.getEmail(), user.getRole(),
                user.getPassword(), user.getUser_id());
    }

    //    public void updateRole(String role){
//
//    }
    public void update2(Integer user_id, User user) {
        String sql = "UPDATE user SET email= ?" +
                ", role = ?, password = ? WHERE (user_id=?)";
        jdbcTemplate.update(sql, user.getEmail(), user.getRole(),
                user.getPassword(), user_id);

    }

    public void update_token(User user) {
        String sql = "update user set reset_password_token = ?, password = ? where (user_id = ?)";
        jdbcTemplate.update(sql, user.getReset_password_token(), user.getPassword(), user.getUser_id());
    }
}