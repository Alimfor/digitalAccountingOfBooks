package com.gaziyev.spring.annotations.Validators;

import com.gaziyev.spring.annotations.IAnnotations.MinYear;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Year;

public class MinYearValidator implements ConstraintValidator<MinYear,Integer> {

    @Override
    public void initialize(MinYear constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        int minYear = Year.now().getValue() - 100;
        return value >= minYear;
    }
}
