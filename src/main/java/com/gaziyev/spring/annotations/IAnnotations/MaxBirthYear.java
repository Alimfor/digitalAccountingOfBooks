package com.gaziyev.spring.annotations.IAnnotations;

import com.gaziyev.spring.annotations.Validators.MaxBirthYearValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MaxBirthYearValidator.class)
public @interface MaxBirthYear {
    String message() default "Для преобретение книги, человек должен быть старше 6 лет!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
