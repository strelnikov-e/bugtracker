package com.strelnikov.bugtracker.controller;

import com.strelnikov.bugtracker.entity.Issue;
import com.strelnikov.bugtracker.service.IssueService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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


    @GetMapping("/issues")
    @PreAuthorize("isAuthenticated")
    public CollectionModel<EntityModel<Issue>> all(@RequestParam(value = "project",defaultValue = "0",required = false) Long projectId) {
        if (projectId != 0L) {
            List<EntityModel<Issue>> issues = issueService.findByProjectId(projectId).stream()
                    .map(assembler::toModel)
                    .collect(Collectors.toList());
            return CollectionModel.of(issues, linkTo(methodOn(IssueRestController.class).all(projectId)).withSelfRel());
        }
        List<EntityModel<Issue>> issues = issueService.findByName("").stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(issues, linkTo(methodOn(IssueRestController.class).all(0L)).withSelfRel());
    }

    // get issue by issueId, return 403 if user is not authorized to see issue
    @GetMapping("/issues/{issueId}")
    @PreAuthorize("@RoleService.hasAnyRoleByIssueId(#issueId, @ProjectRole.MANAGER)")
    public EntityModel<Issue> getById(@PathVariable Long issueId) {
        Issue issue = issueService.findById(issueId);
        return assembler.toModel(issue) ;
    }

    // create new issue for the project, return 403 if user is not authorized to see project
    @PostMapping("/issues")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("@RoleService.hasAnyRoleByProjectId(#projectId, @ProjectRole.MANAGER)")
    public ResponseEntity<?> createIssue(@RequestParam Long projectId, @RequestBody Issue requestIssue) {
        requestIssue.setId(0L);
        EntityModel<Issue> entityModel = assembler.toModel(issueService.create(requestIssue, projectId));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    // update issue, return 403 if user is not authorized to see project
    @PutMapping("/issues/{issueId}")
    @PreAuthorize("@RoleService.hasAnyRoleByIssueId(#issueId, @ProjectRole.MANAGER)")
    public ResponseEntity<?> updateIssue(@PathVariable Long issueId, @RequestBody Issue requestIssue) {
        EntityModel<Issue> entityModel = assembler.toModel(issueService.update(issueId,requestIssue));
         return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    // Delete an issue and and entry(s) in issue_role table
    // * IMPLEMENT: delete associated tags if orphaned
    @DeleteMapping("/issues/{issueId}")
    @PreAuthorize("@RoleService.hasAnyRoleByIssueId(#issueId, @ProjectRole.MANAGER)")
    public ResponseEntity<?> deleteIssue(@PathVariable Long issueId) {
        issueService.deleteById(issueId);
        return ResponseEntity.noContent().build();
    }

}
