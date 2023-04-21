package com.strelnikov.bugtracker.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        // define query to retrive user by username (custom table)
        jdbcUserDetailsManager.setUsersByUsernameQuery("" +
                "select email, password, enabled from users where email=?");
        // define query to retrieve authorities
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("" +
                "select email, authority from authorities where email=?");
        return jdbcUserDetailsManager;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers(HttpMethod.GET, "/api/projects").hasRole("DEVELOPER")
                .requestMatchers(HttpMethod.GET, "/api/projects/**").hasRole("DEVELOPER")
                .requestMatchers(HttpMethod.POST, "/api/projects").hasAnyRole("MANAGER","ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/projects").hasAnyRole("MANAGER","ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/projects/**").hasRole("ADMIN")
        );
        http.httpBasic();
        // in general not required for stateless REST APIs that use POST, PUT, DELETE and/or PATCH
        http.csrf().disable();

        return http.build();
    }
}
