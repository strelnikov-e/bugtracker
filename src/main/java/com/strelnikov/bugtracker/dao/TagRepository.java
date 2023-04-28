package com.strelnikov.bugtracker.dao;

import com.strelnikov.bugtracker.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {

    List<Tag> findTagsByIssuesId(Long issueId);

    List<Tag> findByNameContaining(String name);
}
