package com.gaziyev.spring.models;

import com.gaziyev.spring.annotations.IAnnotations.MaxCurrentYear;
import com.gaziyev.spring.annotations.IAnnotations.MinYear;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

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

    @MaxCurrentYear
    @Column(name = "year")
    private int year;

    @ManyToOne
    @JoinColumn(name = "personId",referencedColumnName = "id")
    private Person person;

    public Book() {}
    public Book(String name, String author, int year,Person person) {
        this.name = name;
        this.author = author;
        this.year = year;
        this.person = person;
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


}
