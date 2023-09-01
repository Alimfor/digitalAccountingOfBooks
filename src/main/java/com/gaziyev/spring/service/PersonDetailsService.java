package com.gaziyev.spring.service;

import com.gaziyev.spring.model.Person;
import com.gaziyev.spring.repository.PeopleRepository;
import com.gaziyev.spring.security.PersonDetails;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Service
public class PersonDetailsService implements UserDetailsService {
    PeopleRepository peopleRepository;

    @Transactional(readOnly = true)
    public String getUserStatus(String fullName) {
        Optional<Person> person = peopleRepository.findByFullName(fullName);
        if (person.isPresent())
            return person.get().getStatus();
        else return "User not found";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = peopleRepository.findByFullName(username);

        if (person.isEmpty())
            throw new UsernameNotFoundException("User not found");

        return new PersonDetails(person.get());
    }

    public boolean isEnabled(String fullName) {
        Optional<Person> person = peopleRepository.findByFullName(fullName);
        return person.isPresent();
    }

    public boolean isCorrectPassword(String login, String password, PasswordEncoder passwordEncoder) {
        Optional<Person> person = peopleRepository.findByFullName(login);
        return person.filter(value -> passwordEncoder.matches(password, value.getPassword())).isPresent();
    }
}
