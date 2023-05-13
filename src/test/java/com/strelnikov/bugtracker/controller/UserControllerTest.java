package com.strelnikov.bugtracker.controller;

import com.strelnikov.bugtracker.entity.User;
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

public class UserControllerTest extends AbstractControllerTest {

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
    void shouldReturn401UnauthorizedUserTryingToGetUsers() {
        final var usersGetResponse =
                restTemplate.getForEntity("/api/users", ResponseEntity.class);
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, usersGetResponse.getStatusCode());
    }

    @Test
    void shouldReturn403ForbiddenUserTryingToGetUsers() {
        final var usersGetResponse =
                restTemplate
                        .withBasicAuth("john", "password")
                        .getForEntity("/api/users", EntityModel.class);
        Assertions.assertEquals(HttpStatus.FORBIDDEN,usersGetResponse.getStatusCode());
    }

    @Test
    void shouldReturnListOfUsersSuccessfully() {
        final var usersGetResponse =
                restTemplate
                        .withBasicAuth("root", "password")
                        .getForObject("/api/users", CollectionModel.class);
        Assertions.assertEquals(4, usersGetResponse.getContent().toArray().length);
    }

    @Test
    void shouldReturnListOfUsersWithSpecificUsernameSuccessfully() {
        final var usersGetResponse =
                restTemplate
                        .withBasicAuth("root", "password")
                        .getForEntity("/api/users?username=john", CollectionModel.class);
        Assertions.assertEquals(1, usersGetResponse.getBody().getContent().size());
        System.out.println(usersGetResponse.getBody().getContent().toString());
        Assertions.assertTrue(usersGetResponse.getBody().getContent().toString().contains("id=2, username=john"));
    }

    @Test
    void shouldCreateNewUserSuccessfully() {
        User user = new User("test","test@mail.com","password",
                true,"name","lastName","company");
        final var usersPostResponse =
                restTemplate
                        .postForEntity("/api/users", user, EntityModel.class);
        Assertions.assertEquals(HttpStatus.CREATED, usersPostResponse.getStatusCode());
        Assertions.assertTrue(usersPostResponse.getBody().getContent().toString().contains("id=5, username=test"));
    }

    @Test
    void shouldReturn401UnauthorizedUserTryingToUpdate() {
        User user = new User("test","test@mail.com","password",
                true,"name","lastName","company");
        HttpEntity<User> userHttpEntity = new HttpEntity<>(user);
        final var usersPostResponse =
                restTemplate
                        .exchange("/api/users?username=john", HttpMethod.PUT,
                                userHttpEntity, EntityModel.class);
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, usersPostResponse.getStatusCode());
    }

    @Test
    void shouldReturn403ForbiddenUserTryingToUpdate() {
        User user = new User("test","test@mail.com","password",
                true,"name","lastName","company");
        HttpEntity<User> userHttpEntity = new HttpEntity<>(user);
        final var response =
                restTemplate
                        .withBasicAuth("mary", "password")
                        .exchange("/api/users?username=john", HttpMethod.PUT,
                                userHttpEntity, String.class);
        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void shouldUpdateHimselfSuccessfully() {
        User user = new User("test","test@mail.com","password",
                true,"name","lastName","company");
        HttpEntity<User> userHttpEntity = new HttpEntity<>(user);
        final var usersResponse =
                restTemplate
                        .withBasicAuth("john", "password")
                        .exchange("/api/users?username=john", HttpMethod.PUT,
                                userHttpEntity, String.class);
        Assertions.assertEquals(HttpStatus.CREATED, usersResponse.getStatusCode());
        Assertions.assertTrue(usersResponse.getBody().contains("id\":2,\"username\":\"john\",\"email\":\"test@mail.com\""));
    }

    @Test
    void shouldReturn401UnathorizedUserTryingToDelete() {
        final var usersDeleteResponse =
                restTemplate.exchange("/api/users?username=john", HttpMethod.DELETE,
                        HttpEntity.EMPTY, String.class);
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, usersDeleteResponse.getStatusCode());
    }

//    @Test
//    void shouldDeleteUserSuccessfully() {
//        final var response =
//                restTemplate
//                        .withBasicAuth("root", "password")
//                        .exchange("/api/users?username=john", HttpMethod.DELETE,
//                        HttpEntity.EMPTY, String.class);
//        System.out.println(response);
//        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
//    }

}
