package com.strelnikov.bugtracker.repository;

import com.strelnikov.bugtracker.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IssueRepository extends JpaRepository<Issue, Long> {
	
	List<Issue> findAllByProjectId(Long projectId);

    List<Issue> findIssuesByTagsId(Long tagId);

    List<Issue> findAllByProjectIdAndNameContaining(Long projectId, String name);

    Optional<Issue> findByIdAndProjectId(Long issueId, Long projectId);

    List<Issue> findByNameContaining(String name);

    void deleteAllByProjectId(Long projectId);

    Collection<Issue> findByProjectIdAndNameContaining(Long projectId, String name);

    @Query("""
            SELECT issue from Issue issue
            JOIN Project proj ON issue.project.id = proj.id
            JOIN ProjectRole proj_role ON proj.id = proj_role.project.id
            WHERE proj_role.user.id = :userId AND issue.name LIKE :name
            """)
    List<Issue> findByUserIdAndNameContaining(Long userId, String name);
}
