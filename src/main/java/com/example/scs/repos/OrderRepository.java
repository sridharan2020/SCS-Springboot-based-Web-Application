package com.example.scs.repos;

import com.example.scs.model.MyOrder;
import com.example.scs.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public List<MyOrder> findAll() {
        String sql = "SELECT * FROM scs_jdbc.my_order";

        return jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(MyOrder.class));
    }

    public void create(MyOrder my_order) {
        String sql = "INSERT INTO scs_jdbc.my_order(my_order_id,orderid,amount,recepit,status,user_id,paymentid) VALUES (?,?, ?, ?, ?,?,?)";
        jdbcTemplate.update(sql, my_order.getMyOrderId(),my_order.getOrderId(),my_order.getAmount(),my_order.getReceipt(),my_order.getStatus(),my_order.getUser_id(),my_order.getPaymentId());
    }


    public MyOrder get(Long myOrderId) {
        String sql = "SELECT * FROM scs_jdbc.my_order WHERE my_order_id = ?";
        Object[] args = {myOrderId};
        return jdbcTemplate.queryForObject(sql, args,
                BeanPropertyRowMapper.newInstance(MyOrder.class));
    }

    public void update(Long myOrderId, MyOrder my_order) {
        String sql = "UPDATE scs_jdbc.my_order SET my_order_id=?,orderId=?,amount=?,receipt=?,status=?,user_id=?,paymentId=? WHERE my_order_id=?";
        jdbcTemplate.update(sql,my_order.getMyOrderId(),my_order.getOrderId(),my_order.getAmount(), my_order.getReceipt(),my_order.getStatus(),my_order.getUser_id(),my_order.getPaymentId(),myOrderId);
    }

    public void delete(Long myOrderId) {
        String sql = "DELETE FROM scs_jdbc.my_order WHERE (my_order_id = ?)";
        jdbcTemplate.update(sql, myOrderId);
    }
}
