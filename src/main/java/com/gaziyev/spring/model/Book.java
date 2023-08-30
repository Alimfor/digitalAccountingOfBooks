package com.gaziyev.spring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "Не забудьте указать название книги")
    private String name;

    @Column(name = "author")
    @NotEmpty(message = "Не забудьте указать имя автора")
    @Size(min = 2, max = 30, message = "Имя автора не дожно быть меньше 2, и больше 30 букв!")
    private String author;

    @Column(name = "year")
    private int year;

    @ManyToOne
    @JoinColumn(name = "personid", referencedColumnName = "id")
    private Person person;

    @Column(name = "issued")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime issued;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_who")
    @NotEmpty
    private String createdWho;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public LocalDateTime getIssued() {
        return issued;
    }

    public void setIssued(LocalDateTime issued) {
        this.issued = issued;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedWho() {
        return createdWho;
    }

    public void setCreatedWho(String createdWho) {
        this.createdWho = createdWho;
    }
}
