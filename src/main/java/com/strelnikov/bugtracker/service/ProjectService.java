package com.strelnikov.bugtracker.service;


import com.strelnikov.bugtracker.entity.Project;

import java.util.List;

public interface ProjectService {

    List<Project> findAll();
}
