package com.gaziyev.spring.util;


import com.gaziyev.spring.models.Person;
import com.gaziyev.spring.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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
        Person person = (Person) target;

        List<Integer> ids = peopleService.getIdByFullName(person.getFullName());
        if (person.getId() != 0 &&  ids.contains(person.getId()) && ids.size() > 1)
            errors.rejectValue("fullName", "", "Человек с таким ФИО уже существует");

        if (person.getId() == 0 && peopleService.getPersonByFullName(person.getFullName()).isPresent())
            errors.rejectValue("fullName", "", "Человек с таким ФИО уже существует");


        if (person.getDateOfBirth() == null) {
            errors.rejectValue("dateOfBirth", "", "Используйте этот формат (dd-mm-yyyy)");
            return;
        }

        Date currentDateMinus7Years = Date.from(LocalDate.now().minusYears(7)
                .atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date minYear = Date.from(LocalDate.now().minusYears(100)
                .atStartOfDay(ZoneId.systemDefault()).toInstant());

        if (!person.getDateOfBirth().before(currentDateMinus7Years))
            errors.rejectValue("dateOfBirth", "", "Для преобретение книги, человек должен быть старше 6 лет!");

        if (!person.getDateOfBirth().after(minYear))
            errors.rejectValue("dateOfBirth", "", "Для преобретения книги, человек должен быть младше 100 лет!");
    }
}
