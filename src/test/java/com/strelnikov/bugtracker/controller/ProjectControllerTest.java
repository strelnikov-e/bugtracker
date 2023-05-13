package com.strelnikov.bugtracker.controller;

import com.strelnikov.bugtracker.entity.Project;
import com.strelnikov.bugtracker.repository.UserRepository;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

public class ProjectControllerTest extends AbstractControllerTest {

    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private UserRepository userRepository;

//    @BeforeEach
//    void beforeEach() {
//        transactionTemplate.executeWithoutResult(
//                status -> DatabaseUtil.cleanDatabase(jdbcTemplate)
//        );
//    }

    @BeforeEach
    void beforeEach(@Autowired Flyway flyway) {
        flyway.clean();
        flyway.migrate();
    }



    @Test
    void shouldReturn401UnathorizedUserTryingToGetProjects() {
        final var projectGetResponse =
                restTemplate.getForEntity("/api/projects", ResponseEntity.class);
        Assertions.assertEquals(projectGetResponse.getStatusCode(),HttpStatus.UNAUTHORIZED);
    }

    @Test
    void shouldGetProjectsListSuccesfully() {
        final var projectGetResponse =
                restTemplate
                        .withBasicAuth("john", "password")
                        .getForObject("/api/projects", CollectionModel.class);
        Assertions.assertEquals(1,projectGetResponse.getContent().size());
    }

    @Test
    void shouldGetProjectSuccesfully() {
        final var projectGetResponse =
                restTemplate
                        .withBasicAuth("john", "password")
                        .getForObject("/api/projects/1", EntityModel.class);
        System.out.println(projectGetResponse.toString());
        System.out.println(projectGetResponse.getLinks());
        String[] response =  projectGetResponse.getContent().toString().split(",");
        Assertions.assertTrue(response[1].contains("Bugtracker"));

    }


    @Test
    void shouldReturn401IfUnathorizedUserTryingToCreateProject() {

        Project project = Project.newProject("test");

        final var projectCreatedResponse =
                restTemplate.postForEntity(
                        "/api/projects",
                        project,
                        ResponseEntity.class
                );
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, projectCreatedResponse.getStatusCode());
    }

    @Test
    void shouldCreateProjectAndIssueSuccesfully() {
        Project project = new Project();
        project.setName("TestProject");
        final var projectCreatedResponse =
                restTemplate
                        .withBasicAuth("john", "password")
                        .postForObject(
                        "/api/projects",
                        project,
                        ResponseEntity.class
                );
        Assertions.assertEquals(HttpStatus.CREATED, projectCreatedResponse.getStatusCode());
    }
}
