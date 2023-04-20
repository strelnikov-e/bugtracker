package com.strelnikov.bugtracker.service;

import com.strelnikov.bugtracker.dao.IssueRepository;
import com.strelnikov.bugtracker.entity.Issue;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssueServiceImpl implements IssueService {

	IssueRepository issueRepository;
	
	public IssueServiceImpl(IssueRepository issueRepository) {
		this.issueRepository = issueRepository;
	}
	
	@Override
	public List<Issue> findAllById(Long projectId) {
		
		return issueRepository.findAllByProjectId(projectId);
	}

}
