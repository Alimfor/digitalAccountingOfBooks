package com.gaziyev.spring.security;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class PasswordComparison {
    final String expectedPassword;
    String actualPassword;

    public PasswordComparison(@Value("${expected.password}") String expectedPassword) {
        this.expectedPassword = expectedPassword;
    }
}
