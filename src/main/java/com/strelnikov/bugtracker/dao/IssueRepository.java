package com.strelnikov.bugtracker.dao;

import com.strelnikov.bugtracker.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IssueRepository extends JpaRepository<Issue, Long> {
	
	List<Issue> findAllByProjectId(Long projectId);

    Optional<Issue> findByIdAndProjectId(Long issueId, Long projectId);
}
