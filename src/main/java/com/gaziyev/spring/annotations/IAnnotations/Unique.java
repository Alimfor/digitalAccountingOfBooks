package com.gaziyev.spring.annotations.IAnnotations;

import com.gaziyev.spring.annotations.Validators.UniqueValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueValidator.class)
public @interface Unique {
    String message() default "К сожалению такое ФИО уже существует!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
