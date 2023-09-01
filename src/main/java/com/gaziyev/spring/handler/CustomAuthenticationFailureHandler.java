package com.gaziyev.spring.handler;

import com.gaziyev.spring.service.PersonDetailsService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.util.logging.Logger;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    PersonDetailsService personDetailsService;
    PasswordEncoder passwordEncoder;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String fullName = request.getParameter("username");
        String password = request.getParameter("password");
        String status = personDetailsService.getUserStatus(fullName);

        if ("LOCKED".equals(status)) {
            response.sendRedirect("/auth/login?locked");
            log.info("User: " + fullName + " is locked!");
        } else if (!personDetailsService.isEnabled(fullName)) {
            response.sendRedirect("/auth/login?deleted");
            log.info("User: " + fullName + " is deleted!");
        } else if (!personDetailsService.isCorrectPassword(fullName,password,passwordEncoder)){
            response.sendRedirect("/auth/login?error");
        }
    }
}
