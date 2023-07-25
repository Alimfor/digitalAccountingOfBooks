package com.gaziyev.spring.repositories;

import com.gaziyev.spring.models.Book;
import com.gaziyev.spring.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book,Integer> {
    List<Book> findBooksByPerson(Person person);

    @Modifying
    @Query(value = "CALL remove_person_fromBook(?1)", nativeQuery = true)
    void removePersonFromBook(int bookId);

    @Modifying
    @Query(value = "CALL update_book_person_relationship(?1,?2)", nativeQuery = true)
    void updateBookPersonRelationship(int bookId,int personId);
}
