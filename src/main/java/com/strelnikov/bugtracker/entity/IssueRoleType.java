package com.strelnikov.bugtracker.entity;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

public enum IssueRoleType implements Role {
    ASSIGNEE, REPORTER, VIEWER;

    private final Set<Role> children = new HashSet<>();

    static {
        ASSIGNEE.children.add(VIEWER);
        REPORTER.children.add(VIEWER);
    }

    @Override
    public boolean includes(Role role) {
        return this.equals(role) || children.stream().anyMatch(r -> r.includes(role));
    }

    @Component("IssueRole")
    static class SpringComponent {
        private final IssueRoleType ASSIGNEE = IssueRoleType.ASSIGNEE;
        private final IssueRoleType REPORTER = IssueRoleType.REPORTER;
        private final IssueRoleType VIEWER = IssueRoleType.VIEWER;

        public IssueRoleType getASSIGNEE() {
            return ASSIGNEE;
        }

        public IssueRoleType getREPORTER() {
            return REPORTER;
        }

        public IssueRoleType getVIEWER() {
            return VIEWER;
        }
    }
}
