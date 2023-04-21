package com.strelnikov.bugtracker.service;

import com.strelnikov.bugtracker.dao.ProjectRepository;
import com.strelnikov.bugtracker.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository projectRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Project save(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public Project findById(Long id) {
        Optional<Project> result = projectRepository.findById(id);
        Project project = null;
        if (result.isPresent()) {
            project = result.get();
        }
        else {
            throw new RuntimeException("Project not found. Requested project id: " + id);
        }
        return project;
    }

    @Override
    public void deleteById(Long projectId) {
        projectRepository.deleteById(projectId);
    }


}
