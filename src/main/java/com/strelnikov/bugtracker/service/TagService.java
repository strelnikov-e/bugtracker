package com.strelnikov.bugtracker.service;

import com.strelnikov.bugtracker.entity.Tag;

import java.util.List;

public interface TagService {

    List<Tag> findAll();

    List<Tag> findByIssueId(Long issueId);

    Tag save(Tag tag);

    void deleteByIssueId(Long issueId);
}
