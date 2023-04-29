package com.strelnikov.bugtracker.controller;

import com.strelnikov.bugtracker.entity.Issue;
import com.strelnikov.bugtracker.service.IssueService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
public class IssueRestController {

    private final IssueService issueService;
    private final IssueModelAssembler assembler;

    public IssueRestController(IssueService issueService, IssueModelAssembler assembler) {
        this.issueService = issueService;
        this.assembler = assembler;
    }

    @GetMapping("/issues/{issueId}")
    public EntityModel<Issue> getById(@PathVariable Long issueId) {
        Issue issue = issueService.findById(issueId);
        return assembler.toModel(issue) ;
    }

    @GetMapping("/issues")
    public CollectionModel<EntityModel<Issue>> all(@RequestBody(required = false) String name) {
        List<EntityModel<Issue>> issues = issueService.findByName(name).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(issues, linkTo(methodOn(IssueRestController.class).all(name)).withSelfRel());
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
