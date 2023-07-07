package com.gaziyev.spring.annotations.IAnnotations;

import com.gaziyev.spring.annotations.Validators.MinYearValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MinYearValidator.class)
public @interface MinYear {
    String message() default "Для преобретения книги, человек должен быть младше 100 лет!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
