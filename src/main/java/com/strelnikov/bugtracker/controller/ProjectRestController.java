package com.strelnikov.bugtracker.controller;

import com.strelnikov.bugtracker.entity.Project;
import com.strelnikov.bugtracker.service.ProjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProjectRestController {

    private ProjectService projectService;

    public ProjectRestController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/projects")
    public List<Project> findAll() {
        return projectService.findAll();
    }

    @GetMapping("/projects/{projectId}")
    public Project findById(@PathVariable Long projectId) {
        Project project = projectService.findById(projectId);
        if (project == null) {
            throw new RuntimeException("Project not found. Requested project id: " + projectId);
        }
        return project;
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
            throw new RuntimeException("Project not found. Requested project ID: " + projectId);
        }
        projectService.deleteById(projectId);
        return "Deleted project with ID: " + projectId;
    }


}
