package com.strelnikov.bugtracker.controller;

import com.strelnikov.bugtracker.entity.Issue;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class IssueModelAssembler implements RepresentationModelAssembler<Issue, EntityModel<Issue>> {
    @Override
    public EntityModel<Issue> toModel(Issue issue) {
        return EntityModel.of(issue,
                linkTo(methodOn(IssueRestController.class).getById(issue.getId())).withSelfRel(),
                linkTo(methodOn(IssueRestController.class).all(0L,"")).withRel("issues"));

    }
}
