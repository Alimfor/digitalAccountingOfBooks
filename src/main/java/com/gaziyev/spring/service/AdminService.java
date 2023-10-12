package com.gaziyev.spring.service;

import com.gaziyev.spring.model.Person;
import com.gaziyev.spring.repository.AdminRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Service
@Transactional
@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_FIRST')")
public class AdminService {

    AdminRepository adminRepository;


    public void changeRole(Person person, String newRole) {
        person.setRole(newRole);
        adminRepository.save(person);
    }

    public void lockUser(int id) {
        adminRepository.updatePersonStatus(id, "LOCKED");
    }

    public void activateUser(int id) {
        adminRepository.updatePersonStatus(id, "ACTIVE");
    }
}
