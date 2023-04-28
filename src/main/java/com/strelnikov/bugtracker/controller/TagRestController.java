package com.strelnikov.bugtracker.controller;

import com.strelnikov.bugtracker.entity.Issue;
import com.strelnikov.bugtracker.entity.Tag;
import com.strelnikov.bugtracker.service.IssueService;
import com.strelnikov.bugtracker.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/api")
public class TagRestController {

    private TagService tagService;
    private IssueService issueService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public TagRestController(TagService tagService, IssueService issueService) {
        this.tagService = tagService;
        this.issueService = issueService;
    }

    @GetMapping("/tags")
    public List<Tag> tags(@RequestBody(required = false) String name) {
        return tagService.findByName(name);
    }

    @GetMapping("/tags/{tagId}")
    public Tag getById(@PathVariable Long tagId) {
        return tagService.findById(tagId);
    }

    @GetMapping("/issues/{issueId}/tags")
    public Set<Tag> getTagsByIssueId(@PathVariable Long issueId) {
        Issue issue = issueService.findById(issueId);
        if (issue == null) {
            throw new RuntimeException("Issue not found");
        }
        return issue.getTags();
    }

    @GetMapping("/tags/{tagId}/issues")
    public List<Issue> getAllIssuesByTagId(@PathVariable Long tagId) {
        Tag tag = tagService.findById(tagId);
        if (!tagService.existById(tagId)) {
            throw new RuntimeException("Tag not found");
        }
        return issueService.findAllByTagId(tagId);
    }

    @PostMapping("/issues/{issueId}/tags")
    public Tag addTag(@PathVariable Long issueId, @RequestBody Tag tagRequest) {
        return tagService.addTag(issueId, tagRequest);
    }

    @DeleteMapping("/issues/{issueId}/tags/{tagId}")
    public String deleteTagFromIssue(@PathVariable Long tagId, @PathVariable Long issueId) {
        Issue issue = issueService.findById(issueId);
        if (issue == null) {
            throw new RuntimeException("Issue not found");
        }
        Tag tag = tagService.findById(tagId);
        issue.getTags().remove(tag);
        return "Tag removed from issue";
    }


}
