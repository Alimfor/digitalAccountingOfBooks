package com.gaziyev.spring.models;

import com.gaziyev.spring.annotations.IAnnotations.MaxBirthYear;
import com.gaziyev.spring.annotations.IAnnotations.MinYear;
import com.gaziyev.spring.annotations.IAnnotations.Unique;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Person {
    private int id;

    @NotEmpty(message = "Не забудьте указать ФИО")
    @Size(min = 6, max = 200, message = "ФИО не должно быть меньше 6, и больше 200 букв!")
    @Unique()
    private String fullName;

    @MinYear()
    @MaxBirthYear()
    private int birthYear;

    public Person(){}
    public Person(int id, String fullName, int birthYear) {
        this.id = id;
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
}
