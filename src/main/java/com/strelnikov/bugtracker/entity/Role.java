package com.strelnikov.bugtracker.entity;

import java.util.Set;

public interface Role {

    boolean includes(Role role);

    static Set<Role> roots() {
        return Set.of(ProjectRoleType.ADMIN);
    }
}
