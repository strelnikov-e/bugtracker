package com.strelnikov.bugtracker.repository;

import com.strelnikov.bugtracker.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepository  extends JpaRepository<Project,Long> {

    List<Project> findByNameContaining(String name);

    @Query("""
            SELECT proj FROM Project proj
            JOIN ProjectRole proj_role on proj.id=proj_role.project.id
            WHERE proj.name LIKE %:name% AND proj_role.user.id = :userId
            """)
    List<Project> findByUserIdAndByNameContaining(Long userId, String name);
}
