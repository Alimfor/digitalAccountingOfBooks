package com.gaziyev.spring.dao;

import com.gaziyev.spring.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM book",
                new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE id = ?",
                new Object[]{id},new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO book (name,author,year) " +
                                "VALUES(?,?,?)",
                            book.getName(),
                            book.getAuthor(),
                            book.getYear());
    }

    public void update(int id) {
        jdbcTemplate.update("UPDATE book " +
                                 "SET personId = NULL " +
                                 "WHERE id = ?",id);
    }

    public void update(int id, Book upBook) {
        jdbcTemplate.update("UPDATE book " +
                                 "SET name = ?, author = ?, year = ? " +
                                 "WHERE id = ?",
                        upBook.getName(),
                        upBook.getAuthor(),
                        upBook.getYear(),id);
    }

    public void update(int bookId,int personId){
        jdbcTemplate.update("UPDATE book " +
                                 "SET personId = ? " +
                                 "WHERE id = ?",personId,bookId);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM book " +
                                 "WHERE id = ?",id);
    }

    public List<Book> getBookName(int id) {
        return jdbcTemplate.query("SELECT name FROM book WHERE personId = ?",new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class));
    }
}
