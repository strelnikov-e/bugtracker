package com.strelnikov.bugtracker.service;

import com.strelnikov.bugtracker.entity.Tag;

import java.util.List;

public interface TagService {

    Tag findById(Long tagId);

    boolean existById(Long tagId);

    Tag addTag(Long issueId, Tag tag);

    List<Tag> findByName(String name);
}
