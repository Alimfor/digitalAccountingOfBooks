package com.gaziyev.spring.service;

import com.gaziyev.spring.model.Person;
import com.gaziyev.spring.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Service
@Transactional
public class UserService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Person find(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public void update(Person updatedPerson) {
        Person originPerson = find(updatedPerson.getId());

        originPerson.setFullName(updatedPerson.getFullName());
        originPerson.setDateOfBirth(updatedPerson.getDateOfBirth());

        theLastUpdate(originPerson);
        userRepository.save(originPerson);
    }

    public void updatePassword(int id, String newPassword) {
        Person originPerson = find(id);

        if (originPerson != null) {
            originPerson.setPassword(
                    passwordEncoder.encode(newPassword)
            );

            theLastUpdate(originPerson);
            userRepository.save(originPerson);
        }
    }

    public void removeUserFromBook(int bookId) {
        userRepository.removeUserFromBook(bookId);
    }

    public void delete(int id) {
        userRepository.deleteById(id);
    }

    private void theLastUpdate(Person person) {
        person.setUpdatedAt(LocalDateTime.now());
    }

}
