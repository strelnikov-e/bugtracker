package com.strelnikov.bugtracker.controller;

import com.strelnikov.bugtracker.entity.Issue;
import com.strelnikov.bugtracker.entity.Project;
import com.strelnikov.bugtracker.service.IssueService;
import com.strelnikov.bugtracker.service.ProjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class IssueRestController {

    private IssueService issueService;

    private ProjectService projectService;

    public IssueRestController(IssueService issueService, ProjectService projectService) {
        this.issueService = issueService;
        this.projectService = projectService;
    }

    @GetMapping("/projects/{projectId}/issues")
    public List<Issue> findAll(@PathVariable Long projectId) {
        return issueService.findAllById(projectId);
    }

    @GetMapping("/projects/{projectId}/issues/{issueId}")
    public Issue findById(@PathVariable Long projectId, @PathVariable Long issueId) {
        Issue issue = issueService.findByIdAndProjectId(issueId, projectId);
        if (issue == null) {
            throw new RuntimeException("Issue not found. Requested issue id: " + issueId);
        }
        return issue;
    }

    @PostMapping("/projects/{projectId}/issues")
    public Issue addIssue(@PathVariable Long projectId, @RequestBody Issue issue) {
        Project project = projectService.findById(projectId);
        issue.setProject(project);
        issue.setId(0L);
        return issueService.save(issue);
    }

    @PutMapping("/projects/{projectId}/issues")
    public Issue updateIssue(@PathVariable Long projectId, @RequestBody Issue issue) {
        issue.setProject(projectService.findById(projectId));
        return issueService.save(issue);
    }

    @DeleteMapping("/projects/{projectId}/issues/{issueId}")
    public String deleteIssue(@PathVariable Long projectId, @PathVariable Long issueId) {
        if (issueService.findByIdAndProjectId(issueId, projectId) == null) {
            throw new RuntimeException("Issue not found. Requested issue id: " + issueId);
        }
        issueService.deleteById(issueId);
        return "Deleted issue with id " + issueId;
    }

}
