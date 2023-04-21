package com.strelnikov.bugtracker.controller;

import com.strelnikov.bugtracker.entity.Issue;
import com.strelnikov.bugtracker.service.IssueService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/projects/{projectId}")
public class IssueRestController {

    private IssueService issueService;

    public IssueRestController(IssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping("/issues")
    public List<Issue> findAll(@PathVariable Long projectId) {
        return issueService.findAllById(projectId);
    }

    @GetMapping("/issues/{issueId}")
    public Issue findById(@PathVariable Long projectId, @PathVariable Long issueId) {
        Issue issue = issueService.findByIdAndProjectId(issueId, projectId);
        if (issue == null) {
            throw new RuntimeException("Issue not found. Requested issue id: " + issueId);
        }
        return issue;
    }

    @PostMapping("/issues")
    public Issue addIssue(@PathVariable Long projectId, @RequestBody Issue issue) {
        issue.setId(0L);
        issue.setProjectId(projectId);
        System.out.println(issue);
        return issueService.save(issue);
    }

    @PutMapping("/issues")
    public Issue updateIssue(@PathVariable Long projectId, @RequestBody Issue issue) {
        issue.setProjectId(projectId);
        return issueService.save(issue);
    }

    @DeleteMapping("/issues/{issueId}")
    public String deleteIssue(@PathVariable Long projectId, @PathVariable Long issueId) {
        if (issueService.findByIdAndProjectId(issueId, projectId) == null) {
            throw new RuntimeException("Issue not found. Requested issue id: " + issueId);
        }
        issueService.deleteById(issueId);
        return "Deleted issue with id " + issueId;
    }

}
