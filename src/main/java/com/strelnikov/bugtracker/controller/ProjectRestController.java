package com.strelnikov.bugtracker.controller;

import com.strelnikov.bugtracker.entity.Project;
import com.strelnikov.bugtracker.exception.ProjectNotFoundException;
import com.strelnikov.bugtracker.service.ProjectService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
public class ProjectRestController {

    private ProjectService projectService;
    private ProjectModelAssembler assembler;

    public ProjectRestController(ProjectService projectService, ProjectModelAssembler assembler) {
        this.projectService = projectService;
        this.assembler = assembler;
    }

    @GetMapping("/projects")
    public CollectionModel<EntityModel<Project>> all(@RequestBody(required = false) String name) {
        List<EntityModel<Project>> projects = projectService.findByName(name).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(projects, linkTo(methodOn(ProjectRestController.class).all(name)).withSelfRel());
    }

    @GetMapping("/projects/{projectId}")
    public EntityModel<Project> getById(@PathVariable Long projectId) {
        Project project = projectService.findById(projectId);
        if (project == null) {
            throw new ProjectNotFoundException(projectId);
        }
        return assembler.toModel(project);
    }

    @PostMapping("/projects")
    public Project addProject(@RequestBody Project project) {
        project.setId(0L);
        return projectService.save(project);
    }

    @PutMapping("/projects")
    public Project updateProject(@RequestBody Project project) {
        return projectService.save(project);
    }

    @DeleteMapping("/projects/{projectId}")
    public String deleteProject(@PathVariable Long projectId) {
        if (projectService.findById(projectId) == null) {
            throw new ProjectNotFoundException(projectId);
        }
        projectService.deleteById(projectId);
        return "Deleted project with ID: " + projectId;
    }


}
