package com.gaziyev.spring.filter;

import com.gaziyev.spring.handler.CustomAccessDeniedHandler;
import com.gaziyev.spring.service.PersonDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.logging.Logger;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Component
public class UserDeletionCheckFilter extends OncePerRequestFilter {
    PersonDetailsService personDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails userDetails) {

                if (!personDetailsService.isEnabled(userDetails.getUsername())) {
                    new SecurityContextLogoutHandler().logout(request, response, authentication);
                    response.sendRedirect("/auth/login?deleted");

                    log.info("User: " + userDetails.getUsername()
                            + " has been deleted!");
                    return;
                }

                String userStatus = personDetailsService.getUserStatus(userDetails.getUsername());
                if (userStatus.equals("LOCKED")) {
                    new SecurityContextLogoutHandler().logout(request, response, authentication);
                    response.sendRedirect("/auth/login?locked");

                    log.info("User: " + userDetails.getUsername()
                            + " has been locked!");
                    return;
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
