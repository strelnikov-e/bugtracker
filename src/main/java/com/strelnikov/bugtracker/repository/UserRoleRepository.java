package com.strelnikov.bugtracker.repository;

import com.strelnikov.bugtracker.entity.UserRole;
import com.strelnikov.bugtracker.entity.UserRoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    @Query("""
    SELECT user_role.type FROM UserRole user_role
    WHERE user_role.user.id = :userId
    """)
    Set<UserRoleType> findAllByUserId(Long userId);

    void deleteAllByUserId(Long userId);
}
