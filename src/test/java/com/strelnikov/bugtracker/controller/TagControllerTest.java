package com.strelnikov.bugtracker.controller;

import com.strelnikov.bugtracker.entity.Issue;
import com.strelnikov.bugtracker.entity.Tag;
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

public class TagControllerTest extends AbstractControllerTest {

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
    void shouldReturn401UnathorizedUserTryingToGetTags() {
        final var response =
                restTemplate.getForEntity("/api/tags", ResponseEntity.class);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.UNAUTHORIZED);
    }

    @Test
    void shouldReturn403ForbiddenUserTryingToGetTagsForIssue() {
        final var response =
                restTemplate
                        .withBasicAuth("mary", "password")
                        .getForEntity("/api/issues/1/tags", CollectionModel.class);
        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void shouldGetListOfTagsSuccessfully() {
        final var response =
                restTemplate
                        .withBasicAuth("john", "password")
                        .getForEntity("/api/issues/1/tags", CollectionModel.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(3,response.getBody().getContent().size());
    }

    @Test
    void shouldGetTagSuccesfully() {
        final var response =
                restTemplate
                        .withBasicAuth("john", "password")
                        .getForEntity("/api/tags/1",
                                EntityModel.class);
        System.out.println(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertTrue(response.getBody().getContent().toString().contains("id=1, name=database"));
    }

    @Test
    void shouldReturn401IfUnauthorizedUserTryingToCreateTagForIssue() {
        Tag tag = new Tag("TestTag");
        final var projectCreatedResponse =
                restTemplate.postForEntity(
                        "/api/issues/1/tags",
                        tag,
                        ResponseEntity.class
                );
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, projectCreatedResponse.getStatusCode());
    }

    @Test
    void shouldCreateTagForIssueSuccesfully() {
        Tag tag = new Tag("TestTag");
        final var response =
                restTemplate
                        .withBasicAuth("john", "password")
                        .postForEntity(
                                "/api/issues?projectId=1",
                                tag,
                                EntityModel.class
                        );
        System.out.println(response);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertTrue(response.getBody().getContent().toString().contains("id=8, name=TestTag"));
    }

    @Test
    void shouldReturn403ForbiddenTryingToCreateTagForIssueByUnauthorizedUser() {
        Tag tag = new Tag("TestTag");
        final var response =
                restTemplate
                        .withBasicAuth("mary", "password")
                        .postForEntity(
                                "/api/issues?projectId=1",
                                tag,
                                EntityModel.class
                        );
        System.out.println(response);
        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }


    @Test
    void shouldReturn403ForbiddenTryingDeleteIssueByUnauthorizedUser() {
        var response = restTemplate
                .withBasicAuth("mary","password")
                .exchange("/api/issues/1/tags/1",
                        HttpMethod.DELETE,
                        new HttpEntity<>(HttpEntity.EMPTY),
                        EntityModel.class);
        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void shouldDeleteIssueSuccessfully() {
        var response = restTemplate
                .withBasicAuth("john","password")
                .exchange("/api/issues/1/tags/1",
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
