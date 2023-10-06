package com.example.scs.repos;

import com.example.scs.model.Bill;
import com.example.scs.model.MonthlyReports;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MonthlyReportsRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<MonthlyReports> findAll() {
        String sql = "SELECT * FROM monthly_reports";

        return jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(MonthlyReports.class));
    }

    public void create(MonthlyReports monthlyReports) {
        String sql = "INSERT INTO monthly_reports(report_id,title, compiled_on, from_date, report_description, to_date, student_compiles_reports_id,status) VALUES (?, ?, ?, ?, ?, ?,? ,?)";
        jdbcTemplate.update(sql, monthlyReports.getReport_id(),monthlyReports.getTitle(),monthlyReports.getCompiled_on(),monthlyReports.getFrom_date(),monthlyReports.getReport_description(),monthlyReports.getTo_date(),monthlyReports.getStudent_compiles_reports_id()
        ,monthlyReports.getStatus()
        );
    }


    public MonthlyReports get(Integer monthlyReportsID) {
        String sql = "SELECT * FROM monthly_reports WHERE report_id = ?";
        Object[] args = {monthlyReportsID};
        return jdbcTemplate.queryForObject(sql, args,
                BeanPropertyRowMapper.newInstance(MonthlyReports.class));
    }

    public void update(Integer monthlyReportsID, MonthlyReports monthlyReports) {
        String sql = "UPDATE monthly_reports SET compiled_on=?,title=?, from_date=?, report_description=?, to_date=?, status=?  WHERE (report_id=?)";
        jdbcTemplate.update(sql,monthlyReports.getCompiled_on(),monthlyReports.getTitle(),monthlyReports.getFrom_date(),monthlyReports.getReport_description(),monthlyReports.getTo_date(), monthlyReports.getStatus(), monthlyReportsID);
    }
    public MonthlyReports getLatest(){
        try {
            String sql = "SELECT * FROM monthly_reports order by report_id desc limit 1";
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(MonthlyReports.class), new Object[] {  });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    public String getReferencedWarning(Integer monthlyReportsID) {
        return null;
    }

    public void delete(Integer monthlyReportsID) {
        String sql = "DELETE FROM monthly_reports WHERE (report_id = ?)";
        jdbcTemplate.update(sql, monthlyReportsID);
    }
}
