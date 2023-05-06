package com.strelnikov.bugtracker.repository;

import com.strelnikov.bugtracker.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {

    List<Tag> findTagsByIssuesId(Long issueId);

    List<Tag> findByNameContaining(String name);

    Tag findByName(String name);
}
