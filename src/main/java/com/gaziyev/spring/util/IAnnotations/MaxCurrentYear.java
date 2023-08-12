package com.gaziyev.spring.util.IAnnotations;

import com.gaziyev.spring.util.MaxCurrentYearValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MaxCurrentYearValidator.class)
public @interface MaxCurrentYear {
    String message() default "Год издательства не должен превышать текущий!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
