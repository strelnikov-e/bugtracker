package com.strelnikov.bugtracker.service;


import com.strelnikov.bugtracker.entity.Project;

import java.util.List;

public interface ProjectService {

    List<Project> findByName(String name);

    Project save(Project project);

    Project findById(Long id);

    void deleteById(Long projectId);

    Project create(Project project);
}
