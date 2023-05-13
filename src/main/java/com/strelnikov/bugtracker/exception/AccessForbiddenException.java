package com.strelnikov.bugtracker.exception;

public class AccessForbiddenException extends RuntimeException {

    public AccessForbiddenException() {
        super("Access denied");
    }
}
