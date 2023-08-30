package com.gaziyev.spring.dto;

import com.gaziyev.spring.model.Person;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicBoolean;


public class BookDTO {
    private int id;
    @NotEmpty(message = "Не забудьте указать название книги")
    private String name;

    @NotEmpty(message = "Не забудьте указать имя автора")
    @Size(min = 2, max = 30, message = "Имя автора не дожно быть меньше 2, и больше 30 букв!")
    private String author;

    private int year;
    private Person person;

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
}
