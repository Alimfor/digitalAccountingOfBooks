package com.gaziyev.spring.dao;

import com.gaziyev.spring.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM person",
                new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE id = ?",new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person (fullname,birthYear) " +
                                 "VALUES(?,?)",
                                person.getFullName(),
                                person.getBirthYear());
    }

    public void update(int id,Person person) {
        jdbcTemplate.update("UPDATE person " +
                                 "SET fullname = ?, birthyear = ? " +
                                 "WHERE id = ?",
                                person.getFullName(),
                                person.getBirthYear(),id);
    }

    public void deleted(int id) {
        jdbcTemplate.update("DELETE FROM person " +
                                "WHERE id = ?",id);
    }
}
