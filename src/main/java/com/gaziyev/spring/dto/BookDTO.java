package com.gaziyev.spring.dto;

import com.gaziyev.spring.model.Person;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicBoolean;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookDTO {

    int id;

    @NotEmpty(message = "Не забудьте указать название книги")
    String name;

    @NotEmpty(message = "Не забудьте указать имя автора")
    @Size(min = 2, max = 30, message = "Имя автора не дожно быть меньше 2, и больше 30 букв!")
    String author;

    int year;

    Person person;
}
