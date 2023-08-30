package com.gaziyev.spring.handler;

import com.gaziyev.spring.service.PersonDetailsService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.util.logging.Logger;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private final PersonDetailsService personDetailsService;
    private final PasswordEncoder passwordEncoder;
    private static final Logger LOG = Logger.getLogger(String.valueOf(CustomAuthenticationFailureHandler.class));

    public CustomAuthenticationFailureHandler(PersonDetailsService personDetailsService, PasswordEncoder passwordEncoder) {
        this.personDetailsService = personDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String fullName = request.getParameter("username");
        String password = request.getParameter("password");
        String status = personDetailsService.getUserStatus(fullName);

        if ("LOCKED".equals(status)) {
            response.sendRedirect("/auth/login?locked");
            LOG.warning("User: " + fullName + " is locked!");
        } else if (!personDetailsService.isEnabled(fullName)) {
            response.sendRedirect("/auth/login?deleted");
            LOG.warning("User: " + fullName + " is deleted!");
        } else if (!personDetailsService.isCorrectPassword(fullName,password,passwordEncoder)){
            response.sendRedirect("/auth/login?error");
        }
    }
}
