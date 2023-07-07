package com.gaziyev.spring.annotations.Validators;

import com.gaziyev.spring.annotations.IAnnotations.Unique;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class UniqueValidator implements ConstraintValidator<Unique,String> {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void initialize(Unique constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        String query = "SELECT COUNT(*) FROM person WHERE fullName = ?";
        int count = jdbcTemplate.queryForObject(query,new Object[]{s},Integer.class);
        return count == 0;
    }
}
