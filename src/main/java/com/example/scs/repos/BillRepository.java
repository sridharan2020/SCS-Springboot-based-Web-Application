package com.example.scs.repos;

import com.example.scs.model.Bill;
import com.example.scs.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BillRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<Bill> findAll() {
        String sql = "SELECT * FROM bills";

        return jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Bill.class));
    }

    public void create(Bill bill) {
        String sql = "INSERT INTO bills(billid,title, date_of_billing,purpose,status, member_id, " +
                "amount) VALUES (?, ?, ?, ?, ?, ?,? )";
        jdbcTemplate.update(sql, bill.getBillid(),bill.getTitle(), bill.getDate_of_billing(),bill.getPurpose(), bill.getStatus(), bill.getMember_id(),
                bill.getAmount());
    }


    public Bill get(Integer billID) {
        String sql = "SELECT * FROM bills WHERE billid = ?";
        Object[] args = {billID};
        return jdbcTemplate.queryForObject(sql, args,
                BeanPropertyRowMapper.newInstance(Bill.class));
    }

    public void update(Integer billID, Bill bill) {
        String sql = "UPDATE bills SET date_of_billing=?,title=?, purpose=?, status=?, amount=? WHERE (billid=?)";
        jdbcTemplate.update(sql,bill.getDate_of_billing(),bill.getTitle(),bill.getPurpose(),bill.getStatus(),bill.getAmount() , billID);
    }
    public Bill getLatest(){
        try {
            String sql = "SELECT * FROM bills order by billid desc limit 1";
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Bill.class), new Object[] {  });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    public String getReferencedWarning(Integer billID) {
        return null;
    }

    public void delete(Integer billID) {
        String sql = "DELETE FROM bills WHERE (billid = ?)";
        jdbcTemplate.update(sql, billID);
    }
}
