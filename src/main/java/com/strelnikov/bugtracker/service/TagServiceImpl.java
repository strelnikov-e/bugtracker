package com.strelnikov.bugtracker.service;

import com.strelnikov.bugtracker.dao.IssueRepository;
import com.strelnikov.bugtracker.dao.TagRepository;
import com.strelnikov.bugtracker.entity.Tag;
import com.strelnikov.bugtracker.exception.IssueNotFoundException;
import com.strelnikov.bugtracker.exception.TagNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private TagRepository tagRepository;
    private IssueRepository issueRepository;

    public TagServiceImpl(TagRepository tagRepository, IssueRepository issueRepository) {
        this.tagRepository = tagRepository;
        this.issueRepository = issueRepository;
    }

    @Override
    public Tag addTag(Long issueId, Tag tagRequest) {
        Tag tag = issueRepository.findById(issueId).map(issue -> {
            long tagId = tagRequest.getId();
            // tag is existed
            if (tagId != 0L) {
                Tag _tag = tagRepository.findById(tagId)
                        .orElseThrow(() -> new TagNotFoundException(tagId));
                issue.addTag(_tag);
                issueRepository.save(issue);
                return _tag;
            }
            // add and create new Tag
            issue.addTag(tagRepository.save(tagRequest));
            return tagRequest;
        }).orElseThrow(() -> new IssueNotFoundException(issueId));

        return tag;
    }

    @Override
    public boolean existById(Long tagId) {
        return tagRepository.existsById(tagId);
    }

    @Override
    public Tag findById(Long tagId) {
        return tagRepository.findById(tagId).orElseThrow(() -> new TagNotFoundException(tagId));
    }

    @Override
    public List<Tag> findByName(String name) {
        if (name != null) {
        return tagRepository.findByNameContaining(name);
        }
        return tagRepository.findAll();
    }
}
