package com.strelnikov.bugtracker.configuration;

import com.strelnikov.bugtracker.entity.IssueRoleType;
import com.strelnikov.bugtracker.entity.ProjectRoleType;
import com.strelnikov.bugtracker.entity.Role;
import com.strelnikov.bugtracker.repository.IssueRoleRepository;
import com.strelnikov.bugtracker.repository.ProjectRoleRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service("RoleService")
public class RoleService {

    private final ProjectRoleRepository projectRoleRepository;
    private final IssueRoleRepository issueRoleRepository;

    public RoleService(ProjectRoleRepository projectRoleRepository, IssueRoleRepository issueRoleRepository) {
        this.projectRoleRepository = projectRoleRepository;
        this.issueRoleRepository = issueRoleRepository;
    }

    @Transactional
    public boolean hasAnyRoleByProjectId(Long projectId, Role... roles) {
        final Long userId = getCurrentAuthentication().getPrincipal();
        final Set<ProjectRoleType> projectRoleTypes = projectRoleRepository.findRoleTypesByUserIdAndProjectId(userId, projectId);
        for (Role role : roles) {
            if (projectRoleTypes.stream().anyMatch(projectRoleType -> projectRoleType.includes(role))) {
                return true;
            }
        }
        final Set<IssueRoleType> issueRoleTypes = issueRoleRepository.findRoleTypesByUserIdAndProjectId(userId, projectId);
        for (Role role : roles) {
            if (issueRoleTypes.stream().anyMatch(issueRoleType -> issueRoleType.includes(role))) {
                return true;
            }
        }
        return false;
    }

    @Transactional
    public boolean hasAnyRoleByIssueId(Long issueId, Role... roles) {
        final Long userId = getCurrentAuthentication().getPrincipal();
        final Set<IssueRoleType> issueRoleTypes = issueRoleRepository.findRoleTypesByUserIdAndIssueId(userId, issueId);
        for (Role role : roles) {
            if (issueRoleTypes.stream().anyMatch(issueRoleType -> issueRoleType.includes(role))) {
                return true;
            }
        }
        final Set<ProjectRoleType> projectRoleTypes = projectRoleRepository.findRoleTypesByUserIdAndIssueId(userId, issueId);
        for (Role role : roles) {
            if (issueRoleTypes.stream().anyMatch(projectRoleType -> projectRoleType.includes(role))) {
                return true;
            }
        }
        return false;
    }

    private static PlainAuthentication getCurrentAuthentication() {
        return (PlainAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

}
