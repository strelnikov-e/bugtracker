package com.strelnikov.bugtracker.controller;

import com.strelnikov.bugtracker.entity.Project;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProjectModelAssembler implements RepresentationModelAssembler<Project, EntityModel<Project>> {
    @Override
    public EntityModel<Project> toModel(Project project) {
        return EntityModel.of(project,
                linkTo(methodOn(ProjectRestController.class).getById(project.getId())).withSelfRel(),
                linkTo(methodOn(ProjectRestController.class).all(project.getName())).withRel("projects"));

    }
}
