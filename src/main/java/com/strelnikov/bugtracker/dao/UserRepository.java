package com.strelnikov.bugtracker.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.strelnikov.bugtracker.entity.User;

public interface UserRepository extends JpaRepository<User,String> {

}
