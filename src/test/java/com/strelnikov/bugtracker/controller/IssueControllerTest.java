package com.strelnikov.bugtracker.controller;

import com.strelnikov.bugtracker.entity.Issue;
import com.strelnikov.bugtracker.repository.UserRepository;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

public class IssueControllerTest extends AbstractControllerTest {

    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void beforeEach(@Autowired Flyway flyway) {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    void shouldReturn401UnauthorizedUserTryingToGetIssues() {
        final var response =
                restTemplate.getForEntity("/api/issues", ResponseEntity.class);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.UNAUTHORIZED);
    }

    @Test
    void shouldReturn403ForbiddenUserTryingToGetIssues() {
        final var response =
                restTemplate
                        .withBasicAuth("mary", "password")
                        .getForEntity("/api/issues/1", EntityModel.class);
        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void shouldGetIssuesListForCurrentUserSuccessfully() {
        final var response =
                restTemplate
                        .withBasicAuth("john", "password")
                        .getForEntity("/api/issues", CollectionModel.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(4,response.getBody().getContent().size());
    }

    @Test
    void shouldGetIssuesListForProjectAndByNameSuccessfully() {
        final var response =
                restTemplate
                        .withBasicAuth("john", "password")
                        .getForEntity("/api/issues?projectId=1&name=database", CollectionModel.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(2,response.getBody().getContent().size());
    }

    @Test
    void shouldGetIssueSuccessfully() {
        final var response =
                restTemplate
                        .withBasicAuth("john", "password")
                        .getForEntity("/api/issues/1",
                                EntityModel.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertTrue(response.getBody().getContent().toString().contains("Create issues database"));
    }

    @Test
    void shouldReturn401IfUnauthorizedUserTryingToCreateIssue() {
        Issue issue = createIssue();
        final var projectCreatedResponse =
                restTemplate.postForEntity(
                        "/api/issues",
                        issue,
                        ResponseEntity.class
                );
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, projectCreatedResponse.getStatusCode());
    }

    @Test
    void shouldCreateIssueSuccessfully() {
        Issue issue = createIssue();
        final var response =
                restTemplate
                        .withBasicAuth("john", "password")
                        .postForEntity(
                                "/api/issues?projectId=1",
                                issue,
                                EntityModel.class
                        );
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertTrue(response.getBody().getContent().toString().contains("id=8, name=Test issue"));
    }

    @Test
    void shouldReturn403ForbiddenTryingToCreateIssueByUnauthorizedUser() {
        Issue issue = createIssue();
        final var response =
                restTemplate
                        .withBasicAuth("mary", "password")
                        .postForEntity(
                                "/api/issues?projectId=1",
                                issue,
                                EntityModel.class
                        );
        System.out.println(response);
        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void shouldReturn403ForbiddenTryingUpdateIssueByUnauthorizedUser() {
        Issue issue = createIssue();
        var response = restTemplate
                .withBasicAuth("mary","password")
                .exchange("/api/issues/1",
                        HttpMethod.PUT,
                        new HttpEntity<>(issue),
                        EntityModel.class);
        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void shouldUpdateIssueSuccessfully() {
        Issue issue = createIssue();
        var response = restTemplate
                .withBasicAuth("john", "password")
                .exchange("/api/issues/1",
                        HttpMethod.PUT,
                        new HttpEntity<>(issue),
                        EntityModel.class);
        System.out.println(response.getBody().getContent().toString());
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertTrue(response.getBody().getContent().toString().contains("id=1, name=Test issue"));
    }

    @Test
    void shouldReturn403ForbiddenTryingDeleteIssueByUnauthorizedUser() {
        var response = restTemplate
                .withBasicAuth("mary","password")
                .exchange("/api/issues/1",
                        HttpMethod.DELETE,
                        new HttpEntity<>(HttpEntity.EMPTY),
                        EntityModel.class);
        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void shouldDeleteIssueSuccessfully() {
        var response = restTemplate
                .withBasicAuth("john","password")
                .exchange("/api/issues/1",
                        HttpMethod.DELETE,
                        new HttpEntity<>(HttpEntity.EMPTY),
                        EntityModel.class);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    private Issue createIssue() {
        Issue issue = new Issue();
        issue.setName("Test issue");
        return issue;
    }
}
