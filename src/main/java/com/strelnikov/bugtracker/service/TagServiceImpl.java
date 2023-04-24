package com.strelnikov.bugtracker.service;

import com.strelnikov.bugtracker.dao.TagRepository;
import com.strelnikov.bugtracker.entity.Tag;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public void deleteByIssueId(Long issueId) {
        tagRepository.deleteAllByIssueId(issueId);
    }

    @Override
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> findByIssueId(Long issueId) {
        return tagRepository.findByIssueId(issueId);
    }
}
