package com.strelnikov.bugtracker.service;

import com.strelnikov.bugtracker.configuration.PlainAuthentication;
import com.strelnikov.bugtracker.entity.Issue;
import com.strelnikov.bugtracker.entity.Project;
import com.strelnikov.bugtracker.entity.ProjectRoleType;
import com.strelnikov.bugtracker.entity.User;
import com.strelnikov.bugtracker.exception.ProjectNotFoundException;
import com.strelnikov.bugtracker.repository.ProjectRepository;
import com.strelnikov.bugtracker.repository.ProjectRoleRepository;
import com.strelnikov.bugtracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository projectRepository;
    private ProjectRoleRepository projectRoleRepository;
    private UserRepository userRepository;
    private IssueService issueService;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectRoleRepository projectRoleRepository, UserRepository userRepository, IssueService issueService) {
        this.projectRepository = projectRepository;
        this.projectRoleRepository = projectRoleRepository;
        this.userRepository = userRepository;
        this.issueService = issueService;
    }


    private User getCurrentUser() {
        final var userId = ((PlainAuthentication) SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
        return userRepository.findById(userId).orElseThrow();
    }

    @Override
    @Transactional
    public Project create(Project project) {
        project.setId(0L);
        Project newProject = projectRepository.save(project);
        final User currentUser = this.getCurrentUser();
        currentUser.addProjectRole(newProject, ProjectRoleType.ADMIN);
        return newProject;
    }

    @Override
    @Transactional
    public Project save(Project project) {
        Project newProject = projectRepository.save(project);
        final User currentUser = this.getCurrentUser();
        currentUser.addProjectRole(project, ProjectRoleType.ADMIN);
        return newProject;
    }

    @Override
    public List<Project> findByName(String name) {
        if (name == null) {
            name = "";
        }
        return projectRepository.findByNameContaining(name);
    }

    @Override
    public Project findById(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(projectId));
    }

    @Override
    @Transactional
    public void deleteById(Long projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new ProjectNotFoundException(projectId);
        }
        List<Issue> issues = issueService.findByProjectId(projectId);
        for (Issue issue : issues) {
            issueService.deleteById(issue.getId());
        }
        projectRoleRepository.deleteAllByProjectId(projectId);
        projectRepository.deleteById(projectId);
    }


}
