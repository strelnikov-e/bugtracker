package com.strelnikov.bugtracker.service;

import com.strelnikov.bugtracker.entity.Issue;
import com.strelnikov.bugtracker.entity.Project;
import com.strelnikov.bugtracker.exception.IssueNotFoundException;
import com.strelnikov.bugtracker.exception.ProjectNotFoundException;
import com.strelnikov.bugtracker.repository.IssueRepository;
import com.strelnikov.bugtracker.repository.IssueRoleRepository;
import com.strelnikov.bugtracker.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class IssueServiceImpl implements IssueService {

	IssueRepository issueRepository;
	IssueRoleRepository issueRoleRepository;
	ProjectRepository projectRepository;

	public IssueServiceImpl(IssueRepository issueRepository, IssueRoleRepository issueRoleRepository, ProjectRepository projectRepository) {
		this.issueRepository = issueRepository;
		this.issueRoleRepository = issueRoleRepository;
		this.projectRepository = projectRepository;
	}

	@Override
	@Transactional
	public void deleteById(Long issueId) {
		if (!issueRepository.existsById(issueId)) {
			throw new IssueNotFoundException(issueId);
		}
		issueRoleRepository.deleteAllByIssueId(issueId);
		issueRepository.deleteById(issueId);
	}

	@Override
	public List<Issue> findByName(String name) {
		if (name == null) {
			name = "";
		}
		return issueRepository.findByNameContaining(name);
	}


	@Override
	@Transactional
	public Issue save(Issue issue, Long projectId) {
		issue.setProject(projectRepository.getReferenceById(projectId));
		final Issue newIssue = issueRepository.save(issue);
		return newIssue;
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
		return issues;
	}

	@Override
	public List<Issue> findByProjectId(Long projectId) {
		if (!projectRepository.existsById(projectId)) {
			throw new ProjectNotFoundException(projectId);
		}
		return issueRepository.findAllByProjectId(projectId);
	}

	@Override
	public Issue findById(Long issueId) {
		return issueRepository.findById(issueId).orElseThrow(() -> new IssueNotFoundException(issueId));
	}

	@Override
	public List<Issue> findAllByTagId(Long tagId) {
		return issueRepository.findIssuesByTagsId(tagId);
	}

	@Override
	public Issue create(Issue issue, Long projectId) {
		Project project = projectRepository.findById(projectId)
				.orElseThrow(() ->new ProjectNotFoundException(projectId));
		issue.setProject(project);
		return issueRepository.save(issue);
	}

	@Override
	public Issue update(Long issueId, Issue requestIssue) {
		Issue issue = issueRepository.findById(issueId)
				.orElseThrow(() -> new IssueNotFoundException(issueId));
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
