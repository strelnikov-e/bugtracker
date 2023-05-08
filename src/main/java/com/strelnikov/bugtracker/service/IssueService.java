package com.strelnikov.bugtracker.service;

import com.strelnikov.bugtracker.entity.Issue;

import java.util.Collection;
import java.util.List;

public interface IssueService {

    List<Issue> getAll(Long projectId, String name);

    Issue save(Issue issue);

    Issue save(Issue issue, Long projectId);

    void deleteById(Long issueId);

    Issue create(Issue issue, Long projectId);

    Issue update(Long issueId, Issue requestIssue);

    Issue findById(Long issueId);

    List<Issue> findAllByTagId(Long tagId);

    List<Issue> findByName(String name);

    List<Issue> findByProjectId(Long projectId);

    Collection<Issue> projectIdAndNameContaining(Long projectId, String name);
}
