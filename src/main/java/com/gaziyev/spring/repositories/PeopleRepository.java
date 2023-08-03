package com.gaziyev.spring.repositories;

import com.gaziyev.spring.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person,Integer> {
    Optional<Person> findByFullName(String fullName);
    @Query("SELECT p.id FROM Person p WHERE p.fullName = ?1")
    List<Integer> getIdByFullName(String fullName);
}
