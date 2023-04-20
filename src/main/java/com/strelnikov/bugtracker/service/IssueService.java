package com.strelnikov.bugtracker.service;

import java.util.List;

import com.strelnikov.bugtracker.entity.Issue;

public interface IssueService {
	
	List<Issue> findAllById(Long id);

}
