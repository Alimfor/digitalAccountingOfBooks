package com.gaziyev.spring.util;


import com.gaziyev.spring.dto.PersonDTO;
import com.gaziyev.spring.model.Person;
import com.gaziyev.spring.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.util.List;

@Component
public class PersonValidator implements Validator {
    private final PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PersonDTO person = (PersonDTO) target;

        List<Integer> ids = peopleService.getIdByFullName(person.getFullName());
        if (person.getId() != 0 && ids.contains(person.getId()) && ids.size() > 1)
            errors.rejectValue("fullName", "", "Человек с таким логином уже существует");

        if (person.getId() == 0 && peopleService.getPersonByFullName(person.getFullName()).isPresent())
            errors.rejectValue("fullName", "", "Человек с таким логином уже существует");

        if (person.getDateOfBirth() == null) {
            errors.rejectValue("dateOfBirth", "", "Введите дату в формате (dd.mm.yyyy)");
            return;
        }

        LocalDate currentDateMinus7Years = LocalDate.now().minusYears(7);
        LocalDate minYear = LocalDate.now().minusYears(100);

        if (!person.getDateOfBirth().isBefore(currentDateMinus7Years))
            errors.rejectValue("dateOfBirth", "", "Для преобретение книги, человек должен быть старше 6 лет!");

        if (!person.getDateOfBirth().isAfter(minYear))
            errors.rejectValue("dateOfBirth", "", "Для преобретения книги, человек должен быть младше 100 лет!");
    }
}
