package com.strelnikov.bugtracker.controller;

import com.strelnikov.bugtracker.entity.Tag;
import com.strelnikov.bugtracker.service.IssueService;
import com.strelnikov.bugtracker.service.TagService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TagRestController {

    TagService tagService;
    IssueService issueService;

    public TagRestController(TagService tagService, IssueService issueService) {
        this.tagService = tagService;
        this.issueService = issueService;
    }

    @GetMapping("/tags")
    public List<Tag> findAll() {
        return tagService.findAll();
    }

    @GetMapping("/projects/{project_id}/issues/{issue_id}/tags")
    public List<Tag> findByIssueId(@PathVariable("issue_id") Long issueId, @PathVariable("project_id") Long projectId) {
        if (issueService.findByIdAndProjectId(issueId,projectId) == null) {
            throw new RuntimeException("Tags for requested issue not found");
        }
        return tagService.findByIssueId(issueId);
    }

    @PostMapping("/projects/{project_id}/issues/{issue_id}/tags")
    public Tag addTag(@PathVariable("issue_id") Long issueId, @PathVariable("project_id") Long projectId, @RequestBody Tag tag) {
        tag.setIssue(issueService.findByIdAndProjectId(issueId,projectId));
       return tagService.save(tag);
    }

    @PutMapping("/projects/{project_id}/issues/{issue_id}/tags/{tag_name}")
    public Tag updateTag(@PathVariable("tag_name") String tagName, @PathVariable("issue_id") Long issueId,
                         @PathVariable("project_id") Long projectId, @RequestBody Tag tag) {
        tag.setIssue(issueService.findByIdAndProjectId(issueId, projectId));
        return tagService.save(tag);
    }

    @DeleteMapping("/projects/{project_id}/issues/{issue_id}/tags/{tag_name}")
    public String deleteTag(@PathVariable("tag_name") String tagName, @PathVariable("issue_id") Long issueId) {
        
        tagService.deleteByIssueId(issueId);
        return "Tags for issue were removed.";
    }

    @DeleteMapping("/projects/{project_id}/issues/{issue_id}/tags")
    public String deleteAllTags(@PathVariable("issue_id") Long issueId) {
        tagService.deleteByIssueId(issueId);
        return "Tags for issue were removed.";
    }
}
