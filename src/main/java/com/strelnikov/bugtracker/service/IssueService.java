package com.strelnikov.bugtracker.service;

import com.strelnikov.bugtracker.entity.Issue;

import java.util.List;

public interface IssueService {

    List<Issue> getAll(Long projectId, String name);

    Issue findByIdAndProjectId(Long issueId, Long projectId);

    Issue save(Issue issue);

    void deleteById(Long issueId);

    Issue create(Issue issue, Long projectId);

    Issue update(Issue issue, Issue requestIssue);

    Issue findById(Long issueId);

    List<Issue> findAllByTagId(Long tagId);
}
