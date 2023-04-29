package com.strelnikov.bugtracker.controller;

import com.strelnikov.bugtracker.entity.Issue;
import com.strelnikov.bugtracker.service.IssueService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class IssueRestController {

    private final IssueService issueService;

    public IssueRestController(IssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping("/issues/{issueId}")
    public Issue findById(@PathVariable Long issueId) {
        return issueService.findById(issueId);
    }

    @PostMapping("/issues")
    @ResponseStatus(HttpStatus.CREATED)
    public Issue createIssue(@RequestBody Issue requestIssue) {
        requestIssue.setId(0L);
        return issueService.create(requestIssue);
    }

    @PutMapping("/issues/{issueId}")
    public Issue updateIssue(@PathVariable Long issueId, @RequestBody Issue requestIssue) {
        return issueService.update(issueId,requestIssue);
    }


    @DeleteMapping("/issues/{issueId}")
    public String deleteIssue(@PathVariable Long issueId) {
        issueService.deleteById(issueId);
        return "Deleted issue with id '" + issueId + "'";
    }

}
