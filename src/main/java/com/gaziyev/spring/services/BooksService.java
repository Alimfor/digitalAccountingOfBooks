package com.gaziyev.spring.services;

import com.gaziyev.spring.models.Book;
import com.gaziyev.spring.models.Person;
import com.gaziyev.spring.repositories.BooksRepository;
<<<<<<< HEAD
import org.hibernate.Hibernate;
=======
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
>>>>>>> ecacc1e (final Spring data JPA project)
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
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

<<<<<<< HEAD
    public List<Book> findAll() {
        return booksRepository.findAll();
=======
    public List<Book> findAll(int pageNum,int size, boolean sort_by_year) {
        Page<Book> page = sort_by_year
                                ? booksRepository.findAll( PageRequest.of(pageNum,size,Sort.by("year")))
                                : booksRepository.findAll( PageRequest.of(pageNum,size));
        return page.getContent();
>>>>>>> ecacc1e (final Spring data JPA project)
    }

    public Book findOne(int id) {
        Optional<Book> foundBook = booksRepository.findById(id);
        return foundBook.orElse(null);
    }

    public List<Book> findByNameStartingWith(String bookName,int page, int size) {
        return booksRepository.findByNameStartingWith(bookName,PageRequest.of(
                                                            page,size,
                                                            Sort.by("name","year")
                                                        )
                                                );
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
        Book book = findOne(bookId);
        Person person = peopleService.findOne(personId);

        if (book != null && person != null) {
            book.setIssued(new Date());
            booksRepository.updateBookPersonRelationship(bookId, personId);
        }
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }
}
