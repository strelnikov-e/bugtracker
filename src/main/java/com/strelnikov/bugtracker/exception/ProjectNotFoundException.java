package com.strelnikov.bugtracker.exception;

public class ProjectNotFoundException extends RuntimeException {

    public ProjectNotFoundException(Long id) {
        super("Project with id '" + id + "' not found");
    }
}
