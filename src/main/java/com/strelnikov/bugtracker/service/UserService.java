package com.strelnikov.bugtracker.service;

import com.strelnikov.bugtracker.entity.User;

public interface UserService {
	
	User findByEmail(String email);

}
