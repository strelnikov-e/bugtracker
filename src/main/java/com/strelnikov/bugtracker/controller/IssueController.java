package com.strelnikov.bugtracker.controller;

import com.strelnikov.bugtracker.entity.Issue;
import com.strelnikov.bugtracker.service.IssueService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class IssueController {

    IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping("/{projectName}/issues")
    public String findAll(@PathVariable("projectName") String projectName, @RequestParam("projectId") Long projectId, Model model) {
        List<Issue> issues = issueService.findAllById(projectId);
        model.addAttribute("issues" ,issues);
        return "issues";
    }
}
