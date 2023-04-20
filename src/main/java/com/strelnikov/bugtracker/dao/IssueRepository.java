package com.strelnikov.bugtracker.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.strelnikov.bugtracker.entity.Issue;

public interface IssueRepository extends JpaRepository<Issue, Long> {
	
	List<Issue> findAllByProjectId(Long projectId);

}
