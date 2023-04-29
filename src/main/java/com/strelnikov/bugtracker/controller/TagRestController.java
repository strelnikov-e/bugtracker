package com.strelnikov.bugtracker.controller;

import com.strelnikov.bugtracker.entity.Issue;
import com.strelnikov.bugtracker.entity.Tag;
import com.strelnikov.bugtracker.exception.IssueNotFoundException;
import com.strelnikov.bugtracker.service.IssueService;
import com.strelnikov.bugtracker.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class TagRestController {

    private final TagService tagService;
    private final IssueService issueService;
    private final TagModelAssembler assembler;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public TagRestController(TagService tagService, IssueService issueService, TagModelAssembler assembler) {
        this.tagService = tagService;
        this.issueService = issueService;
        this.assembler = assembler;
    }

    @GetMapping("/tags")
    public CollectionModel<EntityModel<Tag>> all(@RequestBody(required = false) String name) {
        List<EntityModel<Tag>> tags = tagService.findByName(name).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(tags, linkTo(methodOn(TagRestController.class).all(name)).withSelfRel());
    }

    @GetMapping("/tags/{tagId}")
    public EntityModel<Tag> getById(@PathVariable Long tagId) {
        Tag tag = tagService.findById(tagId);
        return assembler.toModel(tag);
    }

    @GetMapping("/issues/{issueId}/tags")
    public CollectionModel<EntityModel<Tag>> getTagsByIssueId(@PathVariable Long issueId) {
        Issue issue = issueService.findById(issueId);
        if (issue == null) {
            throw new IssueNotFoundException(issueId);
        }
        List<EntityModel<Tag>> tags = issue.getTags().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(tags, linkTo(methodOn(TagRestController.class).getTagsByIssueId(issueId)).withSelfRel());
    }

//    @GetMapping("/tags/{tagId}/issues")
//    public CollectionModel<EntityModel<Issue>> getAllIssuesByTagId(@PathVariable Long tagId) {
//        Tag tag = tagService.findById(tagId);
//        if (!tagService.existById(tagId)) {
//            throw new TagNotFoundException(tagId);
//        }
//        return issueService.findAllByTagId(tagId);
//    }

    @PostMapping("/issues/{issueId}/tags")
    @ResponseStatus(HttpStatus.CREATED)
    public Tag addTag(@PathVariable Long issueId, @RequestBody Tag tagRequest) {
        return tagService.addTag(issueId, tagRequest);
    }

    @DeleteMapping("/issues/{issueId}/tags/{tagId}")
    public String deleteTagFromIssue(@PathVariable Long tagId, @PathVariable Long issueId) {
        Issue issue = issueService.findById(issueId);
        if (issue == null) {
            throw new IssueNotFoundException(issueId);
        }
        Tag tag = tagService.findById(tagId);
        issue.getTags().remove(tag);
        return "Tag removed from issue";
    }


}
