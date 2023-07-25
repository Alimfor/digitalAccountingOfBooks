package com.gaziyev.spring.services;

import com.gaziyev.spring.models.Book;
import com.gaziyev.spring.models.Person;
import com.gaziyev.spring.repositories.BooksRepository;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;
    private final PeopleService peopleService;

    public BooksService(BooksRepository booksRepository, PeopleService peopleService) {
        this.booksRepository = booksRepository;
        this.peopleService = peopleService;
    }

    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    public Book findOne(int id) {
        Optional<Book> foundBook = booksRepository.findById(id);
        return foundBook.orElse(null);
    }

    public List<Book> findBooksByPerson(int id) {
        return booksRepository.findBooksByPerson(peopleService.findOne(id));
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void removePersonFromBook(int id) {
        booksRepository.removePersonFromBook(id);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setId(id);
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void updateBookPersonRelationship(int bookId,int personId) {
        booksRepository.updateBookPersonRelationship(bookId,personId);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }
}
