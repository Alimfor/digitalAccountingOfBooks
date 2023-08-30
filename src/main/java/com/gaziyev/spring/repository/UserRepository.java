package com.gaziyev.spring.repository;

import com.gaziyev.spring.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Person, Integer> {
    @Modifying
    @Query(value = "CALL remove_person_from_book(?1)", nativeQuery = true)
    void removeUserFromBook(int bookId);
}
