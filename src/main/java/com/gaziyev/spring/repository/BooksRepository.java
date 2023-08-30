package com.gaziyev.spring.repository;

import com.gaziyev.spring.model.Book;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    @Modifying
    @Query(value = "CALL remove_person_from_book(?1)", nativeQuery = true)
    void removePersonFromBook(int bookId);

    @Modifying
    @Query(value = "CALL update_book_person_relationship(?1,?2)", nativeQuery = true)
    void updateBookPersonRelationship(int bookId, int personId);

    List<Book> findByNameStartingWith(String bookName, PageRequest pageRequest);
}
