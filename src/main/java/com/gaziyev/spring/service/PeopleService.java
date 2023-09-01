package com.gaziyev.spring.service;

import com.gaziyev.spring.model.Book;
import com.gaziyev.spring.model.Person;
import com.gaziyev.spring.repository.PeopleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Service
@Transactional(readOnly = true)
public class PeopleService {
    PeopleRepository peopleRepository;
    PasswordEncoder passwordEncoder;

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findOne(int id) {
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElse(null);
    }

    public Optional<Person> getPersonByFullName(String fullName) {
        return peopleRepository.findByFullName(fullName);
    }

    public List<Integer> getIdByFullName(String fullName) {
        return peopleRepository.getIdByFullName(fullName);
    }

    @Transactional
    public void save(Person person) {
        person.setPassword(
                passwordEncoder.encode(person.getPassword())
        );

        if (peopleRepository.count() == 0) {
            person.setRole("ROLE_FIRST");
        } else {
            person.setRole("ROLE_USER");
        }

        person.setStatus("ACTIVE");

        whoAndWhenCreated(person);
        peopleRepository.save(person);
    }

    @Transactional
    public void update(Person updatedPerson) {
        Person originPerson = findOne(updatedPerson.getId());
        originPerson.setFullName(updatedPerson.getFullName());
        originPerson.setDateOfBirth(updatedPerson.getDateOfBirth());

        theLastUpdate(originPerson);
        peopleRepository.save(originPerson);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

    private void whoAndWhenCreated(Person person) {
        person.setCreatedWho("ADMIN");
        person.setCreatedAt(LocalDateTime.now());
        theLastUpdate(person);
    }

    private void theLastUpdate(Person person) {
        person.setUpdatedAt(LocalDateTime.now());
    }
}
