package com.gaziyev.spring.util;

import com.gaziyev.spring.util.IAnnotations.MaxCurrentYear;
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
