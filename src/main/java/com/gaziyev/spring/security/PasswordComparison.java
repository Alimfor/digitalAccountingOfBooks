package com.gaziyev.spring.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PasswordComparison {
    private final String expectedPassword;
    private String actualPassword;

    public PasswordComparison(@Value("${expected.password}") String expectedPassword) {
        this.expectedPassword = expectedPassword;
    }

    public String getExpectedPassword() {
        return expectedPassword;
    }

    public String getActualPassword() {
        return actualPassword;
    }

    public void setActualPassword(String actualPassword) {
        this.actualPassword = actualPassword;
    }
}
