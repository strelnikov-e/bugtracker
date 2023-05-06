package com.strelnikov.bugtracker.service;

import com.strelnikov.bugtracker.configuration.PlainAuthentication;
import com.strelnikov.bugtracker.entity.Project;
import com.strelnikov.bugtracker.entity.ProjectRoleType;
import com.strelnikov.bugtracker.entity.User;
import com.strelnikov.bugtracker.exception.ProjectNotFoundException;
import com.strelnikov.bugtracker.repository.ProjectRepository;
import com.strelnikov.bugtracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository projectRepository;
    private UserRepository userRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }


    private User getCurrentUser() {
        final var userId = ((PlainAuthentication) SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
        return userRepository.findById(userId).orElseThrow();
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
        Optional<Project> result = projectRepository.findById(projectId);
        Project project = null;
        if (result.isPresent()) {
            project = result.get();
        }
        else {
            throw new ProjectNotFoundException(projectId);
        }
        return project;
    }

    @Override
    public void deleteById(Long projectId) {
        projectRepository.deleteById(projectId);
    }


}
