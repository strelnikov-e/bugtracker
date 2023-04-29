package com.strelnikov.bugtracker.service;

import com.strelnikov.bugtracker.entity.Issue;

import java.util.List;

public interface IssueService {

    List<Issue> getAll(Long projectId, String name);

    Issue save(Issue issue);

    void deleteById(Long issueId);

    Issue create(Issue issue);

    Issue update(Long issueId, Issue requestIssue);

    Issue findById(Long issueId);

    List<Issue> findAllByTagId(Long tagId);

    List<Issue> findByName(String name);
}
