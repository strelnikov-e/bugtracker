package com.strelnikov.bugtracker.controller;

import com.strelnikov.bugtracker.entity.Project;
import com.strelnikov.bugtracker.exception.ProjectNotFoundException;
import com.strelnikov.bugtracker.service.ProjectService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
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
    public CollectionModel<EntityModel<Project>> all(@RequestParam(value = "name", defaultValue = "", required = false) String name) {
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
    public ResponseEntity<?> addProject(@RequestBody Project project) {
        project.setId(0L);
        EntityModel<Project> entityModel = assembler.toModel(projectService.save(project));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PutMapping("/projects")
    public ResponseEntity<?> updateProject(@RequestBody Project project) {
        EntityModel<Project> entityModel = assembler.toModel(projectService.save(project));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping("/projects/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable Long projectId) {
        if (projectService.findById(projectId) == null) {
            throw new ProjectNotFoundException(projectId);
        }
        projectService.deleteById(projectId);
        return ResponseEntity.noContent().build();
    }


}
