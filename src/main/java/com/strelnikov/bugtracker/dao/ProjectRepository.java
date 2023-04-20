package com.strelnikov.bugtracker.dao;

import com.strelnikov.bugtracker.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository  extends JpaRepository<Project,Long> {
}
