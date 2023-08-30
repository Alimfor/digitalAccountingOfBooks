package com.gaziyev.spring.util;

import com.gaziyev.spring.security.PasswordComparison;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PasswordValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return PasswordComparison.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PasswordComparison passwordComparison = (PasswordComparison) target;
        boolean isCorrectPassword = passwordComparison.getActualPassword().equals(passwordComparison.getExpectedPassword());

        if (!isCorrectPassword) {
            errors.rejectValue("actualPassword", "", "Неправильный пароль!");
        }
    }
}
