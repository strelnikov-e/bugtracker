package com.strelnikov.bugtracker.controller;

import com.strelnikov.bugtracker.entity.User;
import com.strelnikov.bugtracker.service.UserService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
public class UserRestController {

    private final UserService userService;
    private final UserModelAssembler assembler;

    public UserRestController(UserService userService, UserModelAssembler assembler) {
        this.userService = userService;
        this.assembler = assembler;
    }

    @GetMapping("/users")
    @PreAuthorize("@RoleService.hasRootRole()")
    public CollectionModel<EntityModel<User>> all(@RequestParam(value = "username", defaultValue = "", required = false) String username) {
        List<EntityModel<User>> users = userService.findAll(username).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(users, linkTo(methodOn(UserRestController.class).all(username)).withSelfRel());
    }

   @PostMapping("/users")
    public ResponseEntity<?> create(@RequestBody User user) {
        EntityModel<User> entityModel = assembler.toModel(userService.save(user));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
   }

   @PutMapping("/users")
   @PreAuthorize("isAuthenticated")
    public  ResponseEntity<?> update(@RequestParam(value = "username") String username, @RequestBody User user) {
        EntityModel<User> entityModel = assembler.toModel(userService.update(username, user));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
   }

   @DeleteMapping("/users")
   @PreAuthorize("@RoleService.hasRootRole()")
    public ResponseEntity<?> delete(@RequestParam(value="username", defaultValue = "") String username) {
        userService.deleteByUsername(username);
        return ResponseEntity.noContent().build();
   }

}
