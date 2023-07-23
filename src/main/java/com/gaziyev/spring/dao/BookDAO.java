package com.gaziyev.spring.dao;

import com.gaziyev.spring.models.Book;
import com.gaziyev.spring.models.Person;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDAO {
    private final SessionFactory sessionFactory;

    @Autowired
    public BookDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public List<Book> index() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT b FROM Book b", Book.class)
                .getResultList();
    }

    @Transactional
    public Book show(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Book.class,id);
    }

    @Transactional
    public void save(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(book);
    }

    @Transactional
    public void update(int id) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class,id);

        if (book != null) {
            book.getPerson().getBooks().remove(book);
            book.setPerson(null);
        }
    }

    @Transactional
    public void update(int id, Book upBook) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class,id);

        if (book != null) {
            book.setName(upBook.getName());
            book.setAuthor(upBook.getAuthor());
            book.setYear(upBook.getYear());
            session.merge(book);
        }

    }

    @Transactional
    public void update(int bookId,int personId){
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class,bookId);
        Person person = session.get(Person.class,personId);

        if (book != null && person != null) {
            if (book.getPerson() != null)
                book.getPerson().getBooks().remove(book);

            book.setPerson(person);
            person.getBooks().add(book);
        }
    }

    @Transactional
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(Book.class,id));
    }

    @Transactional
    public List<Book> getAllBooks(int personId) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Person.class,personId)
                .getBooks();
    }
}
