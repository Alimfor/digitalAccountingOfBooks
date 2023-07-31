package com.gaziyev.spring.services;

import com.gaziyev.spring.models.Book;
import com.gaziyev.spring.models.Person;
import com.gaziyev.spring.repositories.PeopleRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

<<<<<<< HEAD
=======
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
>>>>>>> ecacc1e (final Spring data JPA project)
import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;
    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findOne(int id) {
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElse(null);
    }
    private LocalDate getIssuedDateAsLocalDate(Book book) {
        return book.getIssued().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
    public boolean overdue(Book book) {
        LocalDate currentDate = LocalDate.now();
        return getIssuedDateAsLocalDate(book).plusDays(10).isBefore(currentDate);
    }

    public List<Book> getBooksByPersonId(int id) {
        Optional<Person> foundPerson = peopleRepository.findById(id);

        if (foundPerson.isPresent()) {
            Hibernate.initialize(foundPerson.get().getBooks());
            return foundPerson.get().getBooks();
        }
        return Collections.emptyList();
    }

    public Optional<Person> getPersonByFullName(String fullName) {
        return peopleRepository.findByFullName(fullName);
    }

    public Long getIdByFullName(String fullName) {
        return peopleRepository.getIdByFullName(fullName);
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }
}
