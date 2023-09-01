package com.gaziyev.spring.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Component
public class PersonAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        boolean hasUserRole = false;
        boolean hasAdminRole = false;

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for (var grantedAuthorities : authorities) {
            if (grantedAuthorities.getAuthority().equals("ROLE_USER") ||
                    grantedAuthorities.getAuthority().equals("ROLE_FIRST")) {
                hasUserRole = true;
                break;
            } else if (grantedAuthorities.getAuthority().equals("ROLE_ADMIN")) {
                hasAdminRole = true;
                break;
            }
        }

        if (hasUserRole)
            redirectStrategy.sendRedirect(request, response, "/user/infoAbout");
        else if (hasAdminRole)
            redirectStrategy.sendRedirect(request, response, "/admin/categories");
        else throw new IllegalStateException();
    }
}
