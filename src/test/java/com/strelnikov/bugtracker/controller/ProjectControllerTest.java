package com.strelnikov.bugtracker.controller;

import com.strelnikov.bugtracker.entity.Project;
import com.strelnikov.bugtracker.entity.User;
import com.strelnikov.bugtracker.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Map;

public class ProjectControllerTest extends AbstractControllerTest {

    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void beforeEach() {
        transactionTemplate.executeWithoutResult(
                status -> DatabaseUtil.cleanDatabase(jdbcTemplate)
        );
    }

    @Test
    void shouldReturn401IfUnathorizedUserTryingToCreateProject() {
        userRepository.save(
                new User("john", "john@mail.com", "password",
                        true, "John", "Doe","company"));
        Project project = Project.newProject("test");

        final var projectCreatedResponse =
                restTemplate.postForEntity(
                        "/api/projects",
                        project,
                        ResponseEntity.class,
                        Map.of("name", "project_name")
                );
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, projectCreatedResponse.getStatusCode());
    }

    @Test
    void shouldCreateProjectAndIssueSuccesfully() {

    }
}
