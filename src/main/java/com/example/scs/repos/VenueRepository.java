package com.example.scs.repos;

import com.example.scs.model.Faculty;
import com.example.scs.model.Venue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VenueRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Venue> findAll() {
        String sql = "SELECT * FROM venue";

        return jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(Venue.class));
    }

    public void create(Venue venue) {
        String sql = "INSERT INTO venue(venueid, capacity, contactnumber, contactperson, location, " +
                "venue_name) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, venue.getVenueID(), venue.getCapacity(), venue.getContactnumber(),
                venue.getContactperson(), venue.getLocation(), venue.getVenueName());
    }

    public Venue get(Integer venue_id){
        try {
            String sql = "SELECT * FROM venue WHERE venueid = ?";
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Venue.class), venue_id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void update(Integer venue_id, Venue venue) {
        String sql = "update venue set capacity = ?, contactnumber = ?, contactperson = ?," +
                " location = ?, venue_name = ? where (venueid = ?)";

        jdbcTemplate.update(sql, venue.getCapacity(), venue.getContactnumber(),
                venue.getContactperson(), venue.getLocation(), venue.getVenueName(), venue_id);
    }

    public String getReferencedWarning(Integer faculty_id) {
        return null;
    }

    public void delete(Integer venue_id) {
        String sql = "DELETE FROM venue WHERE venueid = ?";
        jdbcTemplate.update(sql, venue_id);
    }
}
