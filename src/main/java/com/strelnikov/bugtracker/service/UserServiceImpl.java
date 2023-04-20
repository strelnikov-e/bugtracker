package com.strelnikov.bugtracker.service;

import com.strelnikov.bugtracker.dao.UserRepository;
import com.strelnikov.bugtracker.entity.User;

public class UserServiceImpl implements UserService {
	
	private UserRepository userRepository;
	
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User findByEmail(String email) {
		
		return userRepository.getReferenceById(email);
	}
	
	
}
