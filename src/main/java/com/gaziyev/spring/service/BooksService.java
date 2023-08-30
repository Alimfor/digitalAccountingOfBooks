package com.gaziyev.spring.service;

import com.gaziyev.spring.model.Book;
import com.gaziyev.spring.model.Person;
import com.gaziyev.spring.repository.BooksRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    public List<Book> findAll(int pageNum, int size, boolean sort_by_year) {
        Page<Book> page = sort_by_year
                ? booksRepository.findAll(PageRequest.of(pageNum, size, Sort.by("year")))
                : booksRepository.findAll(PageRequest.of(pageNum, size));
        return page.getContent();
    }

    public Book findOne(int id) {
        Optional<Book> foundBook = booksRepository.findById(id);
        return foundBook.orElse(null);
    }

    public List<Book> findByNameStartingWith(String bookName, int page, int size) {
        return booksRepository.findByNameStartingWith(bookName, PageRequest.of(
                        page, size,
                        Sort.by("name", "year")
                )
        );
    }

    @Transactional
    public void save(Book book) {
        whoAndWhenCreated(book);
        booksRepository.save(book);
    }

    @Transactional
    public void removePersonFromBook(int id) {
        booksRepository.removePersonFromBook(id);
    }

    @Transactional
    public void update(Book updatedBook) {
        Book originBook = findOne(updatedBook.getId());
        originBook.setName(updatedBook.getName());
        originBook.setAuthor(updatedBook.getAuthor());
        originBook.setYear(updatedBook.getYear());

        theLastUpdate(originBook);
        booksRepository.save(originBook);
    }

    @Transactional
    public void updateBookPersonRelationship(int bookId, int personId) {
        Book book = findOne(bookId);
        Person person = peopleService.findOne(personId);

        if (book != null && person != null) {
            book.setIssued(LocalDateTime.now());
            booksRepository.updateBookPersonRelationship(bookId, personId);
        }
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    private void whoAndWhenCreated(Book book) {
        book.setCreatedWho("ADMIN");
        book.setCreatedAt(LocalDateTime.now());
        theLastUpdate(book);
    }

    private void theLastUpdate(Book book) {
        book.setUpdatedAt(LocalDateTime.now());
    }
}
