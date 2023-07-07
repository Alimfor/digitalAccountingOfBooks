package com.gaziyev.spring.annotations.Validators;

import com.gaziyev.spring.annotations.IAnnotations.MaxCurrentYear;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Year;

public class MaxCurrentYearValidator implements ConstraintValidator<MaxCurrentYear, Integer> {
    @Override
    public void initialize(MaxCurrentYear constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        int currentYear = Year.now().getValue();
        return value <= currentYear;
    }
}
