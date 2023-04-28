package com.strelnikov.bugtracker.service;

import com.strelnikov.bugtracker.dao.IssueRepository;
import com.strelnikov.bugtracker.dao.ProjectRepository;
import com.strelnikov.bugtracker.entity.Issue;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IssueServiceImpl implements IssueService {

	IssueRepository issueRepository;
	ProjectRepository projectRepository;

	public IssueServiceImpl(IssueRepository issueRepository, ProjectRepository projectRepository) {
		this.issueRepository = issueRepository;
		this.projectRepository = projectRepository;
	}


	@Override
	public void deleteById(Long issueId) {
		issueRepository.deleteById(issueId);
	}

	@Override
	public Issue save(Issue issue) {
		return issueRepository.save(issue);
	}

	// Get all issues request with optional get by issue name;
	@Override
	public List<Issue> getAll(Long projectId, String name) {
		List<Issue> issues = new ArrayList<>();
		if (name == null) {
			issueRepository.findAllByProjectId(projectId).forEach(issues::add);
		} else {
			issueRepository.findAllByProjectIdAndNameContaining(projectId, name).forEach(issues::add);
		}
		if (issues.isEmpty()) {
			throw new RuntimeException("Issues not found");
		}
		return issues;
	}

	// Get issue details
	@Override
	public Issue findByIdAndProjectId(Long issueId, Long projectId) {
		Optional<Issue> result = issueRepository.findByIdAndProjectId(issueId, projectId);
		if (result.isEmpty()) {
			throw new RuntimeException("Issue not found. Requested issue id: " + issueId);
		}
		return result.get();
	}

	@Override
	public Issue findById(Long issueId) {
		return issueRepository.findById(issueId).orElseThrow(() -> new RuntimeException("Issue not found"));
	}

	@Override
	public List<Issue> findAllByTagId(Long tagId) {
		return issueRepository.findIssuesByTagsId(tagId);
	}

	@Override
	public Issue create(Issue issue, Long projectId) {
		Issue newIssue = projectRepository.findById(projectId).map(project -> {
					issue.setProject(project);
					return issueRepository.save(issue);
				}).orElseThrow(() -> new RuntimeException("Project with id = " + projectId + " not found"));
		return newIssue;
	}

	@Override
	public Issue update(Issue issue, Issue requestIssue) {
		issue.setTags(requestIssue.getTags());
		issue.setAssignee(requestIssue.getAssignee());
		issue.setDescription(requestIssue.getDescription());
		issue.setClosedDate(requestIssue.getClosedDate());
		issue.setDueDate(requestIssue.getDueDate());
		issue.setReporter(requestIssue.getReporter());
		issue.setName(requestIssue.getName());
		issue.setReproducible(requestIssue.isReproducible());
		issue.setSeverity(requestIssue.getSeverity());
		issue.setStatus(requestIssue.getStatus());
		return issueRepository.save(issue);
	}
}
