package com.strelnikov.bugtracker.controller;

import com.strelnikov.bugtracker.entity.Issue;
import com.strelnikov.bugtracker.entity.Project;
import com.strelnikov.bugtracker.repository.UserRepository;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
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
    private DateTimeFormat dateTimeFormat;

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
        final var response =
                restTemplate
                        .withBasicAuth("john", "password")
                        .getForEntity("/api/projects", CollectionModel.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(2,response.getBody().getContent().size());
    }

    @Test
    void shouldGetProjectSuccesfully() {
        final var response =
                restTemplate
                        .withBasicAuth("john", "password")
                        .getForEntity("/api/projects/1",
                                EntityModel.class);

//        String[] response =  projectGetResponse.getContent().toString().split(",");
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertTrue(response.getBody().getContent().toString().contains("Bugtracker"));
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
        Project project = createProject();
        final var projectResponse =
                restTemplate
                        .withBasicAuth("john", "password")
                        .postForEntity(
                        "/api/projects",
                        project,
                        String.class
                );
        System.out.println(projectResponse);
        Assertions.assertEquals(HttpStatus.CREATED, projectResponse.getStatusCode());
        Assertions.assertTrue(projectResponse.getBody().contains("\"id\":4,\"name\":\"Test project\""));
        Issue issue = new Issue();
        issue.setName("Test issue");
        final var issueResponse =
                restTemplate
                        .withBasicAuth("john", "password")
                        .postForEntity(
                                "/api/issues?projectId=4",
                                issue,
                                String.class
                        );
        System.out.println(issueResponse);
        Assertions.assertEquals(HttpStatus.CREATED, issueResponse.getStatusCode());
        Assertions.assertTrue(issueResponse.getBody().contains("\"id\":8,\"name\":\"Test issue\""));
    }

    @Test
    void shouldReturn403ForbiddenTryingUpdateByUnathorizedUser() {
        Project project = createProject();
        var response = restTemplate
                .withBasicAuth("mary","password")
                .exchange("/api/projects/1",
                        HttpMethod.PUT,
                        new HttpEntity<>(project),
                        EntityModel.class);
        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void shouldUpdateProjectSucessfully() {
        Project project = createProject();
        var response = restTemplate
                .withBasicAuth("john", "password")
                .exchange("/api/projects/1",
                        HttpMethod.PUT,
                        new HttpEntity<>(project),
                        EntityModel.class);
        System.out.println(response.getBody().getContent().toString());
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertTrue(response.getBody().getContent().toString().contains("id=1, name=Test project"));
    }

    @Test
    void shouldReturn403ForbiddenTryingDeleteByUnathorizedUser() {
        Project project = createProject();
        var response = restTemplate
                .withBasicAuth("mary","password")
                .exchange("/api/projects/1",
                        HttpMethod.DELETE,
                        new HttpEntity<>(HttpEntity.EMPTY),
                        EntityModel.class);
        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void shouldDeleteProjectSuccesfully() {
        var response = restTemplate
                .withBasicAuth("john","password")
                .exchange("/api/projects/1",
                        HttpMethod.DELETE,
                        new HttpEntity<>(HttpEntity.EMPTY),
                        EntityModel.class);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    private Project createProject() {
        Project project = new Project();
        project.setName("Test project");
        return project;
    }

}
