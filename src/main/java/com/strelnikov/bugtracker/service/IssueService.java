package com.strelnikov.bugtracker.service;

import com.strelnikov.bugtracker.entity.Issue;

import java.util.List;

public interface IssueService {
	
	List<Issue> findAllById(Long id);

    Issue findByIdAndProjectId(Long issueId, Long projectId);

    Issue save(Issue issue);

    void deleteById(Long issueId);
}
