package com.strelnikov.bugtracker.controller;

import com.strelnikov.bugtracker.entity.Project;
import com.strelnikov.bugtracker.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    private ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("projects",projectService.findAll());
        return "projects";
    }

    @GetMapping("/createProjectForm")
    public String showCreateProjectForm(Model model) {
        Project project = new Project();
        model.addAttribute(project);
        return "create-project";
    }

    @PostMapping("/saveProject")
    public String saveProject(@ModelAttribute("project") Project project) {
        projectService.saveProject(project);
        return "redirect:/projects";
    }

}
