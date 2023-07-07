package com.gaziyev.spring.annotations.Validators;

import com.gaziyev.spring.annotations.IAnnotations.MaxBirthYear;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Year;

public class MaxBirthYearValidator implements ConstraintValidator<MaxBirthYear,Integer> {
    @Override
    public void initialize(MaxBirthYear constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        int expectedYear = Year.now().getValue() - 7;
        return value < expectedYear;
    }
}
