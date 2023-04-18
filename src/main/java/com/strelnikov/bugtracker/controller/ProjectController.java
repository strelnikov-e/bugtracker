package com.strelnikov.bugtracker.controller;

import com.strelnikov.bugtracker.service.ProjectService;
import org.springframework.stereotype.Controller;
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
}
