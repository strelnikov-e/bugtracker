package com.strelnikov.bugtracker.controller;

import com.strelnikov.bugtracker.entity.Project;
import com.strelnikov.bugtracker.exception.ProjectNotFoundException;
import com.strelnikov.bugtracker.service.ProjectService;
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
public class ProjectRestController {

    private ProjectService projectService;
    private ProjectModelAssembler assembler;

    public ProjectRestController(ProjectService projectService, ProjectModelAssembler assembler) {
        this.projectService = projectService;
        this.assembler = assembler;
    }

    // Get all projects
    @GetMapping("/projects")
    @PreAuthorize("isAuthenticated")
    public CollectionModel<EntityModel<Project>> all(@RequestParam(value = "name", defaultValue = "", required = false) String name) {
        List<EntityModel<Project>> projects = projectService.findByName(name).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(projects, linkTo(methodOn(ProjectRestController.class).all(name)).withSelfRel());
    }

    // Get project by ID
    @GetMapping("/projects/{projectId}")
    @PreAuthorize("@RoleService.hasAnyRoleByProjectId(#projectId, @ProjectRole.MANAGER)")
    public EntityModel<Project> getById(@PathVariable Long projectId) {
        Project project = projectService.findById(projectId);
        if (project == null) {
            throw new ProjectNotFoundException(projectId);
        }
        return assembler.toModel(project);
    }

    // Create new project and set project admin to current user
    @PostMapping("/projects")
    @PreAuthorize("isAuthenticated")
    public ResponseEntity<?> createProject(@RequestBody Project project) {
        EntityModel<Project> entityModel = assembler.toModel(projectService.create(project));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    // Update project
    @PutMapping("/projects/{projectId}")
    @PreAuthorize("@RoleService.hasAnyRoleByProjectId(#projectId, @ProjectRole.MANAGER)")
    public ResponseEntity<?> updateProject(@PathVariable Long projectId, @RequestBody Project project) {
        project.setId(projectId);
        EntityModel<Project> entityModel = assembler.toModel(projectService.save(project));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping("/projects/{projectId}")
    @PreAuthorize("@RoleService.hasAnyRoleByProjectId(#projectId, @ProjectRole.ADMIN)")
    public ResponseEntity<?> deleteProject(@PathVariable Long projectId) {
        projectService.deleteById(projectId);
        return ResponseEntity.noContent().build();
    }


}
