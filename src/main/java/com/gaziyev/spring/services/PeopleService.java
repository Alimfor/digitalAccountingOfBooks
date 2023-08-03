package com.gaziyev.spring.services;

import com.gaziyev.spring.models.Book;
import com.gaziyev.spring.models.Person;
import com.gaziyev.spring.repositories.PeopleRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
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

    public List<Book> getBooksByPersonId(int id) {
        Optional<Person> foundPerson = peopleRepository.findById(id);

        if (foundPerson.isPresent()) {
            Hibernate.initialize(foundPerson.get().getBooks());

            foundPerson.get().getBooks().forEach(book -> {
                long diffInMillies = Math.abs(book.getIssued().getTime() - new Date().getTime());
                long TEN_DAYS = 864_000_000;//ms

                if (diffInMillies > TEN_DAYS)
                    book.setExpired(true);
            });

            return foundPerson.get().getBooks();
        }
        return Collections.emptyList();
    }

    public Optional<Person> getPersonByFullName(String fullName) {
        return peopleRepository.findByFullName(fullName);
    }

    public List<Integer> getIdByFullName(String fullName) {
        return peopleRepository.getIdByFullName(fullName);
    }

    @Transactional
    public void save(Person person) {
        person.setCreatedAt(new Date());
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        Person originPerson = findOne(id);
        originPerson.setDateOfBirth(updatedPerson.getDateOfBirth());
        originPerson.setFullName(updatedPerson.getFullName());
        peopleRepository.save(originPerson);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }
}
