package com.strelnikov.bugtracker.controller;

import com.strelnikov.bugtracker.entity.Issue;
import com.strelnikov.bugtracker.exception.IssueNotFoundException;
import com.strelnikov.bugtracker.service.IssueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class IssueRestController {

    private IssueService issueService;

    public IssueRestController(IssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping("/projects/{projectId}/issues")
    public ResponseEntity<List<Issue>> getAllByProjectId(@PathVariable Long projectId, @RequestBody(required = false) String name) {
        List<Issue> issues = issueService.getAll(projectId, name);
        if(issues.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(issues, HttpStatus.OK);
    }

    @GetMapping("/projects/{projectId}/issues/{issueId}")
    public ResponseEntity<Issue> findById(@PathVariable Long projectId, @PathVariable Long issueId) {
        Issue issue = issueService.findByIdAndProjectId(issueId, projectId);
        return new ResponseEntity<>(issue, HttpStatus.OK);
    }

    @PostMapping("/projects/{projectId}/issues")
    public ResponseEntity<Issue> createIssue(@PathVariable Long projectId, @RequestBody Issue requestIssue) {
        requestIssue.setId(0L);
        Issue issue = issueService.create(requestIssue, projectId);
        return new ResponseEntity<>(issue, HttpStatus.CREATED);
    }

    @PutMapping("/projects/{projectId}/issues/{issueId}")
    public ResponseEntity<Issue> updateIssue(@PathVariable Long projectId, @PathVariable Long issueId, @RequestBody Issue requestIssue) {
        Issue issue = issueService.findByIdAndProjectId(issueId, projectId);
        if (issue == null) {
            throw new IssueNotFoundException(issueId);
        }
        return new ResponseEntity<>(issueService.update(issue,requestIssue), HttpStatus.OK);
    }


    @DeleteMapping("/projects/{projectId}/issues/{issueId}")
    public String deleteIssue(@PathVariable Long projectId, @PathVariable Long issueId) {
        if (issueService.findByIdAndProjectId(issueId, projectId) == null) {
            throw new IssueNotFoundException(issueId);
        }
        issueService.deleteById(issueId);
        return "Deleted issue with id " + issueId;
    }

}
