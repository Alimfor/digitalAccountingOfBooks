package com.gaziyev.spring.models;

import com.gaziyev.spring.annotations.IAnnotations.MaxCurrentYear;
import com.gaziyev.spring.annotations.IAnnotations.MinYear;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Book {
    private int id;

    @NotEmpty(message = "Не забудьте указать название книги")
    private String name;

    @NotEmpty(message = "Не забудьте указать имя автора")
    @Size(min = 2, max = 30, message = "Имя автора не дожно быть меньше 2, и больше 30 букв!")
    private String author;

    @MaxCurrentYear()
    @MinYear()
    private int year;
    private int personId;
    public Book() {}
    public Book(int id, String name, String author, int year, Integer personId) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.year = year;
        this.personId = personId != null
                                    ? personId
                                    : -1;
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
    public int getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId != null
                ? personId
                : -1;
    }
}
