package com.strelnikov.bugtracker.service;

import com.strelnikov.bugtracker.entity.User;

import java.util.List;

public interface UserService {
	
	User findByUsername(String username);

    List<User> findAll();
}
