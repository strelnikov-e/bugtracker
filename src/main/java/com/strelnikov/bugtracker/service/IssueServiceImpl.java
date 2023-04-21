package com.strelnikov.bugtracker.service;

import com.strelnikov.bugtracker.dao.IssueRepository;
import com.strelnikov.bugtracker.entity.Issue;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssueServiceImpl implements IssueService {

	IssueRepository issueRepository;
	
	public IssueServiceImpl(IssueRepository issueRepository) {
		this.issueRepository = issueRepository;
	}

	@Override
	public void deleteById(Long issueId) {
		issueRepository.deleteById(issueId);
	}

	@Override
	public Issue save(Issue issue) {
		return issueRepository.save(issue);
	}

	@Override
	public List<Issue> findAllById(Long projectId) {
		
		return issueRepository.findAllByProjectId(projectId);
	}

	@Override
	public Issue findByIdAndProjectId(Long issueId, Long projectId) {
		Optional<Issue> result = issueRepository.findByIdAndProjectId(issueId, projectId);
		Issue issue = null;
		if (result.isPresent()) {

			issue = result.get();
		}
		else {
			throw new RuntimeException("Issue not found. Requested issue id: " + issueId);
		}
		return issue;
	}

}
