package com.strelnikov.bugtracker.entity;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public enum ProjectRoleType implements Role {
    ADMIN, MANAGER;

    private final Set<Role> children = new HashSet<>();

    static {
        ADMIN.children.add(MANAGER);
        MANAGER.children.addAll(List.of(IssueRoleType.ASSIGNEE, IssueRoleType.REPORTER));
    }

    @Override
    public boolean includes(Role role) {
        return this.equals(role) || children.stream().anyMatch(r -> r.includes(role));
    }

    @Component("ProjectRole")
    static class SpringComponent {
        private final ProjectRoleType ADMIN = ProjectRoleType.ADMIN;
        private final ProjectRoleType MANAGER = ProjectRoleType.MANAGER;

        public ProjectRoleType getADMIN() {
            return ADMIN;
        }

        public ProjectRoleType getMANAGER() {
            return MANAGER;
        }
    }
}
