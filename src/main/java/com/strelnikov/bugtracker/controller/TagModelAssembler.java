package com.strelnikov.bugtracker.controller;

import com.strelnikov.bugtracker.entity.Tag;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TagModelAssembler implements RepresentationModelAssembler<Tag, EntityModel<Tag>> {
    @Override
    public EntityModel<Tag> toModel(Tag tag) {
        return EntityModel.of(tag,
                linkTo(methodOn(TagRestController.class).getById(tag.getId())).withSelfRel(),
                linkTo(methodOn(TagRestController.class).all(tag.getName())).withRel("tags"));
    }

}
