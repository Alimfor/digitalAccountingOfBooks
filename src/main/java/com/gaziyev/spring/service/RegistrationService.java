package com.gaziyev.spring.service;

import com.gaziyev.spring.model.Person;
import com.gaziyev.spring.repository.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RegistrationService {
    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(Person person) {
        person.setPassword(
                passwordEncoder.encode(person.getPassword())
        );

        if (peopleRepository.count() == 0) {
            person.setRole("ROLE_FIRST");
        } else {
            person.setRole("ROLE_USER");
        }

        person.setStatus("ACTIVE");
        registrationTime(person);
        peopleRepository.save(person);
    }

    private void registrationTime(Person person) {
        person.setCreatedWho("REGISTERED");
        person.setCreatedAt(LocalDateTime.now());
        person.setUpdatedAt(LocalDateTime.now());
    }

    public boolean updatePassword(String login, String newPassword) {
        Optional<Person> person = peopleRepository.findByFullName(login);

        if (person.isPresent()) {
            person.get().setPassword(
                    passwordEncoder.encode(newPassword)
            );
            peopleRepository.save(person.get());
            return true;
        }
        return false;
    }
}
