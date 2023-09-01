package com.gaziyev.spring.dto;

import com.gaziyev.spring.model.Book;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonDTO {

    private int id;

    @NotEmpty(message = "Не забудьте указать ФИО")
    @Size(min = 6, max = 200, message = "ФИО не должно быть меньше 6, и больше 200 букв!")
    String fullName;

    LocalDate dateOfBirth;

    List<Book> books;

    String password;

    String role;

    String status;

    public boolean isLocked() {
        return status.equals("LOCKED");
    }
}
