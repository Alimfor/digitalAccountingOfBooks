package com.gaziyev.spring.models;

import com.gaziyev.spring.annotations.IAnnotations.MaxBirthYear;
import com.gaziyev.spring.annotations.IAnnotations.MinYear;
import com.gaziyev.spring.annotations.IAnnotations.Unique;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Unique
    @NotEmpty(message = "Не забудьте указать ФИО")
    @Size(min = 6, max = 200, message = "ФИО не должно быть меньше 6, и больше 200 букв!")
    @Column(name = "fullName")
    private String fullName;

    @MinYear
    @MaxBirthYear
    @Column(name = "birthYear")
    private int birthYear;

    @OneToMany(mappedBy = "person")
    private List<Book> books;

    public Person(){}
    public Person(String fullName, int birthYear) {
        this.fullName = fullName;
        this.birthYear = birthYear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
