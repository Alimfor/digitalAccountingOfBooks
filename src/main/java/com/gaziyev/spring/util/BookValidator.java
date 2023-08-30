package com.gaziyev.spring.util;

import com.gaziyev.spring.model.Book;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.Year;


@Component
public class BookValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;

        if (book.getYear() > Year.now().getValue())
            errors.rejectValue("year", "", "Год издательства не должен превышать текущий!");
    }
}
