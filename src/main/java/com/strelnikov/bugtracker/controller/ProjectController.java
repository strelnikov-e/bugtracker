package com.strelnikov.bugtracker.controller;

import com.strelnikov.bugtracker.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ProjectController {

    private ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/home")
    public String showHomePage(){
        return "home";
    }

    @GetMapping("/projects")
    public String findAll(Model model) {
        model.addAttribute("projects",projectService.findAll());
        return "projects";
    }
}
