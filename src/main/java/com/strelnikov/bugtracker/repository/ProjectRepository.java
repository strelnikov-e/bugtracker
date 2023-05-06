package com.strelnikov.bugtracker.repository;

import com.strelnikov.bugtracker.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository  extends JpaRepository<Project,Long> {

    List<Project> findByNameContaining(String name);
}
