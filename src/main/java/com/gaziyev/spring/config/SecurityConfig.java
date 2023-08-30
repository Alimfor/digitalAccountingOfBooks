package com.gaziyev.spring.config;


import com.gaziyev.spring.filter.UserDeletionCheckFilter;
import com.gaziyev.spring.handler.CustomAccessDeniedHandler;
import com.gaziyev.spring.handler.CustomAuthenticationFailureHandler;
import com.gaziyev.spring.handler.PersonAuthenticationSuccessHandler;
import com.gaziyev.spring.service.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final PersonDetailsService personDetailsService;
    private final PersonAuthenticationSuccessHandler successHandler;
    private final UserDeletionCheckFilter userDeletionCheckFilter;

    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService, PersonAuthenticationSuccessHandler successHandler, UserDeletionCheckFilter userDeletionCheckFilter) {
        this.personDetailsService = personDetailsService;
        this.successHandler = successHandler;
        this.userDeletionCheckFilter = userDeletionCheckFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                                .requestMatchers("/auth/**", "/error").permitAll()
                                .requestMatchers("/admin/changeRole", "/admin/check").hasAnyRole("FIRST","ADMIN")
                                .requestMatchers("/book/**", "/people/**", "/admin/**").hasRole("ADMIN")
                                .requestMatchers("/user/**").hasAnyRole("USER", "FIRST")
                                .anyRequest().permitAll()
                )
                .addFilterBefore(userDeletionCheckFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(handle ->
                        handle
                                .accessDeniedHandler(accessDeniedHandler())
                )
                .formLogin(login ->
                        login
                                .loginPage("/auth/login")
                                .loginProcessingUrl("/process_login")
                                .successHandler(successHandler)
                                .failureHandler(customAuthenticationFailureHandler())
                )
                .logout(logout ->
                        logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/auth/login")
                );

        return http.build();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler(personDetailsService, passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        authenticationProvider.setUserDetailsService(personDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }
}
