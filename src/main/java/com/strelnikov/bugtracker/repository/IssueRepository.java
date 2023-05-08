package com.strelnikov.bugtracker.repository;

import com.strelnikov.bugtracker.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IssueRepository extends JpaRepository<Issue, Long> {
	
	List<Issue> findAllByProjectId(Long projectId);

    List<Issue> findIssuesByTagsId(Long tagId);

    List<Issue> findAllByProjectIdAndNameContaining(Long projectId, String name);

    Optional<Issue> findByIdAndProjectId(Long issueId, Long projectId);

    List<Issue> findByNameContaining(String name);

    void deleteAllByProjectId(Long projectId);
}
