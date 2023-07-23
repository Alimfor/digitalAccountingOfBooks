package com.gaziyev.spring.annotations.Validators;

import com.gaziyev.spring.annotations.IAnnotations.Unique;
import com.gaziyev.spring.dao.PersonDAO;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraintvalidation.SupportedValidationTarget;
import jakarta.validation.constraintvalidation.ValidationTarget;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UniqueValidator implements ConstraintValidator<Unique, String> {
    private final PersonDAO personDAO;

    @Autowired
    public UniqueValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public void initialize(Unique constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        Long count = personDAO.getPeopleCountByFullName(s);

        return count == 0L;
    }
}
