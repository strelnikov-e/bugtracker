package com.strelnikov.bugtracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.strelnikov.bugtracker.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    List<User> findByUsernameContaining(String username);

    Optional<User> findByUsername(String username);

    @Transactional
    void deleteByUsername(String username);

    boolean existsByUsername(String username);
}
