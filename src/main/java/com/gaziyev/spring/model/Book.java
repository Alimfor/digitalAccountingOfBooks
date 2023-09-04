package com.gaziyev.spring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "book")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "name")
    @NotEmpty(message = "Не забудьте указать название книги")
    String name;

    @Column(name = "author")
    @NotEmpty(message = "Не забудьте указать имя автора")
    @Size(min = 2, max = 30, message = "Имя автора не дожно быть меньше 2, и больше 30 букв!")
    String author;

    @Column(name = "year")
    int year;

    @ManyToOne
    @JoinColumn(name = "personid", referencedColumnName = "id")
    Person person;

    @Column(name = "issued")
    @Temporal(TemporalType.TIMESTAMP)
    LocalDateTime issued;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @Column(name = "created_who")
    @NotEmpty
    String createdWho;

    public Book() {
    }

    public Book(String name, String author, int year, Person person) {
        this.name = name;
        this.author = author;
        this.year = year;
        this.person = person;
    }

    public boolean isExpired() {
        LocalDateTime issuedDate = getIssued();
        LocalDateTime currentDate = LocalDateTime.now();
        Duration duration = Duration.between(issuedDate, currentDate);
        long TEN_DAYS_IN_SECONDS = 10 * 24 * 60 * 60;

        return duration.getSeconds() > TEN_DAYS_IN_SECONDS;
    }
}
