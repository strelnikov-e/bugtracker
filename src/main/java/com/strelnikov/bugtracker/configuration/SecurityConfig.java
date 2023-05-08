package com.strelnikov.bugtracker.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .cors().disable()
                .authorizeHttpRequests((request -> request.requestMatchers("/api/users").permitAll()))
                .authorizeHttpRequests(customizer -> customizer.anyRequest().authenticated())

                .httpBasic()
                .authenticationEntryPoint((request, response, authException) -> response.sendError(401))
                .and()

                .build();

    }

//    @Bean
//    public UserDetailsManager userDetailsManager(DataSource dataSource) {
//        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
////        // define query to retrive user by username (custom table)
////        jdbcUserDetailsManager.setUsersByUsernameQuery("" +
////                "select u from users u where username=?");
////        // define query to retrieve authorities
////        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("" +
////                "select userId, authority from authorities where email=?");
//        return jdbcUserDetailsManager;
//    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests((requests) -> requests
//                .requestMatchers(HttpMethod.GET, "/api/**").hasAnyRole("EMPLOYEE","MANAGER","ADMIN")
//                .requestMatchers(HttpMethod.GET, "/api/issues/**").hasAnyRole("EMPLOYEE","MANAGER","ADMIN")
//                .requestMatchers(HttpMethod.POST, "/api/issues/**").hasAnyRole("MANAGER","ADMIN")
//                .requestMatchers(HttpMethod.PUT, "/api/issues/**").hasAnyRole("MANAGER","ADMIN")
//                .requestMatchers(HttpMethod.DELETE, "/api/issues/**").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.GET, "/api/projects").hasAnyRole("EMPLOYEE","MANAGER","ADMIN")
//                .requestMatchers(HttpMethod.GET, "/api/projects/**").hasAnyRole("EMPLOYEE","MANAGER","ADMIN")
//                .requestMatchers(HttpMethod.POST, "/api/projects").hasAnyRole("MANAGER","ADMIN")
//                .requestMatchers(HttpMethod.PUT, "/api/projects").hasAnyRole("MANAGER","ADMIN")
//                .requestMatchers(HttpMethod.POST, "/api/projects/**").hasAnyRole("MANAGER","ADMIN")
//                .requestMatchers(HttpMethod.PUT, "/api/projects/**").hasAnyRole("MANAGER","ADMIN")
//                .requestMatchers(HttpMethod.DELETE, "/api/projects/**").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.GET, "/api/users").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.POST, "/api/users").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.PUT, "/api/users").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.DELETE, "/api/users").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.DELETE, "/api/tags/**").hasRole("ADMIN")
//        );
//        http.httpBasic();
//        // in general not required for stateless REST APIs that use POST, PUT, DELETE and/or PATCH
//        http.csrf().disable();
//
//        return http.build();
//    }
}
